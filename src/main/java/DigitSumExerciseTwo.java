/**
 * This class create a function (digitSum) that accepts an integer and calculates the sum of it's digits.
 *
 * If the sum is greater than 9 repeats the calculation of the sum of it's digits until we get sum < 10.
 *
 * Returns the final sum.
 *
 *
 *
 * examples
 *
 * 10 -> 1 + 0 = 1 ... returns 1
 *
 * 38 -> 3 + 8 = 11 -> 1 + 1 = 2 ... returns 2
 *
 * 785 -> 7 + 9 + 5 = 21 -> 2 + 1 = 3  returns 3 => this should return 2 not 3
 * because (785 -> 7 + 8 + 5 = 20 -> 2 + 0 = 2 return 2)
 *
 * 99892 -> 9 + 9 + 8 + 9 + 2 = 37 -> 3 + 7 = 10 -> 1 + 0 = 1 returns 1
 */

public class DigitSumExerciseTwo {

    public static void main(String[] args) {
        //run all the test case and pass
        System.out.println(digitSum(10));
        System.out.println(digitSum(38));
        //this will return 2 not 3 as the sample data suggested
        System.out.println(digitSum(785));
        System.out.println(digitSum(99892));
    }

    /**
     * A recursive method that sum up all the digits in an integer and return the total
     * if is < 9 or else call digitSum method.
     * @param number int
     * @return int
     */
    public static int digitSum(int number){
        int finalSum = 0;
        if (number == 0) return 0;
        String [] stringNumbers = Integer.toString(number).split("");
        for (var strNum : stringNumbers){
            finalSum+= Integer.parseInt(strNum);
        }
        if (finalSum > 9){
          return digitSum(finalSum);
        }
        return finalSum;
    }
}
