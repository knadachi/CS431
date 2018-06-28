/**
 * This class contains the main method and runs the UI.
 */
public class FileSystem
{
   public static void main( String[] args )
   {
      FS fileSystem = new FS();
      UI ui = new UI( fileSystem );
      ui.start();
   }
}

