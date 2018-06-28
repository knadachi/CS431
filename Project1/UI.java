import java.util.Scanner;

// Command Line Interface
public class UI
{
   private Scanner input;
   private ProcTable procTable;

   public UI( ProcTable pt )
   {
      input = new Scanner( System.in );
      procTable = pt;
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
            case "fork":
               procTable.fork();
               break;
            case "kill":
               procTable.kill( Integer.parseInt( args[1] ));
               break;
            case "execve":
               procTable.execve( args[1], args[2] );
               break;
            case "block":
               procTable.block();
               break;
            case "yield":
               procTable.yield();
               break;
            case "exit":
               procTable.exit();
               break;
            case "print":
               System.out.println( procTable.getCPU() );
               System.out.println( procTable );
               break;
            case "unblock":
               procTable.unblock( Integer.parseInt( args[1] ));
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
