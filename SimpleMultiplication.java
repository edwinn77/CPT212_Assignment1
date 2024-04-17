import java.math.BigInteger;
import java.util.Random;

public class SimpleMultiplication {

    public static void main(String[] args) {
        Random random = new Random();

        int n = 1 + random.nextInt(9); // Random digit length between 1 and 9
        // int n = 5; // Example digit length, you can change this based on your requirements
    
        // Generate random numbers of n digits
        String num1 = generateRandomNumber(n);
        String num2 = generateRandomNumber(n);
        
        // String num1 = "12346"; // Example number 1
        // String num2 = "54321"; // Example number 2

        System.out.println("Multiplying " + num1 + " and " + num2);
        multiplyNumbers(num1, num2);

        // Using BigInteger to handle large integer calculations
        BigInteger bigNum1 = new BigInteger(num1);
        BigInteger bigNum2 = new BigInteger(num2);
        BigInteger result = bigNum1.multiply(bigNum2);

        System.out.println("The result is: " + result);
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
    
        // For storing the results of each multiplication step
        int[][] partialProducts = new int[num2.length()][num1.length()];
        int[][] carriers = new int[num2.length()][num1.length()];
    
        // Loop through each digit of num2 (multiplier)
        for (int i = 0; i < num2.length(); i++) {
            // Loop through each digit of num1 (multiplicand)
            for (int j = 0; j < num1.length(); j++) {
                int carry = 0; // Reset carry for the next iteration
                int product = (num2.charAt(i) - '0') * (num1.charAt(j) - '0') + carry;
                partialProducts[i][j] = product % 10; // Store the ones digit as partial product
                carry = product / 10; // Calculate the new carry
                carriers[i][j] = carry; // Store the tens digit as carry 
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

        for (int i = 0; i < num2.length(); i++) {
            for (int j = 0; j < num1.length(); j++) {
                // Calculate the value for each digit and carrier, shifted by its position
                BigInteger baseValue = BigInteger.valueOf(partialProducts[i][j] + carriers[i][j] * 10);
                BigInteger positionalValue = baseValue.multiply(BigInteger.TEN.pow(i + j));
                result = result.add(positionalValue);
            }
        }
    
        System.out.println("The calculated result is: " + result.toString());
    }   
}
