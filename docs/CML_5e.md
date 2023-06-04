# CML - D\&D 5e-Related Functions
*These are the functions that are implemented in the runtime. For types and functions that are implemented in CML, see the (upcoming) **SRD in CML** repository.*

## Overview
- [Functions not requiring user choices](#functions-not-requiring-user-choices)
	- [abilityIncrease](#abilityincrease)
	- [addAction](#addaction)
	- [addBackgroundTraits](#addbackgroundtraits)
	- [addClassTraits](#addclasstraits)
	- [addItem](#additem)
	- [addItemProficiencies](#additemproficiencies)
	- [addLanguages](#addlanguages)
	- [addMaxHP](#addmaxhp)
	- [addRacialTraits](#addracialtraits)
	- [addSaveProficiencies](#addsaveproficiencies)
	- [addSkillProficiencies](#addskillproficiencies)
	- [addSpell](#addspell)
	- [getAbilities](#getAbilities)
	- [getAbilityMod](#getabilitymod)
	- [getArmor](#getarmor)
	- [getProficiency](#getproficiency)
	- [modAC](#modac)
	- [setAC](#setac)
	- [setFullCaster](#setfullcaster)
	- [setHalfCaster](#sethalfcaster)
	- [setSpecialCaster](#setspecialcaster)
	- [setThirdCaster](#setthirdcaster)
- [Functions asking the user to make a decision](#functions-asking-the-user-to-make-a-decision)
	- [chooseDataByKind](#choosedatabykind)
	- [chooseFrom](#choosefrom)
	- [chooseItem](#chooseitem)
	- [chooseNByKind](#choosenbykind)
	- [chooseNCantrips](#choosencantrips)
	- [chooseNFrom](#choosenfrom)
	- [chooseNSpellsUpTo](#choosenspellsupto)

<!-- /TOC -->

## Functions not requiring user choices
### abilityIncrease
```cml
abilityIncrease(ability, amount)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `ability` | instance of a type of kind `Ability` | The ability whose score to increase
 `amount` | integer | The amount to increase (or decrease) by
 
*Return value:* nothing

Increases (or decreases, if `amount` is negative) a character's ability score. This is most notably used by races for their racial traits.

### addAction
```cml
addAction(action)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `action` | instance of a type of kind `Action` | The action to add
 
*Return value:* nothing

Adds an action available to the player.

### addBackgroundTraits
```cml
addBackgroundTraits(traits)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `traits` | list of instances of a type of kind `Trait` | The traits to add
 
*Return value:* nothing

Adds one or more traits to the player (the source being the player's background).

### addClassTraits
```cml
addClassTraits(name, traits)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `name` | string | name of the class which is the source of this trait
 `traits` | list of instances of a type of kind `LevelUpTrait` | The traits to add
 
*Return value:* nothing

Adds one or more traits to the player (the source being the given class).

### addItem
```cml
addItem(item)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `item` | instance of a type of kind `Item` | The item to add
 
*Return value:* nothing

Adds an item to the player's inventory, and, if needed, also its actions to the player's available actions.

### addItemProficiencies
```cml
addItemProficiencies(tags)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `tags` | list of strings | Item tags
 
*Return value:* nothing

Adds profiency with all items matching any of the tags to the player.

### addLanguages
```cml
addLanguages(languages)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `languages` | list of instances of a type of kind `Language` | The languages to add
 
*Return value:* nothing

Marks the player to be able to read, write and speak the given languages.

### addMaxHP
```cml
addMaxHP(amount)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `amount` | integer | amount of HP to grant
 
*Return value:* nothing

Increases (or decreases, if `amount < 0`) the player's max HP by the given amount.

### addRacialTraits
```cml
addRacialTraits(traits)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `traits` | list of instances of a type of kind `Trait` | The traits to add
 
*Return value:* nothing

Adds one or more traits to the player (the source being the player's race).

### addSaveProficiencies
```cml
addSaveProficiencies(abilities)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `abilities` | list of instances of a type of kind `Ability` | Abilities in which the player should become proficient
 
*Return value:* nothing

Makes the player proficient in saving throws from the given abilities.

### addSkillProficiencies
```cml
addSkillProficiencies(skills)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `skills` | list of instances of a type of kind `Skill` | Skills in which the player should become proficient
 
*Return value:* nothing

Makes the player proficient in the given skills.

### addSpell
```cml
addSpell(spell)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `spell` | instance of a type of kind `Spell` | Spell to add to the player's available spells
 
*Return value:* nothing

Adds the spell to the player's currently available spells, and its action to the player's available actions.

### getAbilities
```cml
getAbilities()
```
*Arguments:* None
 
*Return value:* list of instance of Ability

Gets all abilities known in the system.

### getAbilityMod
```cml
getAbilityMod(ability)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `ability` | instance of a type of kind `Ability` | Ability whose modifier to get.
 
*Return value:* int

Gets the player's current modifier for the given ability.

### getArmor
```cml
getArmor()
```
*Arguments:* None
 
*Return value:* list of instances of types of kind `Item`

Gets the player's currently equipped armor (the player might have more armor available).

### getProficiency
```cml
getProficiency()
```
*Arguments:* None
 
*Return value:* int

Gets the player's current proficiency bonus.

### modAC
```cml
modAC(delta)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `delta` | int | Amount to modify the player's AC by.
 
*Return value:* nothing

Adds a modifier to the player's AC (e.g. equipping a shield might call `modAC(2)`).

### setAC
```cml
setAC(ac)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `ac` | int | Base value to set the player's AC to.
 
*Return value:* nothing

Sets the player's base AC (any armor might call `setAC()` with some value). When nothing calls `setAC`, the base `10 + DEX mod` is used.

### setFullCaster
```cml
setFullCaster(level)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `level` | int | The caster's level.
 
*Return value:* nothing

Marks the player as a `level`-level full spellcaster (e.g. Bard, Cleric, Druid, Sorcerer, Wizard) in addition to any other classes that might grant the spellcasting ability (e.g. upon restoring, the Cleric class might call `setFullCaster(level)`).

### setHalfCaster
```cml
setHalfCaster(level)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `level` | int | The caster's level.
 
*Return value:* nothing

Marks the player as a `level`-level half spellcaster (e.g. Paladin, Ranger) in addition to any other classes that might grant the spellcasting ability (e.g. upon restoring, the Ranger class might call `setHalfCaster(level)`).

### setSpecialCaster
```cml
setSpecialCaster(slots, level)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `slots` | list of lists of integers | A 20 row x 9 column matrix of spell slot counts.
 `level` | int | The caster's level.
 
*Return value:* nothing

Marks the player as a special spellcaster (e.g. Warlock) of level `level`. The `slots` parameter describes how much spells slots of each level the player has available. Each row (sub-list) describes a player level, and each column describes how the amount of spell slots of the given level evolves.

### setThirdCaster
```cml
setThirdCaster(level)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `level` | int | The caster's level.
 
*Return value:* nothing

Marks the player as a `level`-level one-third spellcaster (e.g. Eldritch Knight Fighter, Arcane Trickster Rogue) in addition to any other classes that might grant the spellcasting ability (e.g., upon restoring, the Arcane Trickster subclass might call `setHalfCaster(level)`).

## Functions asking the user to make a decision
Each of these functions will trigger a popup (when called in a valid scope). This popup will show the user the available options and allow them to choose one or more (depending on the function call). Each choice is also registered (by its data name) so that it might be restored upon a reload.

### chooseDataByKind
```cml
chooseDataByKind(tag, kind)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `tag` | string | The name of the choice. Can be used later to restore it.
 `kind` | string | The kind of type to allow to be chosen from.
 
*Return value:* instance of a type of the chosen kind

Asks the user to choose one of the types with the given kind. This type is then constructed and returned. This can be used to e.g. allow the player to choose a subclass (by making subclasses all of a certain kind).

### chooseFrom
```cml
chooseFrom(tag, options)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `tag` | string | The name of the choice. Can be used later to restore it.
 `options` | list | The kind of type to allow to be chosen from.
 
*Return value:* the chosen option

Asks the user to choose one of the options. This option is then returned.

### chooseItem
```cml
chooseItem(tag, tags, options)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `tag` | string | The name of the choice. Can be used later to restore it.
 `tags` | list of string | Tags the items have to satisfy.
 `options` | list | Additional options (should be list of `Item` instances).
 
*Return value:* instance of a matching item, or one of the options

Asks the user to choose from either
 - any item that matches all given `tags` (e.g. a simple melee weapon), or
 - any option in the `options` list.
The chosen value is then returned (as instance).

### chooseNByKind
```cml
chooseNByKind(tag, n, kind)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `tag` | string | The name of the choice. Can be used later to restore it.
 `n` | int | Amount of choices the user can make.
 `kind` | string | The kind of type to allow to be chosen from.
 
*Return value:* list of instances of a type of the chosen kind

Asks the user to choose some (`n`) of the types with the given kind. The user cannot choose the same option more than once. This type is then constructed and returned. This can be used to e.g. allow the player to choose their class's Skill proficiencies (`chooseNByKind("prof", 2, "Skill")`).

### chooseNCantrips
```cml
chooseNCantrips(tag, n, class, base)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `tag` | string | The name of the choice. Can be used later to restore it.
 `n` | int | Amount of choices the user can make.
 `class` | string | The name of the class that grants the spells.
 `base` | list of instances of types of kind Spell | The base spell list of the user's class.
 
*Return value:* list of instances of kind Spell, all with level 0

Asks the user to select `n` unique cantrips from either the given `base` spell list, or from any spells that are marked as available to the class `class`. Both the `base` spell list and the marked spells are filtered by level. The chosen cantrips are aggregated into a list and then returned.

### chooseNFrom
```cml
chooseNFrom(tag, n, options)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `tag` | string | The name of the choice. Can be used later to restore it.
 `n` | int | Amount of choices the user can make.
 `options` | list | The options for the player
 
*Return value:* sub-list of length `n` of `options`

Asks the user to select `n` unique value from the given list. These values are then returned in a list.

### chooseNSpellsUpTo
```cml
chooseNSpellsUpTo(tag, n, class, base)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `tag` | string | The name of the choice. Can be used later to restore it.
 `n` | int | Amount of choices the user can make.
 `maxLvl` | int | Maximum level of the spells that can be chosen.
 `class` | string | The name of the class that grants the spells.
 `base` | list of instances of types of kind Spell | The base spell list of the user's class.
 
*Return value:* list of instances of kind Spell, all with level between (inclusive) 1 and `maxLvl`

Asks the user to select `n` spells from either the given `base` spell list, or from any spells that are marked as available to the class `class`. Both the `base` spell list and the marked spells are filtered by level. The chosen spells are aggregated into a list and then returned.
