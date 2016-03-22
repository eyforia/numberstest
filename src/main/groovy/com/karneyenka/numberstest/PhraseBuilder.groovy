package com.karneyenka.numberstest

import groovy.transform.CompileStatic

@CompileStatic
class PhraseBuilder {

    Dictionary dictionary

    PhraseBuilder(Dictionary dictionary) {
        this.dictionary = dictionary
    }


    List<String> getPhrases(String rawNumber) {
        String filteredNumber = rawNumber.findAll { Character.isDigit(it as char) }.join("")    //filter non-digits

        List<Word> aliases = dictionary.getMatchingWords(filteredNumber) //all matching words
        List<Phrase> possibleMatches = getPossibleMatches(new Phrase(new Word(filteredNumber)), aliases)

        def validMatches = possibleMatches.findAll { Phrase phrase -> phrase.isValid() } //find all valid phrases

        return validMatches.collect { Phrase phrase -> phrase.toString() } //format output
    }

    List<Phrase> getPossibleMatches(Phrase phrase, List<Word> aliases) {
        List<Phrase> result = []
        def matchingAliases = aliases.findAll { Word alias -> phrase.lastWord.startsWith(alias) } //find all aliases matching remaining raw number

        if (matchingAliases) {  //can use alias
            matchingAliases.each { Word alias ->
                Phrase updatedPhrase = phrase.replaceLastWordWith(alias)
                result.add(updatedPhrase)

                if (updatedPhrase.hasMoreRawNumbers()) {
                    def subResults = getPossibleMatches(updatedPhrase, aliases)
                    result.addAll(subResults)
                }
            }
        } else {    //cannot use alias - skip 1 char
            Phrase substring = phrase.next() //skip 1 character forward
            if (substring) {
                def substringMatches = getPossibleMatches(substring, aliases)
                result.addAll(substringMatches)
            }
        }


        return result
    }


}
