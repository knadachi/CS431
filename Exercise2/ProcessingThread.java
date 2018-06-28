import java.util.concurrent.ConcurrentHashMap;

// ProcessingThread: runs a loop that processes the strings of text from the lines 
//                   queue populated by the File Read Thread. It also counts the 
//                   occurrences of lowercase letters, uppercase letters, and the 
//                   digits 0-9. The counts are stored in a Hash Map.
public class ProcessingThread extends Thread
{
   public static ConcurrentHashMap<Character, Integer> counts = new ConcurrentHashMap<Character, Integer>();
   private Thread t;
   private String name;

   public ProcessingThread( String n )
   {
      name = n;

      for( int i = 97; i <= 122; i++ )
         counts.put( (char)i, 0 );
      for( int i = 65; i <= 90; i++ )
         counts.put( (char)i, 0 );
      for( int i = 48; i <= 57; i++ )
         counts.put( (char)i, 0 );;
   }

   public void run()
   {
      while( true )
      {
         try
         {
            String curLine = ThreadTest.lines.take();
            if( curLine.equals( "poison" ) )
               return;

            char c;
            for( int i = 0; i < curLine.length(); i++ )
            {
               c = curLine.charAt( i );
               Integer value = counts.get( c );
               if( value != null )
                  counts.put( c, ++value );
            }
         }
         catch( InterruptedException e )
         {
            return;
         }
         catch( Exception e )
         {
            System.out.print( "ProcessingThread Error.\n> " );
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
}

