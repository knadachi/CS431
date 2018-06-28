import java.util.Scanner;

// User I/O Thread: prompts the user to enter commands such as read filename, 
//                  counts, and exit
public class IOThread extends Thread
{
   private Thread t;
   private String name;
   private final String POISON = "poison"; //put in the queues when ending the program

   public IOThread( String n )
   {
      name = n;
   }

   public void run()
   {
      Scanner sc = new Scanner( System.in );
      while( true )
      {
         try
         {
            System.out.print( "> " );
            String input = sc.nextLine();
            String[] args = input.split( "\\s" );
            switch( args[0] )
            {
               case "read":
                  ThreadTest.files.put( args[1] );
                  break;
               case "counts":
                  printCounts();
                  break;
               case "exit":
                  sc.close();
                  ThreadTest.files.put( POISON );
                  ThreadTest.lines.put( POISON );
                  Thread.currentThread().interrupt();
                  return;
               default:
                  sc.reset();
            }
         }
         catch( InterruptedException e )
         {
            return;
         }
      }
   }

   public void start()
   {
      if( t == null )
      {
         t = new Thread( this, name );
         t.start();
      }
   }

   // prints the count of occurrences determined by the Processing Thread
   private void printCounts()
   {
      for( int i = 97; i <= 122; i++ )
         System.out.println( (char)i + ": " + ProcessingThread.counts.get( (char)i ));
      for( int i = 65; i <= 90; i++ )
         System.out.println( (char)i + ": " + ProcessingThread.counts.get( (char)i ));
      for( int i = 48; i <= 57; i++ )
         System.out.println( (char)i + ": " + ProcessingThread.counts.get( (char)i ));
   }
}

