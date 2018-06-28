import java.util.Scanner;

/**
 * This maintains the command line interface that allows the user to input commands;
 * enter 'q' to quit
 */
public class UI
{
   private Scanner input;
   private FS fileSystem;

   public UI( FS fs )
   {
      input = new Scanner( System.in );
      fileSystem = fs;
   }

   public void start()
   {
      String command = "";
      while( !command.equals( "q" ) )
      {
         System.out.print( "> " );
         command = input.nextLine();
         String[] args = command.split( "\\s" );
         switch( args[0] )
         {
            case "put":
               if( !fileSystem.put( args[1], Integer.parseInt( args[2] ) ) )
                  System.out.println( "File could not be put in the system." );
               break;
            case "del":
               if( !fileSystem.delete( args[1] ) )
                  System.out.println( "File does not exist in the system." );
               break;
            case "bitmap":
               fileSystem.printBitmap();
               break;
            case "inodes":
               fileSystem.printInodes();
               break;
            case "q":
               input.close();
               return;
            default:
               input.reset();
         }
      }
   }
}

