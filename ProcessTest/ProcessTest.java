import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessTest
{
   public static void main( String args[] )
   {
      try
      {
         Process pr = Runtime.getRuntime().exec( "java" );
         String line = null;
         BufferedReader in = new BufferedReader( new InputStreamReader( pr.getErrorStream() ));
         while(( line = in.readLine() ) != null ){
            System.out.println( line );
         }
         //pr.waitFor();
      }
      catch( Exception e )
      {
         System.out.println( "Error." );
      }
   }
}
