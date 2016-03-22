package com.karneyenka.numberstest

import groovy.transform.CompileStatic

@CompileStatic
class PhoneNumber {
    String value
    List<String> aliases


    @Override
    public String toString() {
        return aliases?.
                collect { String alias -> "$value: $alias" }?. //format each line as "number: alias"
                join("\n")  //new line for each alias
    }
}
