import java.util.concurrent.LinkedBlockingQueue;

// contains the main method and runs the threads
public class ThreadTest
{
   public static LinkedBlockingQueue<String> files = new LinkedBlockingQueue<String>();
   public static LinkedBlockingQueue<String> lines = new LinkedBlockingQueue<String>();

   public static void main( String[] args )
   {
      IOThread t1 = new IOThread( "I/O Thread" );
      FileReadThread t2 = new FileReadThread( "File Read Thread" );
      ProcessingThread t3 = new ProcessingThread( "Processing Thread" );

      t1.start();
      t2.start();
      t3.start();

      try
      {
         t1.join();
         t2.interrupt();
         t3.interrupt();
      }
      catch( InterruptedException e )
      {
         System.out.println( "Interrupted Exception occurred.\n> " );
      }
   }
}

