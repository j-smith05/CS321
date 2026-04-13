/**
 * Generates twin primes within a given range. 
 * A twin prime is a pair of prime numbers that 
 * differ by 2, such as (3, 5) or (11, 13).
 * @author Jacob Smith
 */
public class TwinPrimeGenerator {

    /**
     * Finds the first twin prime pair in [min, max]
     * and returns the larger value.
     * @param min the lower bound
     * @param max the upper bound
     * @return the larger twin prime, or -1 if none found
     */
    public static int generateTwinPrime(int min, int max) {
        for (int i = Math.max(min, 3); i <= max; i++) {
            if (isPrime(i) && isPrime(i - 2)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks whether a number is prime.
     * @param num the number
     * @return true if prime, false otherwise
     */
    private static boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}