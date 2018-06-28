
/* class that contains the main method and runs the UI */
public class SwapTest
{
   public static void main( String[] args )
   {
      Swaps memory = new Swaps();
      UI ui = new UI( memory );
      ui.start();
   }
}

