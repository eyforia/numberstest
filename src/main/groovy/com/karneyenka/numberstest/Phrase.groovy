package com.karneyenka.numberstest

import groovy.transform.CompileStatic

@CompileStatic
class Phrase {
    List<Word> words = []

    Phrase(Word firstWord) {
        words.add(firstWord)
    }

    private Phrase() {
    }

    Word getLastWord() {
        return words ? words.last() : null
    }

    /**
     * replace one word with multiple
     * @param alias
     * @param words
     */
    Phrase replaceLastWordWith(Word alias) {
        Phrase result = new Phrase(
                words: this.words.collect { it == this.lastWord ? alias : it } //replace raw number with alias
        )

        Word suffix = lastWord - alias  //see if there's any raw numbers left

        if (suffix)
            result.words.add(suffix)    //add them to the end

        return result
    }

    boolean hasMoreRawNumbers() {
        return lastWord.isRawNumber()
    }

    /**
     * advance forward - 1 character
     * @return
     */
    Phrase next() {
        Phrase result = null
        if (lastWord.rawNumber && lastWord.number.length() > 1) {  //can advance ?

            // break last word into 2
            def word1 = new Word(lastWord.number.substring(0, 1))   //1 char
            def word2 = new Word(lastWord.number.substring(1))  //everything else

            result = new Phrase(
                    words: this.words - lastWord + word1 + word2
            )

        }
        return result
    }

    int size() {
        return words.sum { Word word -> word.number.length() } as int
    }

    boolean isValid() {
        return !toString()
                .replaceAll(" ", "")
                .collectReplacements { char c -> Character.isDigit(c) ? '*' : null }
                .contains("**")
    }

    @Override
    public String toString() {
        return words.collect { word -> word.value }.join(" ")
    }


}
