package algorithm.sort;

/**
 * @author junlin_huang
 * @create 2021-03-24 下午7:59
 **/

public class CommonUtil {


    public static void swap(int [] arr,int i,int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}