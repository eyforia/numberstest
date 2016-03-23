package com.karneyenka.numberstest

import spock.lang.Specification

class PhoneNumberFactoryTest extends Specification {

    void "happy path"() {
        given:
        Dictionary dictionary = new Dictionary(getClass().getResourceAsStream("/com/karneyenka/numberstest/small-dictionary.txt"))
        PhraseBuilder phraseBuilder = new PhraseBuilder(dictionary)
        PhoneNumberFactory factory = new PhoneNumberFactory(phraseBuilder)
        def input = getClass().getResourceAsStream("/com/karneyenka/numberstest/small-input.txt")
        def referenceOutput = getClass().getResourceAsStream("/com/karneyenka/numberstest/small-result.txt").text

        when: "reference file is processed"
        def result = input.text
                .split() //iterate lines
                .collect { String line -> factory.create(line) } //convert lines to PhoneNumbers
                .findAll { PhoneNumber number -> number.aliases }    //find only numbers with aliases
                .collect { PhoneNumber word -> word.toString() } //convert to strings
                .join("\n")
        println result

        then: "output is same as reference"
        result.readLines().sort() == referenceOutput.readLines().sort()

    }

}
