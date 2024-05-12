import java.math.BigInteger;
import java.util.Random;

public class SimpleMultiplication {

    static int primitiveOperationCounter = 0;  // Make this static to access it in static methods

    public static void main(String[] args) {

        // Random digit length between 1 and 100
        Random random = new Random();
        int n = 1 + random.nextInt(100);

        // for(int n = 1; n < 10; n++) {
            // Generate random numbers of n digits
                String num1 = generateRandomNumber(n);
                String num2 = generateRandomNumber(n);

                System.out.println("X " + n + ": " + num1);
                System.out.println("Y " + n + ": " + num2);
                System.out.println("=================================================================\n");
                multiplyNumbers(num1, num2);

                // Using BigInteger to handle large integer calculations
                BigInteger bigNum1 = new BigInteger(num1);
                BigInteger bigNum2 = new BigInteger(num2);
                BigInteger result = bigNum1.multiply(bigNum2);

                System.out.println("Expected " + n + ": " + result);
                System.out.println("Total number of primitive operations: " + primitiveOperationCounter);
                System.out.println();
                System.out.println();
                primitiveOperationCounter = 0; // Reset the counter for the next run
        // }
    }


    
        

    private static String generateRandomNumber(int n) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int digit = (i == 0) ? 1 + (int)(Math.random() * 9) : (int)(Math.random() * 10);
            number.append(digit);
        }
        return number.toString();
    }//Time complexity and char big O
    
    private static void multiplyNumbers(String num1, String num2) {
        // Reverse the numbers to start multiplication from the rightmost digit
        num1 = new StringBuilder(num1).reverse().toString();
        num2 = new StringBuilder(num2).reverse().toString();

        primitiveOperationCounter += 2; // Increment for two reversals
    
        // For storing the results of each multiplication step
        int[][] partialProducts = new int[num2.length()][num1.length()];
        int[][] carriers = new int[num2.length()][num1.length()];

        primitiveOperationCounter += 2; // Increment for two array initializations
    
        primitiveOperationCounter += 1;
        // Loop through each digit of num2 (multiplier)
        for (int i = 0; i < num2.length(); i++) {

            primitiveOperationCounter += 4;
            primitiveOperationCounter += 1;

            // Loop through each digit of num1 (multiplicand)
            for (int j = 0; j < num1.length(); j++) {

                primitiveOperationCounter += 4;

                int carry = 0; // Reset carry for the next iteration
                primitiveOperationCounter += 1;

                int product = (num2.charAt(i) - '0') * (num1.charAt(j) - '0') + carry;
                primitiveOperationCounter += 7; // For addition, subtraction and multiplication operations

                partialProducts[i][j] = product % 10; // Store the ones digit as partial product
                primitiveOperationCounter += 2;

                carry = product / 10; // Calculate the new carry
                primitiveOperationCounter += 2;

                carriers[i][j] = carry; // Store the tens digit as carry 
                primitiveOperationCounter += 1;
            }
        }


    
        printResults(num1, num2, partialProducts, carriers);
    }
    
    private static void printResults(String num1, String num2, int[][] partialProducts, int[][] carriers) {

        BigInteger result = BigInteger.ZERO; // Correct initialization
        // Printing partial products and carriers
        for (int i = 0; i < num2.length(); i++) {
            char multiplierDigit = num2.charAt(i);  // Correct digit from multiplier

            System.out.print("Partial products for (" + new StringBuilder(num1).reverse().toString() + " x " + multiplierDigit + "): ");
            for (int j = num1.length() - 1; j >= 0; j--) {  // Reverse the loop to print from lowest to highest
                System.out.print(partialProducts[i][j]);
            }
            System.out.println();

            System.out.print("Carriers for (" + new StringBuilder(num1).reverse().toString() + " x " + multiplierDigit + "): ");
            for (int j = num1.length() - 1; j >= 0; j--) {  // Include the final carry and reverse the loop
                System.out.print(carriers[i][j]);
            }
            System.out.println();
        }

        primitiveOperationCounter += 1; // Increment for the loop

        for (int i = 0; i < num2.length(); i++) {
            primitiveOperationCounter += 3;
            primitiveOperationCounter += 1;

            for (int j = 0; j < num1.length(); j++) {

                primitiveOperationCounter += 3;
                // Calculate the value for each digit and carrier, shifted by its position
                BigInteger baseValue = BigInteger.valueOf(partialProducts[i][j] + carriers[i][j] * 10);
                primitiveOperationCounter += 4;

                BigInteger positionalValue = baseValue.multiply(BigInteger.TEN.pow(i + j));
                primitiveOperationCounter += 4;

                result = result.add(positionalValue);
                primitiveOperationCounter += 2;
            }
        }
    
        System.out.println("=================================================================\n");
        System.out.println("Actual " + num1.length() + ": " + result.toString());
    }   
}
