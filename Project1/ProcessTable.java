// contains main method

import java.util.Random;
import java.util.Formatter;
public class ProcessTable
{
   public static void main( String[] args )
   {
      ProcTable procTable = new ProcTable();
      UI ui = new UI( procTable );
      ui.start();
   }
}

