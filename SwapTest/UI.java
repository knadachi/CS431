import java.util.Scanner;

/* This class maintains the command line interface that allows the user to input commands */
public class UI
{
   private Scanner input;
   private Swaps memory;

   public UI( Swaps memory )
   {
      input = new Scanner( System.in );
      this.memory = memory;
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
            case "add":
               if( !memory.addJob( Integer.parseInt( args[1] ), Integer.parseInt( args[2] ) ))
                  System.out.println( "Job could not be added." );
               break;
            case "jobs":
               System.out.print( memory.printJobList() );
               break;
            case "list":
               System.out.println( memory.toString() );
               break;
            case "ff":
               if( !memory.firstFit( Integer.parseInt( args[1] ) ))
                  System.out.println( "Job could not be allocated." );
               break;
            case "nf":
               if( !memory.nextFit( Integer.parseInt( args[1] ) ))
                  System.out.println( "Job could not be allocated." );
               break;
            case "bf":
               if( !memory.bestFit( Integer.parseInt( args[1] ) ))
                  System.out.println( "Job could not be allocated." );
               break;
            case "wf":
               if( !memory.worstFit( Integer.parseInt( args[1] ) ))
                  System.out.println( "Job could not be allocated." );
               break;
            case "de":
               if( !memory.deallocate( Integer.parseInt( args[1] ) ))
                  System.out.println( "Job is not currently allocated." );
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

