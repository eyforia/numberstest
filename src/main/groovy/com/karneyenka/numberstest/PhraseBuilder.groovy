package com.karneyenka.numberstest

import groovy.transform.CompileStatic

@CompileStatic
class PhraseBuilder {

    Dictionary dictionary

    PhraseBuilder(Dictionary dictionary) {
        this.dictionary = dictionary
    }

    List<String> getAliases(String rawNumber) {
        String filteredNumber = rawNumber.findAll { Character.isDigit(it as char) }.join("")    //filter non-digits

        List<Word> matchingWords = dictionary.getMatchingWords(filteredNumber) //all matching words
        List<Phrase> possibleMatches = getPossibleMatches(new Phrase(new Word(filteredNumber)), matchingWords)

        def validMatches = possibleMatches.findAll { Phrase phrase -> phrase.isValid() } //find all valid aliases

        return validMatches.collect { Phrase phrase -> phrase.toString() } //format output
    }

    List<Phrase> getPossibleMatches(Phrase phrase, List<Word> dictionary) {
        List<Phrase> result = []
        def matchingWords = dictionary.findAll { Word alias -> phrase.lastWord.startsWith(alias) } //find all aliases matching remaining raw number

        if (matchingWords) {  //can potentially be aliased
            matchingWords.each { Word alias ->
                Phrase updatedPhrase = phrase.replaceLastWordWith(alias)
                result.add(updatedPhrase)

                if (updatedPhrase.hasMoreRawNumbers()) {
                    def subResults = getPossibleMatches(updatedPhrase, dictionary)
                    result.addAll(subResults)
                }
            }
        } else {    //cannot use alias - skip 1 char
            Phrase substring = phrase.next() //skip 1 character forward
            if (substring) {
                def substringMatches = getPossibleMatches(substring, dictionary)
                result.addAll(substringMatches)
            }
        }

        return result
    }


}
