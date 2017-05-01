
# NumericInputEdittext
Android EditText extension for validate numeric input values.

This library contains four main UI element :

- **IntegerInputEdittext**
    for integer input, set interval e.g. [ 0 , 100 ]

- **LongInputEdittext**
    for long input, set interval e.g. [ 50000l , 150000l ]

- **FloatInputEditText**
    for float value input, set interval e.g. ] 0f , 100f ]

- **DoubleInputEdittext**
   for double value input, set interval e.g. ] 0d , 1000000000d ]
***
## Features
- **Set interval** for valid numeric input from attribute string.
- **Get value directly** from EditText with one line
- **Validate** with one line
- **Autocorrect** the values


![demo1](https://github.com/Theophrast/NumericInputEditText/blob/master/screenshots/demo1.gif)

***
## How to use
Set the interval:

```java
    IntegerInputEditText et_Int = (IntegerInputEditText) findViewById(R.id.et_intinput);
    et_Int.setValidInterval("[0,50]")
```
Validate, show error messages, and autocorrect the wrong value:
```java
    et_Int.setAutoCorrectOnError(true);
    et_Int.setShowMessageOnError(true);
    boolean isInputValueValid = et_Int.isValid();
```
Get value directly from Edittext:

```java
    Integer result = et_Int.getValue();
```

You can also specify the parameters from layout:
```xml
  <com.theophrast.forgivingui.numericinputedittext.ui.IntegerInputEditText
        xmlns:numericinput="http://schemas.android.com/apk/res-auto"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/et_intinput"
        numericinput:showMessageOnError="true"
        numericinput:autoCorrectOnError="true"
        numericinput:validRange="[0,100]"
        />
```
To float value input use this:

```xml
   <com.theophrast.forgivingui.numericinputedittext.ui.FloatInputEditText
        xmlns:numericinput="http://schemas.android.com/apk/res-auto"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/et_floatinput"
        numericinput:showMessageOnError="true"
        numericinput:autoCorrectOnError="true"
        numericinput:correction="0.1"
        numericinput:validRange="]0f,1f]"
        />

```
Check the sample for more details.

## Gradle
```groovy
    dependencies{
        ...
        compile 'com.theophrast.forgivingui.numericinputedittext:numericinputedittext:1.0'
    }
```

***
## Interval set
The valid interval of a NumericInputEditText can be specified with a String.
Use **[valueMin , valueMax ]**  brackets to include the endpoints of the interval,
or use **]valueMin , valueMax[**  brackets to exclude them.
You can also combine the brackets.
Examples:
>
        [ minimum_enabled_value , maximum_enabled_value ]
        or
        ] minimum_not_enabled_value , maximum_not_enabled_value [
        or
        ] minimum_not_enabled_value , maximum_enabled_value ]
        or
         [ minimum_enabled_value , maximum_not_enabled_value [



### Examples for interval set

##### Input interval for Dice rolling (int):

Enabled values are 1, 2, 3, 4, 5 and 6.

```java
String interval = "[1,6]"
```
from layout.xml:
```xml
        numericinput:validRange="[0,100]"
```
##### Input interval for a material temperature in laboratory (float - °C):
Enabled values are from ‒273,15f to positive infinite
```java
String interval = "[‒273,15f,+["
```

##### Input interval for measured length (float):
In this case the enabled values are positive float numbers without maximum limit, but

0f value is not enabled.
```java
String interval = "]0,+["
```


## License
```
Copyright 2017 Janos Jakub

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
