# CML-Charman Interaction
*This document aims to give you an idea of how Charman will use your CML code.*

## Prerequisite
Just a reminder that Charman only loads CML files (with the `.cml`) extension in its cache directory. You can find out which directory that is by looking at the CML Console in the app.  
Additionally, it might help to look at how the [SRD](https://github.com/jay-tux/SRDCML) is implemented.

## Races
Types are only considered a playable race if they are of kind `Race`; this means they are defined in your code as `data Race <Name> { }`.

Races are expected to have the following fields (or they are seen as invalid, which will be visible in the Console):
 - A field `name` of type `string`. This is the name that will show up in the UI and can differ from the type name.

Furthermore, they are expected to have the following functions:
 - `fun onSelect()`. This function is called when the race is selected by the user in the UI upon creating a new character. 
   - This function should be used to apply ability score increases and sub-race selections (this is a so-called choice-scope where you can use the `choose*` functions). 
   - If necessary, also allow the user to choose any other options that might be needed (e.g. the Dwarf's *Tool Proficiency* ability).
   - If necessary, don't forget to call your subclass's `onSelect`. 
   - Often, you'll want to call your race's `onRestore` function at the end (to avoid repeating a lot of code).
 - `fun onRestore(choices, level)`. This function is called when a character (with the given race) is loaded.
   - This is the function where you'll want to do the `addRacialTraits` calls.
   - The `choices` dictionary will contain all choices the player made regarding the race (and its subraces), each with the specified `tag` as key.
   - If your subrace requires restoring (maybe other user-created subraces might?), call its `onRestore` function here as well.
 - `fun onLevelUp(level)`. Use this function if either your race (or again, any of its subraces) wants to do something upon leveling up.
   - If necessary, this function is also run in a choice-scope, so you can use the `choose*` functions.

If any of your race's racial traits require restoring as well, give them an `onRestore(choices, level)` function (it will automatically be called if available). Most of your traits will be simple *name-and-description* traits, and for that you can use `instance Trait[name, description];`.

## Classes
Types are only considered a playable class if they are of kind `Class`; this means they are defined in your code as `data Class <Name> { }`.

Classes are expected to have the following fields:
 - A field `name` of type `string`. Analogous to races.
 - A field `hitDie` of type `dice`. Only the kind of die is important here; you can define it as `12d12`, but the system won't bother with the amount of dice.

Furthermore, they are expected to have the following functions:
 - `fun onSelect()`. This functions is called when the class is selected by the user in the UI upon creating a new character.
   - This is the prime place to add starting equipment, and allow the player to make all required choices.
   - You can ask the player to select a subclass here, but you can also delay it until later levels (note that you might have to call its `onSelect` function here as well).
   - Often, you'll want to call your class's `onRestore` function at the end (to avoid repeating a lot of code).
   - If you want the player to make any choices, that is possible using the `choose*` functions (this function is run in a choice-scope).
 - `fun onLevelUp(level)`. This function is called each time the player levels up. 
   - Here's where you want to call the `addClassTraits` function to add abilities to the player.
   - For ease of use, I recommend using a list of lists to represent the abilities the player will gain at each level, with a `0` to represent subclass abilities (which can then be detected upon calling by `isInt(level)`).- 
   - If you want the player to make any choices, that is possible using the `choose*` functions (this function is run in a choice-scope).
 - `fun onRestore(choices, level)`. The function is called whenever the player is loaded.
   - The important things to do here are, among others, restoring skill proficiencies, subclasses, saving throw proficiencies, spells (if any) and class abilities.
   - To restore class abilities, it might be easy to use a loop like this:
     ```cml
     for(l in 1 ... level) { onLevelUp(l); }
     ```
   - Don't forget to also restore the subclass using `subclass.onRestore(choices, level)`, if needed.

Additionally, there is a special function available to classes:
 - `fun onLateRestore(choices, level)`. This function is similar to the `onRestore` function, but has the benefit of being called after all other `onRestore` functions.
   - This is used e.g. by the SRD Bard's `Jack of All Trades` feature (which needs to know all skill proficiencies).

## Backgrounds
Backgrounds are very similar to [races](./CML_Charman.md), so I'll refer to that section instead. Note that the kind of a playable background should be `Background`.

## Others
For others, I'll refer to the SRD implementation, as these have their own templates (which are documented there). The templates contain the minimal requirements for their kinds.
