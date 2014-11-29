import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private int size;
   private Item[] a;
   public RandomizedQueue()
   {
       size = 0;
       a = (Item[]) new Object[1];
   }
   public boolean isEmpty()
   {
       return size == 0;
   }
   public int size()
   {
       return size;
   }
   private void resize(int capacity)
   {
       Item[] copy = (Item[]) new Object[capacity];
       for (int i = 0; i < size; i++)
           copy[i] = a[i];
       a = copy;
   }
   public void enqueue(Item item)
   {
       if (item == null)
           throw new NullPointerException();
       if (size == a.length)
           resize(2 * a.length);
       a[size++] = item;
   }
   
   public Item dequeue()
   {
       if (size == 0)
           throw new NoSuchElementException();
       int r = StdRandom.uniform(size);
       Item result = a[r];
       a[r] = a[size-1];
       a[--size] = null;
       if (size > 0 && size == a.length/4)
           resize(a.length/2);
       return result;
   }
   public Item sample()
   {
       if (size == 0)
           throw new NoSuchElementException();
       return a[StdRandom.uniform(size)];
   }
   public Iterator<Item> iterator()
   {
       return new RandomizedQueueIterator(a, size);
   }
   private class RandomizedQueueIterator<Item> implements Iterator<Item>
   {
       private int i;
       private Item[] copy;
       public RandomizedQueueIterator(Item[] a, int size)
       {
           i = 0;
           copy = (Item[]) new Object[size];
           for (int j = 0; j < size; j++)
               copy[j] = a[j];
           StdRandom.shuffle(copy);
       }
       public boolean hasNext()
       {
           return i < copy.length;
       }
       public void remove()
       {
           throw new UnsupportedOperationException();
       }
       public Item next()
       {
           if (!hasNext())
               throw new NoSuchElementException();
           return copy[i++];
       }
   }
   public static void main(String[] args)
   {
       int N = Integer.parseInt(args[0]);
       RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
       for (int i = 0; i < N; i++)
           randomizedQueue.enqueue(i+1);
       for (int i: randomizedQueue)
       {
           for (int j: randomizedQueue)
               StdOut.print(j + " ");
           StdOut.println();
           StdOut.println(i + " ");
       }
   }
}