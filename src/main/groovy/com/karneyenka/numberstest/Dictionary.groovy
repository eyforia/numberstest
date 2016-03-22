package com.karneyenka.numberstest

import groovy.transform.CompileStatic

@CompileStatic
class Dictionary {
    static def encoding = [E: 0, J: 1, N: 1, Q: 1, R: 2, W: 2, X: 2, D: 3, S: 3, Y: 3, F: 4, T: 4, A: 5, M: 5, C: 6, I: 6, V: 6, B: 7, K: 7, U: 7, L: 8, O: 8, P: 8, G: 9, H: 9, Z: 9]

    private List<Word> words

    public Dictionary(InputStream is) {
        this.words = is.readLines().collect { String line -> new Word(line, toNumber(line)) } //convert to Words
    }

    List<Word> getMatchingWords(String number) {
        return words.findAll { Word word -> number.contains(word.number) }    //find ones contained in the number
    }


    private static String toNumber(String text) {
        return text.toUpperCase()
                .collect { String s -> encoding[s] as String }
                .findAll { it }
                .join("")
    }

}
