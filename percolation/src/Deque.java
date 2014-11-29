import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int size;

    private class Node<Item> {
        Item item;
        Node<Item> previous, next;
    }

    public Deque() {
        first = new Node<Item>();
        last = new Node<Item>();
        first.next = last;
        last.previous = first;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) // insert the item at the front
    {
        if (item == null)
            throw new NullPointerException();
        Node<Item> tmp = new Node<Item>();
        tmp.item = item;
        tmp.next = first.next;
        first.next.previous = tmp;
        tmp.previous = first;
        first.next = tmp;

        size++;
    }

    public void addLast(Item item) // insert the item at the end
    {
        if (item == null)
            throw new NullPointerException();
        Node<Item> tmp = new Node<Item>();
        tmp.item = item;
        tmp.previous = last.previous;
        last.previous.next = tmp;
        tmp.next = last;
        last.previous = tmp;

        size++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node<Item> tmp = first.next;
        first.next = tmp.next;
        tmp.next.previous = first;
        size--;

        return tmp.item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node<Item> tmp = last.previous;
        tmp.previous.next = last;
        last.previous = tmp.previous;
        size--;

        return tmp.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first.next;
        }

        public boolean hasNext() {
            return current.next != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < N; i++)
            deque.addFirst(i + 1);
        // for (int i = 0; i < N; i++)
        // StdOut.print(deque.removeLast() + " ");
        for (int i : deque)
            StdOut.print(i + " ");
    }
}