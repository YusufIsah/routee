/**
 * This class create a function (findSeven) that takes an array of numbers and return "Found"
 *
 * if the character 7 appears in the array of the numbers. Otherwise, return "there is no 7 in the array".
 *
 * Examples :
 *
 * findSeven([1, 2, 3, 4, 5, 6, 7]) ➞ "Found"
 *
 * // 7 contains the number seven.
 *
 * findSeven([8, 6, 33, 100]) ➞ "there is no 7 in the array"
 *
 * // None of the items contain 7 within them.
 *
 * findSeven([2, 55, 60, 97, 86]) ➞ "Found"
 *
 * // 97 contains the number seven.
 */

public class FindSevenExerciseOne {

    public static void main(String[] args) {
        //run all the test case and pass
        System.out.println(findSeven(new int[] {1, 2, 3, 4, 5, 6, 7}));
        System.out.println(findSeven(new int[] {8, 6, 33, 100}));
        System.out.println(findSeven(new int[] {2, 55, 60, 97, 86}));
    }

    public static String findSeven(int [] numbers){

        String found = "there is no 7 in the array";
        int expectedNumber = 7;

        //check for null or empty array
        if (numbers == null || numbers.length <= 0){
            return found;
        }
        for (int num : numbers){
            if (num == expectedNumber){
                 found = "Found";
                 break;
            }
            if (Integer.toString(num).length() > 1){
                if (isSeven(num)){
                    found = "Found";
                    break;
                }
            }
        }
        return found;
    }

    /**
     *  this method check a string value if there is 7 inside string characters
     *  and true if exist else false
     * @param number int
     * @return boolean
     */
    public static boolean isSeven(int number){
        return Integer.toString(number).contains("7");

    }

}
