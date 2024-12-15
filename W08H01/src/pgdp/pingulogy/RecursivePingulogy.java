package pgdp.pingulogy;

import java.util.HashMap;

public class RecursivePingulogy {

    private static final HashMap<Integer, Long> pinguSequenceHashMap = new HashMap<>();

    public static long pinguSequenceRec(int n, int p0, int p1, int p2) {
        if (n == 0) {
            return p0;
        } else if (n == 1) {
            return p1;
        } else if (n == 2) {
            return p2;
        }
        pinguSequenceHashMap.clear();
        pinguSequenceHashMap.put(0, (long) p0);
        pinguSequenceHashMap.put(1, (long) p1);
        pinguSequenceHashMap.put(2, (long) p2);

        return psHelper(n);
    }

    private static long psHelper(int n) {
        if (n < -122 || n > 145) {
            return -1;
        }

        if (pinguSequenceHashMap.containsKey(n)) {
            return pinguSequenceHashMap.get(n);
        }

        long value = 0;
        if (n < 0) {
            long nTemp = psHelper(-n);
            value = 2 * nTemp;
        } else {
            long n1 = psHelper(n - 1);
            long n2 = psHelper(n - 2);
            long n3 = psHelper(n - 3);
            value = n1 - n2 + 2 * n3;
        }

        pinguSequenceHashMap.put(n, value);
        return value;
    }

    public int pinguF(int n) {
        if (n < 0) {
            return 0;
        } else if (n == 0) {
            return 1;
        }

        return n - pinguM(pinguF(n - 1));
    }

    public int pinguM(int n) {
        if (n <= 0) {
            return 0;
        }

        return n - pinguF(pinguM(n - 1));
    }

    public static int pinguCode(int n, int m) {
        return pcHelper(n, m, 0);
    }

    private static int pcHelper(int n, int m, int zwischen) {
        if (n == 0) {
            return m + zwischen;
        } else if ((n + zwischen) % 2 == 0) {
            return pcHelper(m, n / 2, zwischen + n / 2);
        }

        return pcHelper(n - 1, m / 2, zwischen + m);
    }


    // task 4
    public static String pinguDNA(int f, int m) {
        if (f == 0 && m == 0) {
            return "";
        }

        if (f == 0) {
            return pinguDNA(f, m >> 1 ) + "A";
        } else if (m == 0) {
            return pinguDNA(f >> 1, m) + "T";
        }

        if (f % 2 == m % 2) {
            if (f < m) {
                return pinguDNA(f >> 1, m >> 1) + "GA";
            } else if (f > m) {
                return pinguDNA(f >> 1, m >> 1) + "GT";
            }
            return pinguDNA(f >> 1, m >> 1) + "GC";
        }

        if (f % 2 == 1) {
            return pinguDNA(f >> 1, m >> 1) + "TC";
        }
        return pinguDNA(f >> 1, m >> 1) + "AC";
    }


    /**
     * For testing change the int value testTask with the
     * corresponding number of the task in the switch stmt below.
     */

    public static void main(String[] args) {
        int testTask = 1;
        switch (testTask) {
            case 1:
                System.out.println("Task 1 example output");
                for (int i = 0; i < 145; i++) {
                    System.out.println(i + ": " + pinguSequenceRec(i, 1, 1, 2));
                }
                break;
            case 2:
                /**
                 * For better testing, pinguF and pinguM are not static.
                 * Hence, you have to initialize a new RecursivePingulogy Object and
                 * call the methods on that instance, as you can see below.
                 */
                RecursivePingulogy rp = new RecursivePingulogy();
                System.out.print("Task 2 example output\npinguF: ");
                System.out.print(rp.pinguF(0));
                for (int i = 1; i < 10; i++) {
                    System.out.print(", " + rp.pinguF(i));
                }
                System.out.print("\npingM: ");
                System.out.print(rp.pinguM(0));
                for (int i = 1; i < 10; i++) {
                    System.out.print(", " + rp.pinguM(i));
                }
                break;
            case 3:
                System.out.println("Task 3 example output");
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.println(i + ", " + j + ": " + pinguCode(i, j));
                    }
                    System.out.println("----------");
                }
                break;
            case 4:
                System.out.println("Task 4 example output");
                System.out.println("pinguDNA(21, 25) = " + pinguDNA(21, 25));
                break;
            default:
                System.out.println("There are only 4 tasks!");
                break;
        }
    }

}
