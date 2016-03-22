package com.karneyenka.numberstest

import static groovyx.gpars.GParsExecutorsPool.withPool

public class App {

    public static void main(String[] args) {
        long time = System.currentTimeMillis()
        Dictionary dictionary = new Dictionary(new FileInputStream("data/dictionary.txt"))
        PhraseBuilder phraseBuilder = new PhraseBuilder(dictionary)
        PhoneNumberFactory factory = new PhoneNumberFactory(phraseBuilder)

        withPool(Runtime.getRuntime().availableProcessors()) {  //split between cores for faster processing
            new FileInputStream("data/input.txt")
                    .readLines()//iterate lines
                    .collectParallel { String line -> factory.create(line) } //convert lines to PhoneNumbers
                    .findAll { PhoneNumber number -> number.phrases }  //find numbers with has phrases
                    .each { PhoneNumber number -> println number.toString() } //print to output
        }


        println "Execution time: ${System.currentTimeMillis() - time}ms"
    }


}
