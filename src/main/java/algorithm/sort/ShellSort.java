package algorithm.sort;

/**
 * @author junlin_huang
 * @create 2021-01-11 上午12:00
 **/

public class ShellSort {

    public static void sort(Comparable[] a) {
        int length = a.length;
        int h = 1;
        while (h < length / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && !compare(a[j], a[j - h]); j -= h) {
                    exchange(a, j, j - h);
                }

            }
            h = h / 3;
        }
    }

    private static boolean compare(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        Comparable[] numbers = new Comparable[]{6, 5, 7, 2, 4, 1};
        sort(numbers);
        for (Comparable number : numbers) {
            System.out.print(number);
        }
    }


}