package com.karneyenka.numberstest

import groovy.transform.CompileStatic

@CompileStatic
class Word {

    String value
    String number

    /**
     * for aliases
     * @param value
     * @param number
     */
    Word(String value, String number) {
        this.value = value
        this.number = number
    }

    /**
     * for raw numbers
     * @param value
     */
    Word(String value) {
        this.value = value
        this.number = value
    }

    Word minus(Word otherWord) {
        assert isRawNumber(), "can only subtract from raw numbers"
        assert startsWith(otherWord)
        return this.number == otherWord.number ? null   //null if nothing left
                : new Word(this.number.substring(otherWord.number.length()))    //or remaining substring
    }

    boolean startsWith(Word word) {
        return this.number.startsWith(word.number)
    }


    boolean isRawNumber() {
        return this.value == this.number
    }
}
