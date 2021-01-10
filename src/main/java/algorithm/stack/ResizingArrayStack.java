package algorithm.stack;

public class ResizingArrayStack<T> {

    private T[] array = (T[]) new Object[1];
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }


    public void push(T item) {
        if (size == array.length) {
            resize(2 * array.length);
        }
        array[size++] = item;
    }

    public T pop() {
        T item = array[size - 1];
        array[size - 1] = null;
        size--;
        if (size > 0 && size == array.length / 4) {
            resize(array.length / 2);
        }
        return item;
    }

    public static void main(String[] args) {
        ResizingArrayStack<Integer> stack = new ResizingArrayStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.size());
        int temp = stack.pop();
        System.out.println(temp);
        System.out.println(stack.size());
    }
}



