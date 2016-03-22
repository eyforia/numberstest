import com.karneyenka.numberstest.Dictionary
import com.karneyenka.numberstest.PhoneNumber
import com.karneyenka.numberstest.PhoneNumberFactory
import com.karneyenka.numberstest.PhraseBuilder

import static groovyx.gpars.GParsExecutorsPool.withPool

public class App {

    public static void main(String[] args) {
        assert args.length == 2, "Usage: java -jar numbers.jar <Input File> <Dictionary File>\n" +
                "e.g. java -jar input.txt dictionary.txt\n"

        def inputFileName = args[0]
        def dictionaryFileName = args[1]

        long time = System.currentTimeMillis()
        println "Loading dictionary from $dictionaryFileName..."
        Dictionary dictionary = new Dictionary(new FileInputStream(dictionaryFileName))
        PhraseBuilder phraseBuilder = new PhraseBuilder(dictionary)
        PhoneNumberFactory factory = new PhoneNumberFactory(phraseBuilder)

        int numCores = Runtime.getRuntime().availableProcessors()
        println "Processing on $numCores cores ..."
        withPool(numCores) {  //split between cores for faster processing
            new FileInputStream(inputFileName)
                    .readLines()//iterate lines
                    .collectParallel { String line -> factory.create(line) } //convert lines to PhoneNumbers
                    .findAll { PhoneNumber number -> number.phrases }  //find numbers with has phrases
                    .each { PhoneNumber number -> println number.toString() } //print to output
        }


        println "Execution time: ${System.currentTimeMillis() - time}ms"
    }


}
