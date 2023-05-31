# CML - Base Library
*A list of basic functions (no related to D&D specifically) that comes with the current version of CML. Future functions may be added on an as-needed basis. Don't hesitate to submit an issue or pull request with additional functions you may need.*

## Overview
 - [appendStr](./CML_stdlib.md#appendStr)
 - [appendList](./CML_stdlib.md#appendList)
 - [empty](./CML_stdlib.md#empty)
 - [inDict](./CML_stdlib.md#inDict)
 - [inList](./CML_stdlib.md#inList)
 - [isInt](./CML_stdlib.md#isInt)
 - [log](./CML_stdlib.md#log)
 - [raise](./CML_stdlib.md#raise)
 - [replace](./CML_stdlib.md#replace)
 - [split](./CML_stdlib.md#split)
 - [trim](./CML_stdlib.md#trim)

## appendStr
```cml
appendStr(args...)
```
*Arguments:* any number of values  
*Return value:* a string

Converts all values to their string representation, then concatenates them into one big string and returns it.

## appendList
```cml
appendList(l, val)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `l` | list | The list to append to
 `val` | any value | The value to append
 
*Return value:* a list

Creates a new list containing all the values in the original list, and appended with the new value.

## empty
```cml
empty(l)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `l` | list | The list to check
 
*Return value:* a boolean

Checks whether or not a list is empty.

## inDict
```cml
inDict(dict, key)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `dict` | dict | The dictionary to search
 `key` | any value | The key to search for
 
*Return value:* a boolean

Checks whether or not the given key exists in the given dictionary. Returns `true` if the key was found, otherwise `false`.

## inList
```cml
inList(l, val)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `l` | list | The list to search
 `val` | any value | The value to search for
 
*Return value:* a boolean

Checks whether or not the given value exists in the given list. Returns `true` if the value was found, otherwise `false`.

## isInt
```cml
isInt(val)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `val` | any value | The value to check
 
*Return value:* a boolean

Checks whether or not the given value is an integer. Returns `true` when given an integer, `false` otherwise.

## log
```cml
log(val)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `val` | any value | The value to print
 
*Return value:* nothing

Prints the string representation of the given value to the console.

## raise
```cml
raise(val)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `val` | any value | The error value to throw
 
*Return value:* nothing

Throws the given value as an error.

## replace
```cml
replace(str, search, replace)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `str` | string | The string in which to search and replace
 `search` | string | The substring to search for
 `replace` | string | The substring to replace each occurence with
 
*Return value:* a string

Creates a new string that's a copy of `str`, but where each instance of `search` is replaced by `replace`.


## split
```cml
split(str, by)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `str` | string | The string to split
 `by` | string | The substring to use as delimiter
 
*Return value:* a list

Splits the given string by the given delimiters into a list.

## trim
```cml
trim(str)
```
*Arguments:* 

 **Argument Name** | **Expected Type** | <i></i>
 ---|---|---
 `str` | string | The string to trim
 
*Return value:* a string

Removes all leading and trailing whitespace from the given string.
