package com.karneyenka.numberstest

import groovy.transform.CompileStatic

@CompileStatic
class PhoneNumber {
    String value
    List<String> phrases


    @Override
    public String toString() {
        return phrases?.
                collect { String phrase -> "$value: $phrase" }?. //format each line as "number: phrase"
                join("\n")  //new line for each phrase
    }
}
