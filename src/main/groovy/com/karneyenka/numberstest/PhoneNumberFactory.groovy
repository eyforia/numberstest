package com.karneyenka.numberstest

import groovy.transform.CompileStatic

@CompileStatic
class PhoneNumberFactory {
    PhraseBuilder phraseBuilder

    PhoneNumberFactory(PhraseBuilder phraseBuilder) {
        this.phraseBuilder = phraseBuilder
    }

    PhoneNumber create(String value) {
        String filteredValue = value.findAll { Character.isDigit(it as char) }.join("")    //filter non-digits
        List<String> aliases = phraseBuilder.getAliases(filteredValue)
        return new PhoneNumber(value: value, aliases: aliases)
    }

}
