package stack;

public class LinkedListStack<T> {

    private Node first;
    private int size;

    private class Node {
        T item;
        Node next;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(T item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    public T pop() {
        T item = first.item;
        first = first.next;
        size--;
        return item;
    }

    public static void main(String[] args) {
        LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.size());
        int temp = stack.pop();
        System.out.println(temp);
        System.out.println(stack.size());
        stack.push(4);
        stack.push(5);

    }
}
