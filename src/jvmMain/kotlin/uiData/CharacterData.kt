package uiData

import CMLOut
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.util.fastForEachReversed
import arrow.core.*
import cml.*
import data.*
import filterRight
import mapOrEither
import updateGet
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.roundToInt

data class ClassDesc(val cls: InstanceVal, val level: Int, val isPrimary: Boolean)
data class AbilityDesc(val name: String, val instance: InstanceVal, val score: Int)
data class ItemDesc(val name: String, val weight: Float, val value: Triple<Int, String, InstanceVal>,
                    val actions: List<InstanceVal>, val traits: List<Triple<String, String, InstanceVal>>,
                    val tags: List<String>, val instance: InstanceVal, val equippable: Boolean,
                    val equipped: Boolean = false
)
data class SpellDesc(
    val name: String, val school: String, val level: Int, val castingTime: String, val range: String,
    val components: List<String>, val duration: String, val actions: List<InstanceVal>, val desc: String,
    val source: String, val charge: Pair<String, Int>? = null
)

data class ClassTrait(val desc: String, val source: String, val instance: InstanceVal, val charge: Pair<String, Int>? = null)
data class BaseTrait(val desc: String, val instance: InstanceVal, val charge: Pair<String, Int>? = null)

data class MoneyDesc(
    val amount: Int, val fullName: String, val conversion: Int, val instance: InstanceVal
)

class ActionDesc private constructor(act: Action, val instance: InstanceVal, private val chargeData: Pair<String, Int>?) {
    var action = act
        private set

    fun reload(c: Character): ActionDesc {
        val copy = fromInstance(c, instance, chargeData)
//        val frozen = action
//        if(frozen is SpellDCAction && frozen.name.startsWith("Breath Weapon"))
//            CMLOut.addInfo("Reloading action $instance")
        return copy.fold({ CMLOut.addError(it.localizedMessage); this }) { it }
    }

    companion object {
        fun fromInstance(c: Character, instance: InstanceVal, withCharges: Pair<String, Int>? = null): Either<CMLException, ActionDesc> {
            val p = Character.posRest
            return instance.getName(p).flatMap { name ->
                instance.getList("tags", p).flatMap { tags ->
                    tags.value.mapOrEither { t ->
                        t.requireString(p)
                    }
                }.flatMap { tags ->
                    instance.getList("baseAction", p).flatMap { b ->
                        verifyBaseAction(c, name, instance.type.name, b.value, tags, p)
                    }.map { action ->
                        withCharges?.let { ActionWithCharges(action, it) } ?: action
                    }
                }
            }.map { ActionDesc(it, instance, withCharges) }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is ActionDesc && action == other.action && instance == other.instance &&
                chargeData == other.chargeData
    }

    override fun hashCode(): Int {
        var result = instance.hashCode()
        result = 31 * result + (chargeData?.hashCode() ?: 0)
        result = 31 * result + action.hashCode()
        return result
    }
}

class SpellSlots {
    private var number = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

    operator fun get(i: Int): Int = number[i - 1]
    override fun toString() = "[ ${number.joinToString { "$it" }} ]"
    override fun hashCode() = number.contentHashCode()
    override fun equals(other: Any?) = other is SpellSlots && number contentEquals other.number

    fun reset() = SpellSlots()

    fun useSlot(slot: Int): SpellSlots {
        val copy = SpellSlots()
        copy.number = number.copyOf()
        copy.number[slot - 1]++
        return copy
    }

    companion object {
        fun from(count: List<Int>): SpellSlots {
            val res = SpellSlots()
            for (i in res.number.indices) res.number[i] = count[i]
            return res
        }
    }
}

class Character(
    name: String,
    race: Pair<String, InstanceVal>,
    background: Pair<String, InstanceVal>,
    classes: MutableMap<String, ClassDesc>,
    abilities: MutableMap<String, AbilityDesc>
) {
    val name = mutableStateOf(name)
    val race = mutableStateOf(race)
    val background = mutableStateOf(background)
    val classes = mutableStateOf(classes.toMap())
    val abilities = mutableStateOf(abilities.toMap())

    val classTraits = mutableStateOf(mapOf<String, ClassTrait>())
    val backgroundTraits = mutableStateOf(mapOf<String, BaseTrait>())
    val racialTraits = mutableStateOf(mapOf<String, BaseTrait>())

    val skillProficiencies = mutableStateOf(listOf<InstanceVal>())
    val saveProficiencies = mutableStateOf(listOf<InstanceVal>())
    val itemProficiencies = mutableStateOf(listOf<String>())
    val saveMods = mutableStateOf(mapOf<String, Pair<Int, Boolean>>())
    val skillMods = mutableStateOf(mapOf<String, Tuple4<Int, Boolean, String, Int>>())
    val charges = mutableStateOf(mapOf<String, Pair<Int, Int>>()) // used, max

    val casterLevelX6 = mutableStateOf(0)
    val specialCasting = mutableStateOf(mapOf<String, Pair<ListVal, List<SpellSlots>>>())
    val usedSpellSlots = mutableStateOf(SpellSlots())
    val usedSpellSlotsSpecial = mutableStateOf(mapOf<String, SpellSlots>())

    val languages = mutableMapOf<String, InstanceVal>()
    val inspiration = mutableStateOf(false)
    val hp = mutableStateOf(0)
    val damage = mutableStateOf(0)
    val tempHp = mutableStateOf(0)
    val deathSaves = mutableStateOf(Pair(0, 0)) // failed, succeeded
    val speed = mutableStateOf(0)
    val ac = mutableStateOf(0)
    var acDelta = 0 // not state cause temporary variable while recalculating AC
    val initMod = mutableStateOf(0)
    val hitDice = mutableStateOf(mapOf<Int, Int>()) // (dice type, amount)
    val inventory = mutableStateOf(mapOf<ItemDesc, Int>())
    val money = mutableStateOf(Library.typesByKind("Currency").map { decl ->
        val inst = InstanceVal(decl, posInit)
        inst.getName(posInit).flatMap { name ->
            inst.getString("abbrev", posInit).flatMap { abbrev ->
                inst.getInt("conversionRatio", posInit).map { ratio ->
                    Pair(abbrev, MoneyDesc(0, name, ratio, inst))
                }
            }
        }
    }.filterRight().toMap())
    val spells = mutableStateOf(listOf<SpellDesc>())
    val actions = mutableStateOf(listOf<ActionDesc>())

    val choices = mutableStateOf(Choices())

    private fun callOnAll(fn: String, args: List<Value> = listOf(), withResult: (Value) -> Unit) {
        Library.withCharacter(this) {
            race.value.second.type.functions[fn]?.call(args, posRender)?.let(withResult)
            classes.value.forEach { (_, v) ->
                v.cls.type.functions[fn]?.call(args, posRender)?.let(withResult)
            }
            background.value.second.type.functions[fn]?.call(args, posRender)?.let(withResult)
        }.mapLeft { CMLOut.addError(it.localizedMessage) }
    }

    fun callOnTraits(fn: String, args: List<Value> = listOf()) {
        Library.withCharacter(this) {
            classTraits.value.forEach { it.value.instance.type.functions[fn]?.call(args, posRender) }
            racialTraits.value.forEach { it.value.instance.type.functions[fn]?.call(args, posRender) }
            backgroundTraits.value.forEach { it.value.instance.type.functions[fn]?.call(args, posRender) }
        }
        refreshData()
    }

    private fun <T> refold(e: Either<CMLException, T>, backup: T): T = e.fold({
        CharacterData.setError(this, it)
        backup
    }) { it }

    fun refreshData() {
        val p = PosInfo("<runtime:character:refresh>", 0, 0)

        classTraits.value = classTraits.value.map { (_, desc) ->
            val n = refold(desc.instance.getName(p), "")
            val d = refold(desc.instance.getString("desc", p), "")
            Pair(n, desc.copy(desc = d))
        }.associate { it }
        racialTraits.value = racialTraits.value.map { (_, desc) ->
            val n = refold(desc.instance.getName(p), "")
            val d = refold(desc.instance.getString("desc", p), "")
            Pair(n, desc.copy(desc = d))
        }.associate { it }
        backgroundTraits.value = backgroundTraits.value.map { (_, desc) ->
            val n = refold(desc.instance.getName(p), "")
            val d = refold(desc.instance.getString("desc", p), "")
            Pair(n, desc.copy(desc = d))
        }.associate { it }

        actions.value = actions.value.map { a ->
            a.reload(this)
        }
    }

    private fun recalcAC() {
        val dexMod = abilities.value.firstNotNullOfOrNull { if(it.value.name == "Dexterity") it.value.score.toMod() else null } ?: 0
        ac.value = 10 + dexMod
        Library.withCharacter(this) {
            inventory.value.forEach { (desc, _) ->
                if (desc.equipped) {
                    desc.instance.type.functions["onDon"]?.call(listOf(), posRender)
                }
            }

            callOnTraits("onDeltaAC", listOf())
        }
        ac.value += acDelta
        acDelta = 0
    }

    fun onUpdate() {
        recalcAC()

        var initM = 0
        callOnAll("modInitiative") {
            it.requireInt(posRender).map { v -> initM += v.value }
        }

        Library.withCharacter(this) {
            val dex = Library.types()["Dexterity"]?.let { InstanceVal(it, posRender) }
            if (dex == null) CMLOut.addWarning("Ability Dexterity does not exist.")
            else {
                val mod = abilityMod(dex)
                initM += mod
            }
        }.mapLeft { CMLOut.addError(it.localizedMessage) }

        initMod.value = initM

        val hitDiceM = mutableMapOf<Int, Int>()
        classes.value.forEach { k, (inst, level) ->
            inst.getDice("hitDie", posRender).fold({ CMLOut.addWarning("Class $k does not have the required `hitDie' field.") }) {
                hitDiceM[it.kind] = (hitDiceM[it.kind] ?: 0) + level
            }
        }
        hitDice.value = hitDiceM

        val saveModsM = mutableMapOf<String, Pair<Int, Boolean>>()
        abilities.value.forEach { (ab, v) ->
            val mod = v.score.toMod()
            if(saveProficiencies.value.contains(v.instance)) saveModsM[ab] = Pair(mod + proficiency(), true)
            else saveModsM[ab] = Pair(mod, false)
        }
        saveMods.value = saveModsM

        val skillModsM = mutableMapOf<String, Tuple4<Int, Boolean, String, Int>>()
        Library.typesByKind("Skill").forEach { decl ->
            val name = decl.fields.getVar("name")

            if(name == null) CMLOut.addWarning(CMLException.invalidField(decl.name, "name", posRender).localizedMessage)
            else if(name.value !is StringVal) CMLOut.addWarning(CMLException.typeError("String", name.value, posRender).localizedMessage)
            else {
                val fourth = skillMods.value[(name.value as StringVal).value]?.fourth ?: 0
                val ability = decl.fields.getVar("reliesOn")
                var mod = 0
                var ab = "Invalid"
                if(ability == null) CMLOut.addWarning(CMLException.invalidField(decl.name, "reliesOn", posRender).localizedMessage)
                else {
                    val tmp = abilities.value.entries.firstOrNull { it.value.instance == ability.value }
                    if(tmp == null) CMLOut.addWarning(CMLException.invalidAbility(ability.name, posRender).localizedMessage)
                    else {
                        mod = tmp.value.score.toMod()
                        ab = tmp.key
                    }
                }

                if(skillProficiencies.value.contains(InstanceVal(decl, posRender))) skillModsM[(name.value as StringVal).value] = Tuple4(mod + proficiency(), true, ab, fourth)
                else skillModsM[(name.value as StringVal).value] = Tuple4(mod, false, ab, fourth)
            }
        }
        skillMods.value = skillModsM
    }

    fun proficiency(): Int = when(classes.value.values.sumOf { it.level }) {
        in 1..4 -> 2
        in 5..8 -> 3
        in 9..12 -> 4
        in 13..16 -> 5
        else -> 6
    }

    fun hasSaveProf(prof: InstanceVal): Boolean = saveProficiencies.value.contains(prof)

    fun hasSkillProf(prof: InstanceVal): Boolean = skillProficiencies.value.contains(prof)

    fun abilityMod(ab: InstanceVal): Int {
        return ab.getString("abbrev", posRender).flatMap {
            abilities.value[it]?.score?.right() ?: return@flatMap CMLException.invalidAbility(it, posRender).left()
        }.fold({ l -> CMLOut.addError(l.localizedMessage); 0 }, { r -> floor((r - 10) / 2.0f).roundToInt() })
    }

    fun saveDc(ab: InstanceVal): Int {
        return 8 + proficiency() + abilityMod(ab)
    }

    fun useSpellSlot(level: Int) {
        if(usedSpellSlots.value[level] < defaultSpellSlots[casterLevelX6.value / 6 - 1][level])
            usedSpellSlots.value = usedSpellSlots.value.useSlot(level)
    }

    fun useSpecialSpellSlot(level: Int, cls: String) {
        val dup = usedSpellSlotsSpecial.value.toMutableMap()
        val tmp = dup[cls]
        val caster = specialCasting.value[cls]
        val l = classes.value[cls]?.level
        if(l == null) { CMLOut.addWarning("Spell slots have been added for class $cls, but $name is not of this class.") }
        else if(tmp != null && caster != null) {
            if(tmp[level] < caster.second[l][level])
                dup[cls] = tmp.useSlot(level)
        }
        usedSpellSlotsSpecial.value = dup
    }

    fun serialize(): String {
        val root = RootNode(Identifier(name.value))
        race.value.second.type.toCtor().toField("race").addTo(root)
        background.value.second.type.toCtor().toField("background").addTo(root)
        classes.value.toSerializable { _, desc -> Pair(desc.cls.type.toCtor(), desc.level.toSerializable()) }.toField("classes").addTo(root)
        classes.value.filter { it.value.isPrimary }.firstNotNullOf { it }.value.cls.type.toCtor().toField("primary").addTo(root)
        abilities.value.toSerializable { _, desc -> Pair(desc.instance.type.toCtor(), desc.score.toSerializable()) }.toField("abilities").addTo(root)
        choices.value.raceChoices.toSerializableEither().fold(
            { CMLOut.addWarning("Serialization failed for $name's racial choices: ${it.localizedMessage}") }
        ) { it.toField("choicesRace").addTo(root) }
        choices.value.backgroundChoices.toSerializableEither().fold(
            { CMLOut.addWarning("Serialization failed for $name's background choices: ${it.localizedMessage}") }
        ) { it.toField("choicesBackground").addTo(root) }
        choices.value.classesChoices.toSerializableEither { cName, cChoices ->
            val k = Library.construct(cName, posSer)?.type?.toCtor()
            if(k == null) CMLException.constructNonType(cName, posSer).left()
            else cChoices.toSerializableEither().map { v -> Pair(k, v) }
        }?.toField("choicesClasses")?.addTo(root)
        hp.value.toSerializable().toField("maxHP").addTo(root)
        damage.value.toSerializable().toField("damage").addTo(root)
        tempHp.value.toSerializable().toField("tempHP").addTo(root)
        speed.value.toSerializable().toField("speed").addTo(root)
        inspiration.value.toSerializable().toField("inspiration").addTo(root)
        deathSaves.value.first.toSerializable().toField("deathSavesFailed").addTo(root)
        deathSaves.value.second.toSerializable().toField("deathSavesSucceeded").addTo(root)
        inventory.value.toSerializable { itemDesc, count ->
            Pair(itemDesc.instance.type.toCtor(), count.toSerializable())
        }.toField("inventory").addTo(root)
        money.value.toSerializable { _, desc ->
            Pair(desc.instance.type.toCtor(), desc.amount.toSerializable())
        }.toField("currency").addTo(root)
        charges.value.toSerializable { name, (used, max) ->
            Pair(name.toSerializable(), listOf(used, max).toSerializable { it.toSerializable() })
        }.toField("charges").addTo(root)
        (1..9).map { usedSpellSlots.value[it] }.toSerializable { it.toSerializable() }.toField("usedSpellSlots").addTo(root)
        usedSpellSlotsSpecial.value.toSerializable { cls, slots ->
            Pair(
                cls.toSerializable(),
                (1..9).map { slots[it] }.toSerializable { it.toSerializable() }
            )
        }.toField("usedSpellSlotsSpecial").addTo(root)
        return root.serialize()
    }

    fun modHP(amount: Int) {
        if(amount < 0) {
            val remaining = tempHp.value + amount
            tempHp.value += amount
            if(tempHp.value < 0) tempHp.value = 0
            if(remaining < 0) damage.value -= remaining
        }
        else {
            damage.value -= amount
        }
        if(damage.value < 0) damage.value = 0
    }

    fun addTempHP(amount: Int) {
        if(amount > tempHp.value) tempHp.value = amount
    }

    fun hasCurrency(abbrev: String, amount: Int): Boolean =
        (money.value[abbrev]?.amount ?: 0) >= amount

    private fun cardinalValue(): Int {
        var total = 0
        money.value.forEach { total += it.value.conversion * it.value.amount }
        return total
    }

    fun canPay(abbrev: String, amount: Int): Boolean
        = cardinalValue() >= (money.value[abbrev]?.conversion?.let { it * amount } ?: Int.MAX_VALUE)

    fun payExact(abbrev: String, amount: Int) {
        val prev = money.value[abbrev]!!
        val mut = money.value.toMutableMap()
        mut[abbrev] = prev.copy(amount = prev.amount - amount)
        money.value = mut
    }

    fun pay(abbrev: String, amt: Int) {
        var amount = amt
        val prev = money.value[abbrev]!!
        val mut = money.value.toMutableMap()
        if(prev.amount >= amount) mut[abbrev] = prev.copy(amount = prev.amount - amount)
        else {
            mut[abbrev] = prev.copy(amount = 0)
            amount -= prev.amount

            val sorted = mut.toList().sortedBy { it.second.conversion }
            var total = cardinalValue()
            val amConv = amount * prev.conversion
            total -= amConv

            sorted.fastForEachReversed { (a, desc) ->
                val keep = total / desc.conversion
                total %= desc.conversion
                mut[a] = desc.copy(amount = keep)
            }
        }
        money.value = mut
    }

    fun earn(abbrev: String, amount: Int) {
        val prev = money.value[abbrev]!!
        val mut = money.value.toMutableMap()
        mut[abbrev] = prev.copy(amount = prev.amount + amount)
        money.value = mut
    }

    fun addItem(desc: ItemDesc, amount: Int) {
        val mut = inventory.value.toMutableMap()
        if(mut.containsKey(desc)) mut[desc] = mut[desc]!! + amount
        else {
            mut[desc] = amount
            CharacterScope(this).let { scope ->
                CMLException.catching {
                    desc.actions.forEach { action -> scope.addAction(listOf(action), posRest) }
                }.mapLeft { CMLOut.addError(it.localizedMessage) }
            }
        }
        inventory.value = mut
    }

    fun removeItem(desc: ItemDesc) {
        var filterActions = false
        val mut = inventory.value.toMutableMap()
        if(mut.containsKey(desc)) {
            if(mut[desc]!! == 1) {
                mut.remove(desc)
                filterActions = true
            }
            else mut[desc] = mut[desc]!! - 1
            inventory.value = mut
        }

        if(filterActions) {
            actions.value = actions.value.filter {
                when(val a = it.action) {
                    is AttackAction -> itemsFor(a).isNotEmpty()
                    is SpellAttackAction -> spellsFor(a).any { s -> s.source != desc.name }
                    is SpellDCAction -> spellsFor(a).any { s -> s.source != desc.name }
                    else -> true
                }
            }
        }
    }

    fun toggleItem(desc: ItemDesc) {
        val amount = inventory.value[desc] ?: 1
        inventory.value -= desc
        inventory.value += Pair(desc.copy(equipped = !desc.equipped), amount)
        recalcAC()
    }

    fun itemsFor(a: AttackAction) =
        inventory.value.filterKeys { it.name == a.name.split('(')[0].trim() }.map { it.key }
    fun spellsFor(s: SpellAttackAction) = spells.value.filter { it.name == s.name }
    fun spellsFor(s: SpellDCAction) = spells.value.filter { it.name == s.name }

    fun shortRest() { callOnTraits("onShortRest"); callOnTraits("onAnyRest") }
    fun longRest() { callOnTraits("onLongRest"); callOnTraits("onAnyRest") }
    fun dawn() { callOnTraits("onDawn") }

    fun useCharge(c: String, amount: Int) {
        val old = charges.value[c]
        if(old != null) {
            charges.value -= c
            charges.value += Pair(c, old.copy(first = min(old.first + amount, old.second)))
        }
    }

    companion object {
        val posRender = PosInfo("<runtime:character:ui>", 0, 0)
        val posInit = PosInfo("<runtime:character:init>", 0, 0)
        val posRest = PosInfo("<runtime:character:restore>", 0, 0)
        val posSer = PosInfo("<runtime:character:serialize>", 0, 0)

        val defaultSpellSlots = listOf(
            //                     1  2  3  4  5  6  7  8  9
            SpellSlots.from(listOf(2, 0, 0, 0, 0, 0, 0, 0, 0)), // Level  1
            SpellSlots.from(listOf(3, 0, 0, 0, 0, 0, 0, 0, 0)), // Level  2
            SpellSlots.from(listOf(4, 2, 0, 0, 0, 0, 0, 0, 0)), // Level  3
            SpellSlots.from(listOf(4, 3, 0, 0, 0, 0, 0, 0, 0)), // Level  4
            SpellSlots.from(listOf(4, 3, 2, 0, 0, 0, 0, 0, 0)), // Level  5
            SpellSlots.from(listOf(4, 3, 3, 0, 0, 0, 0, 0, 0)), // Level  6
            SpellSlots.from(listOf(4, 3, 3, 1, 0, 0, 0, 0, 0)), // Level  7
            SpellSlots.from(listOf(4, 3, 3, 2, 0, 0, 0, 0, 0)), // Level  8
            SpellSlots.from(listOf(4, 3, 3, 3, 1, 0, 0, 0, 0)), // Level  9
            SpellSlots.from(listOf(4, 3, 3, 3, 2, 0, 0, 0, 0)), // Level 10
            SpellSlots.from(listOf(4, 3, 3, 3, 2, 1, 0, 0, 0)), // Level 11
            SpellSlots.from(listOf(4, 3, 3, 3, 2, 1, 0, 0, 0)), // Level 12
            SpellSlots.from(listOf(4, 3, 3, 3, 2, 1, 1, 0, 0)), // Level 13
            SpellSlots.from(listOf(4, 3, 3, 3, 2, 1, 1, 0, 0)), // Level 14
            SpellSlots.from(listOf(4, 3, 3, 3, 2, 1, 1, 1, 1)), // Level 15
            SpellSlots.from(listOf(4, 3, 3, 3, 2, 1, 1, 1, 1)), // Level 16
            SpellSlots.from(listOf(4, 3, 3, 3, 2, 1, 1, 1, 1)), // Level 17
            SpellSlots.from(listOf(4, 3, 3, 3, 3, 1, 1, 1, 1)), // Level 18
            SpellSlots.from(listOf(4, 3, 3, 3, 3, 2, 1, 1, 1)), // Level 19
            SpellSlots.from(listOf(4, 3, 3, 3, 3, 2, 2, 1, 1)), // Level 20
        )

        fun mold() = Character(
            name = "",
            race = Pair("Phony Race", Library.phonyInstance()),
            background = Pair("Phony Background", Library.phonyInstance()),
            classes = mutableMapOf(),
            abilities = loadAbilities().toMutableMap()
        )

        // data class AbilityDesc(val name: String, val instance: InstanceVal, val score: Int)
        fun loadAbilities() =
            Library.typesByKind("Ability").map { ability ->
                val inst = InstanceVal(ability, posInit)
                inst.getString("abbrev", posInit).flatMap { abbrev ->
                    inst.getName(posInit)
                        .map { name -> AbilityDesc(name, inst, 0) }
                        .map { desc -> Pair(abbrev, desc) }
                }
            }.filterRight().associate { it }
    }
}

object CharacterData {
    private val _characters = mutableStateOf(listOf<Either<Pair<String, CMLException>, InstanceVal>>())
    val characters: State<List<Either<Pair<String, CMLException>, InstanceVal>>> = _characters
    private val _loadedCharacters = mutableStateOf(listOf<Either<CMLException, Character>>())
    val loadedCharacters: State<List<Either<CMLException, Character>>> = _loadedCharacters

    val runtimeLoadPos = PosInfo("<runtime:load>", 0, 0)
    val runtimeRenderPos = PosInfo("<runtime:render>", 0, 0)

    fun clear() {
        _characters.value = listOf()
        _loadedCharacters.value = listOf()
    }

    fun setError(index: Int, message: String) {
        setError(index, CMLException(message))
    }

    fun setError(index: Int, message: CMLException) {
        _characters.value = _characters.value.updateGet(
            index,
            Pair(_characters.value[index].fold({ it.first }, { it.type.name }), message).left()
        )
    }

    fun setError(v: Character, m: CMLException) {
        _characters.value = _characters.value.mapIndexed { idx, it ->
            it.fold({ it.left() }) {
                if(_loadedCharacters.value[idx].fold({ false }) { it === v })
                    Pair(it.type.name, m).left()
                else
                    it.right()
            }
        }
    }

    fun newCharacter(c: Character) {
        _loadedCharacters.value += c.right()
    }

    fun loadFromLibrary() {
        _characters.value = _characters.value + Library.typesByKind("Character").map { InstanceVal(it, runtimeLoadPos).right() }
        _loadedCharacters.value = characters.value.map { pre -> pre.mapLeft { it.second }.flatMap { Character.loadFromInstance(it) } }
    }

    fun saveAll() {
        _loadedCharacters.value.forEach {
            it.map { c -> Scripts.saveChar(c) }
        }
    }

    fun refreshUI() {
        _characters.value = characters.value
    }

    fun <T> handleCMLException(ex: CMLException, character: Either<CMLException, InstanceVal>, index: Int, res: T): T {
        if(character.isRight()) {
            ex.message?.let {
                CMLOut.addError(it)
            }
            setError(index, ex)
        }
        return res
    }
}

fun Int.toMod() = (Math.floorDiv(this - 10, 2))