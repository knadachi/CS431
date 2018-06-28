import java.util.concurrent.Semaphore;
import java.util.PriorityQueue;

/* simulation of a blocking queue using semaphores */
public final class BlockingQueue<T>
{
   private static int n;
   private static final Semaphore mutex = new Semaphore(1);
   private static Semaphore empty;
   private static Semaphore full = new Semaphore(0);
   private PriorityQueue<T> queue;

   /* runs the given main method (in instructions) to test the blocking queue */
   public static void main( String[] args ) throws Exception
   {
      BlockingQueue<Integer> queue = new BlockingQueue<>(100);
      Runnable r = () ->
      {
         for( int i = 0; i < 200; i++ )
         {
            try
            {
               int n = queue.dequeue();
               System.out.println( n + " removed" );
               Thread.sleep(500);
            }
            catch( Exception e ){}
         }
      };
      Thread t = new Thread(r);
      t.start();
      for( int i = 0; i < 200; i++ )
      {
         System.out.println( "Adding " + i );
         queue.enqueue(i);
      }
   }

   public BlockingQueue( int size )
   {
      n = size;
      empty = new Semaphore(n);
      queue = new PriorityQueue<T>();
   }

   /* adds to queue */
   public void enqueue( T t )
   {
      try
      {
         empty.acquire();
         mutex.acquire();
         queue.add( t );
         mutex.release();
         full.release();
      }
      catch( InterruptedException ie )
      {
         System.out.println( "Interrupted Exception occurred." );
      }
   }

   /* removes from queue */
   public T dequeue()
   {
      T t = null;
      try
      {
         full.acquire();
         mutex.acquire();
         t = queue.poll();
         mutex.release();
         empty.release();
      }
      catch( InterruptedException ie )
      {
         System.out.println( "Interrupted Exception occurred." );
      }
      return t;
   }
}

