package pgdp.pingumath;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SAT {
    /*
     * 'efficient' power implementation
     *
     * This is part of the template, do not change this method!
     */
    private static long pow(long a, int b) {
        if (b < 0) {
            System.out.println("Unexpected input: negative b is not allowed!");
            return 0;
        } else if (b == 0)
            return 1;

        int result = 1;
        while (b > 0) {
            if ((b & 1) == 0) {
                a *= a;
                b >>>= 1;
            } else {
                result *= a;
                b--;
            }
        }
        return result;
    }


    public static boolean isPow(int i, long n) {
        if (i < 0) {
            return false;
        }

        for (int x = 1; ; x++) {
            long powerResult = pow(x, i);
            if (powerResult > n) {
                return false;
            } else if (powerResult == n) {
                return true;
            }
        }
    }


    private static long binom(long n, long k) {
        if (k > n - k) {
            k = n - k;
        }

        long acc = 1;
        for (long i = 1; i <= k; i++) {
            acc *= n - (k - i);
            acc /= i;
        }

        return acc;
    }


    public static boolean isCentralBin(long n) {
        if (n <= 0) {
            return false;
        }

        for (long i = 0; ; i++) {
            long binom = binom(2 * i, i);
            if (binom > n) {
                return false;
            } else if (binom == n) {
                return true;
            }
        }
    }


    public static boolean isJacobsthal(long n) {
        return isLucasLikeSequence(0,1,2,1, n);
    }


    public static boolean isLucasLikeSequence(long x0, long x1, int a, int b, long n) {
        if (x0 < 0 || x1 < 0 || a < 0 || b < 0 || n < 0) {
            return false;
        } else if (x0 == n || x1 == n) {
            return true;
        }

        long[] stack = new long[1000];     // memoization
        stack[0] = x0;
        stack[1] = x1;
        for (int i = 2; ; i++) {
            long current = a * stack[(i - 2) % 1000] + b * stack[(i - 1) % 1000];
            if (current == n) {
                return true;
            } else if (current > n) {
                return false;
            }

            stack[i % 1000] = current;
        }
    }

    /*
     * this method returns a String of the analysis of the input n
     *
     * This is part of the template, do not change this method!
     */
    public static String checkSpecial(long n) {
        String result = "Input: \t\t" + n + "\n";

        String[] sequenceNames = {"Square", "Cubic", "Quartic", "Central Bin.", "Jacobsthal", "Fibonacci"};
        Boolean[] sequenceResults = {isPow(2, n), isPow(3, n), isPow(4, n), isCentralBin(n), isJacobsthal(n),
                isLucasLikeSequence(0, 1, 1, 1, n)};

        result += "Results: ";
        if (Arrays.stream(sequenceResults).anyMatch(x -> x)) {
            String analysis = IntStream.range(0, sequenceNames.length).mapToObj(i -> {
                return sequenceNames[i] + ": \t" + (sequenceNames[i].equals("Cubic") ? "\t" : "")
                        + (sequenceResults[i] ? "Yes" : "No") + "\n";
            }).reduce("", (a, b) -> a + b);
            result += "Interesting!\n" + analysis;
        } else
            result += "Nothing special about this ...\n";
        return result;
    }

    public static void main(String[] args) {
        System.out.println(isLucasLikeSequence(0,1,1,1,21345));
    }
}
