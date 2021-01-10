package algorithm.sort;

public class InsertSort {
    public static void sort(Comparable[] a) {
        int length = a.length;
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 && compare(a[j-1], a[j]); j--) {
                exchange(a, j, j - 1);
            }
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
