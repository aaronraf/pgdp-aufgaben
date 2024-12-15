package pgdp.megamerge;

import java.util.Arrays;

public class MegaMergeSort {

    /**
     * Sorts the array using mega merge sort with div splits
     *
     * @param array the array to be sorted
     * @param div   the split factor
     * @return the sorted array
     */
    protected int[] megaMergeSort(int[] array, int div) {
        return megaMergeSort(array, div, 0, array.length);
    }

    /**
     * Sorts the array using mega merge sort with div splits in the defined range
     *
     * @param array the array to be sorted
     * @param div   the split factor
     * @param from  the lower bound (inclusive)
     * @param to    the upper bound (exclusive)
     * @return the sorted array
     */
    protected int[] megaMergeSort(int[] array, int div, int from, int to) {
        if (to - from < 1) {
            return new int[0];
        } else if (to - from == 1) {
            return new int[] { array[from] };
        }

        int dividingFactor = (to - from) / div;
        int restFactor = (to - from) % div;
        int partition = dividingFactor == 0 ? restFactor : div;
        int[][] result = new int[partition][];
        int left = from;

        for (int i = 0; i < partition; i++) {
            int right = left + dividingFactor;
            if (i < restFactor) {
                right++;
            }
            result[i] = megaMergeSort(array, div, left, right);
            left = right;
        }

        return merge(result, 0, result.length);
    }

    /**
     * Merges all arrays in the given range
     *
     * @param arrays to be merged
     * @param from   lower bound (inclusive)
     * @param to     upper bound (exclusive)
     * @return the merged array
     */
    protected int[] merge(int[][] arrays, int from, int to) {
        if (to - from < 1) {
            return new int[0];
        } else if (to - from == 1) {
            return arrays[from];
        }
        return merge(arrays[from], merge(arrays, from + 1, to));
    }

    /**
     * Merges the given arrays into one
     *
     * @param arr1 the first array
     * @param arr2 the second array
     * @return the resulting array
     */
    protected int[] merge(int[] arr1, int[] arr2) {
        int index1 = 0;
        int index2 = 0;
        int length1 = arr1.length;
        int length2 = arr2.length;

        int[] result = new int[length1 + length2];
        int i = 0;
        for ( ; index1 < length1 && index2 < length2; i++) {
            if (arr1[index1] < arr2[index2]) {
                result[i] = arr1[index1++];
            } else {
                result[i] = arr2[index2++];
            }
        }

        for ( ; index1 < length1; i++) {
            result[i] = arr1[index1++];
        }

        for ( ; index2 < length2; i++) {
            result[i] = arr2[index2++];
        }

        return result;
    }

    public static void main(String[] args) {
        MegaMergeSort mms = new MegaMergeSort();
        int[] arr = new int[]{1, 2, 6, 7, 4, 3, 8, 9, 0, 5};
        int[] res = mms.megaMergeSort(arr, 4);
        System.out.println(Arrays.toString(res));
    }
}
