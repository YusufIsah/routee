/**
 * This class create a function (doRemake) that takes a string of words and
 *
 * -Move the first letter of each word to the end of the word.
 *
 * -Add "ay" to the end of the word.
 *
 * -Words starting with a vowel (a,e,i,o,u, A, E, I, O, U) simply have "way" appended to the end.
 *
 * -Be sure to preserve proper capitalization and punctuation.
 *
 * Examples:
 *
 * doRemake("Cats are great pets.")
 *
 * returns "Atscay areway reatgay etspay."
 *
 *
 *
 * doRemake("Tom got a small piece of pie.")
 *
 * returns "Omtay otgay away allsmay iecepay ofway iepay."
 *
 *
 *
 * doRemake("He told us a very exciting tale.")
 *
 * returns "Ehay oldtay usway away eryvay excitingway aletay."
 */

public class DoRemakeExerciseThree {

    public static void main(String[] args) {
        //run all test cases and pass
        System.out.println(doRemake("Cats are great pets."));
        System.out.println(doRemake("Tom got a small piece of pie."));
        System.out.println(doRemake("He told us a very exciting tale."));
    }

    /**
     * this method the take sentence as param and remake the sentence
     * @param sentence String
     * @return String
     */
    public static String doRemake(String sentence){

        String [] words = sentence.replace(".", "").split(" ");
        String way = "way";
        String ay = "ay";

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words){
            if (isFirstCharacterVowel(word)){
                word+=way;
            }else {
            word = appendAyToWord(word, ay);
            }
            stringBuilder.append(word).append(" ");
        }
       return capitalizeFirstLetter(stringBuilder.toString()).trim() + ".";
    }

    /**
     * this method check if the first letter of word is a vowel and return true else false
     * @param word String
     * @return boolean
     */
    public static boolean isFirstCharacterVowel(String word){
        return "aeiouAEIOU".contains(word.substring(0, 1));
    }

    /**
     * this method move the first letter to the end of the given word
     * and append a string value ay to the end of the word.
     * @param word String
     * @param ay String
     * @return String
     */
    public static String appendAyToWord(String word, String ay){
        String firstLetter = word.substring(0, 1);
        word = word.replace(firstLetter, "");
        word += firstLetter + ay;
        return word;
    }

    /**
     * this mehtod capitalize first letter of sentence or word
     * @param sentence String
     * @return String
     */
    public static String capitalizeFirstLetter(String sentence){
        String firstLetter = sentence.substring(0, 1).toUpperCase();
        String remainingLetters = sentence.substring(1).toLowerCase();
       return firstLetter + remainingLetters;

    }
}
