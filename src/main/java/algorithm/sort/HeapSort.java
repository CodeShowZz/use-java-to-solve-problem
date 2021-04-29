package algorithm.sort;

import edu.princeton.cs.algs4.Heap;

import java.util.Arrays;

/**
 * 目前记录:7分35秒
 *
 * @author junlin_huang
 * @create 2021-03-24 下午7:55
 **/

public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {5, 8, 4, 2, 6};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, length);
        }
        for (int i = length - 1; i >= 0; i--) {
            CommonUtil.swap(arr,0, i);
            heapify(arr,0,i);
        }

    }

    public static void heapify(int[] arr, int i, int n) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        if (i != largest) {
            CommonUtil.swap(arr, i, largest);
            heapify(arr, largest, n);
        }
    }
}