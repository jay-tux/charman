# CML
*An introduction to the programming language Charman uses. This introduction assumes you're somewhat familiar with programming in general.*

You can find the full grammar ([lexer](../antlr/cmlLexer.g4) and [parser](../antlr/cmlParser.g4) in the `../antlr/` directory).

Note that all sample code uses angle brackets (`<>`) to indicate placeholders.  
CML uses single-line comments started by `//`.

## Defining Data
### Simple Data Types
The main idea to CML is defining all the custom data you'll need. This is done using the `data` keyword:
```cml
data <kind> <kind> {
  // content
}
```
Every data type has both a kind an a name. This kind is used to group similar data types (by default, `Race`, `Class`, `Background` and such are looked for specially).

Data types can have any number of member variables (`field <name> = <initial value>;`) and member functions (`fun <name>(<args>) { <statements> }`).

### Template Types
If you have many data types of the same kind (say, simple traits), you can define a template:
```cml
template <kind>[<argument names>] { <definition> }
```
In the body of a template, you can use the angle-bracket syntax to refer to the template arguments, see the example below:

```cml
template Trait[name, desc] {
    field name = <name>;
    field desc = <desc>;
}
```
In this example, you can see that the argument names are `name` and `desc`, and they are used as "placeholders" for the initial values of the `name` and `desc` fields (the fields don't have to be synonymous with the argument names). You can also use template arguments in member functions. Note that these arguments can be any arbitrary expression, so you might have to use parentheses for priority when using them.

#### Variant Templates
If you want to have two different templates with the same kind (say, having a template for `Item`s and a special case of that template for `Armor` items), you can define an as-template:
```cml
template <name> as <kind>[<argument names>] { <definition> }
```
In this case, you'd instantiate the template (see later) using its name, but the instantiated types will be of the required kind.

Example (unimportant code is replaced by `...`):
```cml
template Item[...] { ... }
template Armor as Item[...] { ... }
```
In this example, you can use the `Armor` template to define a type of kind `Item`, but with the specifics of `Armor`.

### Instance Types
You can create a type derived from a template by instancing it:
```cml
instance <template> <name>[<argument values>];
```

For example, with the `Trait` template we used earlier:
```cml
instance Trait Darkvision60[
    "Darkvision (60 ft)", 
    "You can see in dim light within 60 feet of you ..."
];
```

## Global Constants
You can define global constant values using the following syntax:
```cml
glob <name> = <value>;
```
These add global values available to all code to the program.

## Functions
You can define both member functions (functions inside of a `data` or `template`) and free functions:
```cml
fun <name>(<arguments>) {
  <statements>
}
```
These functions work the way you'd expect. They can return values, if you use the `return` keyword.

## Statements
CML mostly has the same statements you're familiar with from other programming languages:
```cml
<expression>;                                         // just executes the expression, throwing away any results
var <name> = <expression>;                            // declares a new variable and initializes it
<variable> = <expression>;                            // overwrites a variable (or field)
if(<condition>) { <body true> }                       // typical conditional
if(<condition>) { <body true> } else { <body false> } // typical if-then-else
while(<condition>) { <body> }                         // typical while loop
for(<name> in <range>) { <body> }                     // loops over an iterable/range
break;                                                // breaks out of the closest loop
return;                                               // returns from a function
return <expression>;                                  // returns a value from a function
```

Most notably, CML doesn't have an `else if` (at the moment). This might be implemented in later versions. Similarly, there is a known bug (see issue #3) that sometimes disallows `break` in subscopes.

## Expressions
Once again, CML resembles most programming languages you're familiar with. 
 - It has the typical operators: mathematical (`+, -, *, /, %`), relational (`<, >, <=, >=, ==, !=`), logical (`!, &&, ||`) and bitwise (`&, |, ^`).
 - It uses the parentheses for priority.
 - You can access variables by name, and fields of variables by `<name>.<field name>`.
 - You can index into iterables (lists and ranges by index only, dicts by key).
 - You can call functions by using typical function call syntax, and similarly for member functions.

## Values
Currently, CML has 9 value types (plus a tenth for functions that return nothing):
 - Boolean values (`true` and `false`);
 - Integers (using typical integer literals);
 - Floating point values (using typical syntax; doesn't use a `f` suffix);
 - Strings (notably, they can be multiline strings! Strings are delimited by `"` and can't contain any `"` characters);
 - Dice values
   - You can create simple dice expressions like `1d6`, `12d100`, ...
   - You can create use expressions like this `(expression)d(expression)`
   - Also works with a capital `D`
 - Lists (they are created using the `[ <value>, <value>, <value> ]` syntax);
 - Ranges
   - You can create an inclusive ranges (up to and including endpoint) using `<value> ... <value>`
   - You can create exclusive ranges (up to but not including endpoint) using `<value> until <value>`
 - Dictionaries (aka maps, created using `{ <key> = <value>, <key> = <value> }`);
 - Instantiated values; these are values of user-defined data types and are constructed using `.<data type name>`.
