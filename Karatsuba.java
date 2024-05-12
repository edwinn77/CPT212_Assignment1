import java.math.BigInteger;
import java.util.Random;

class Karatsuba {

    static BigInteger counter = BigInteger.ZERO;

    public static BigInteger mult(BigInteger x, BigInteger y) {
        if (x.compareTo(BigInteger.TEN) < 0 && y.compareTo(BigInteger.TEN) < 0) {
            counter = counter.add(BigInteger.valueOf(2)); // 1 multiplication and 1 return
            counter = counter.add(BigInteger.valueOf(2)); // 2 comparison
            return x.multiply(y);
        }

        counter = counter.add(BigInteger.valueOf(2)); // 2 Comparison

        int noOneLength = numLength(x);
        int noTwoLength = numLength(y);

        counter = counter.add(BigInteger.valueOf(4)); // 2 Assignment and 2 function calling

        int maxNumLength = Math.max(noOneLength, noTwoLength);
        counter = counter.add(BigInteger.valueOf(2)); // 1 assignment and 1 function calling

        BigInteger halfMaxNumLength = BigInteger.valueOf(maxNumLength).divide(BigInteger.valueOf(2)).add(BigInteger.valueOf(maxNumLength % 2));
        counter = counter.add(BigInteger.valueOf(4)); // 1 assignment and 3 arithmetic operations

        BigInteger maxNumLengthTen = BigInteger.TEN.pow(halfMaxNumLength.intValue());
        counter = counter.add(BigInteger.valueOf(2)); // 1 assignment and 1 function calling

        BigInteger a = x.divide(maxNumLengthTen);
        BigInteger b = x.mod(maxNumLengthTen);
        BigInteger c = y.divide(maxNumLengthTen);
        BigInteger d = y.mod(maxNumLengthTen);
        counter = counter.add(BigInteger.valueOf(8)); // 4 assignment, 4 arithmetic operations

        BigInteger z0 = mult(a, c);
        BigInteger z1 = mult(a.add(b), c.add(d));
        BigInteger z2 = mult(b, d);
        counter = counter.add(BigInteger.valueOf(8)); // 3 assignment, 2 arithmetic operations, 3 function calling

        BigInteger ans = z0.multiply(BigInteger.TEN.pow(halfMaxNumLength.intValue() * 2))
                .add(z1.subtract(z0).subtract(z2).multiply(BigInteger.TEN.pow(halfMaxNumLength.intValue()))
                        .add(z2));
        counter = counter.add(BigInteger.valueOf(10)); // 1 assignment and complex expression

        counter = counter.add(BigInteger.ONE); // 1 return
        return ans;
    }

    // Method to calculate length of the number
    public static int numLength(BigInteger n) {
        BigInteger noLen = BigInteger.ZERO;
        counter = counter.add(BigInteger.ONE); // 1 assignment

        while (n.compareTo(BigInteger.ZERO) > 0) {
            noLen = noLen.add(BigInteger.ONE);
            n = n.divide(BigInteger.TEN);
            counter = counter.add(BigInteger.valueOf(5)); // 1 comparison and 4 arithmetic operations
        }

        counter = counter.add(BigInteger.ONE); // while loop end
        counter = counter.add(BigInteger.ONE); // Returning length of number n
        return noLen.intValue();
    }
    

    public static void main(String[] args) {
        BigInteger MAX_VALUE = new BigInteger("30");
        Random r = new Random();

        for (int i = 0; i < MAX_VALUE.intValue(); i++) {
            counter = BigInteger.ZERO;
            BigInteger max = BigInteger.TEN.pow(i + 1); // n + 1 digits for x and y
            BigInteger min = BigInteger.TEN.pow(i); // n digits for x and y

            // Ensure max is greater than min
            if (max.compareTo(min) <= 0) {
                BigInteger temp = max;
                max = min.add(BigInteger.ONE);
                min = temp;
            }

            BigInteger range = max.subtract(min);
            BigInteger randomNumber1 = min.add(new BigInteger(range.bitLength(), r).mod(range));
            BigInteger randomNumber2 = min.add(new BigInteger(range.bitLength(), r).mod(range));

            BigInteger x = max.compareTo(min) > 0 ? randomNumber1 : randomNumber2;
            BigInteger y = max.compareTo(min) > 0 ? randomNumber2 : randomNumber1;

            System.out.println("X " + (i + 1) + ": " + x);
            System.out.println("Y " + (i + 1) + ": " + y);

            BigInteger expectedProduct = x.multiply(y);
            BigInteger actualProduct = mult(x, y);

            System.out.println("Expected " + (i + 1) + ": " + expectedProduct);
            System.out.println("Actual " + (i + 1) + ": " + actualProduct);
            System.out.println("Total number of primitive operations : " + counter + "\n");
        }
    }
}
