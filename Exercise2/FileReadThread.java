import java.io.BufferedReader;
import java.io.FileReader;

// File Read Thread: runs a loop that processes file names provided by the User I/O 
//                   Thread and stores lines of text read in the lines queue.
public class FileReadThread extends Thread
{
   private Thread t;
   private String name;

   public FileReadThread( String n )
   {
      name = n;
   }

   public void run()
   {
      while( true )
      {
         try
         {
            String fileName = ThreadTest.files.take();
            if( fileName.equals( "poison" ) )
               return; 
 
            String curLine;
            BufferedReader br = new BufferedReader( new FileReader( fileName ) );
            while( (curLine = br.readLine() ) != null ){
               ThreadTest.lines.put( curLine );
            }
            br.close();
         }
         catch( InterruptedException e )
         {
            return;
         }
         catch( Exception e )
         {
            System.out.print( "FileReadThread Error.\n> " );
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

