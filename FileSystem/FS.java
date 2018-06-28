import java.util.ArrayList;

/**
 * This class handles the data structures for the file system:
 * (1) array of ints that corresponds to the File Allocation Table (FAT) and records pointers
 * (2) list of i-nodes
 * (3) bitmap that tracks empty and allocated blocks in the file system
 */
public class FS
{
   private final int FAT_SIZE = 64;
   private int[] FAT;
   private ArrayList<Inode> inodes;
   private long bitmap;

   public FS()
   {
      FAT = new int[FAT_SIZE];
      inodes = new ArrayList<Inode>();
      bitmap = 0;
   }

   /**
    * This method attempts to put the file with the given name and size (in blocks) into the 
    * file system and updates the FAT if necessary; returns true if successfully added and 
    * false otherwise.
    */
   public boolean put( String filename, int filesize )
   {
      if( !inodes.isEmpty() && contains( filename ) )
         return false;

      //this list holds the free blocks in the FAT
      ArrayList<Integer> freeBlocks = new ArrayList<Integer>();
      for( int i = 0; i < FAT.length && freeBlocks.size() < filesize; i++ )
      {
         //checks if current block is free (0)
         if( ( ( bitmap >> i ) & 1L ) == 0 )
            freeBlocks.add( i );
      }

      if( freeBlocks.size() >= filesize )
      {
         inodes.add( new Inode( filename, freeBlocks.get(0) ) );
         for( int i = 0; i < filesize; i++ )
         {
            //sets blocks to allocated (1)
            bitmap |= 1L << freeBlocks.get(i);
            if( i == filesize - 1 )
               FAT[freeBlocks.get(i)] = -1;
            else
               FAT[freeBlocks.get(i)] = freeBlocks.get( i + 1 );
         }
         return true;
      }
      return false;
   }

   /**
    * This method attempts to delete the file with the given name (the inode is removed and 
    * the bitmap is cleared for the appropriate blocks); returns true if successfully deleted 
    * and false otherwise.
    */
   public boolean delete( String filename )
   {
      if( !inodes.isEmpty() && contains( filename ) )
      {
         int index = inodes.indexOf( new Inode( filename, 0 ) );
         Inode deleted = inodes.remove( index );
         int next = deleted.getStart();
         while( next != -1 )
         {
            //sets blocks to free (0)
            bitmap &= ~( 1L << next );
            next = FAT[next];
         }
         return true;
      }
      return false;
   }

   /**
    * This method checks if the file is already in the system; returns true if it exists and 
    * false otherwise.
    */
   public boolean contains( String filename )
   {
      for( int i = 0; i < inodes.size(); i++ )
      {
         if( filename.equals( inodes.get(i).getFilename() ) )
            return true;
      }
      return false;
   }

   /**
    * This method prints the bitmap as an 8x8 square with each line labeled 
    * by the starting block number.
    */
   public void printBitmap()
   {
      String binary = String.format( "%64s", Long.toBinaryString( bitmap )).replace( ' ', '0' );

      for( int i = 0; i < FAT.length; i += 8 )
      {
         System.out.printf( "%2d ", i );
         for( int j = i; j < i + 8; j++ )
         {
            System.out.print( binary.charAt( FAT.length - 1 - j ) );
         }
         System.out.println();
      }
   }

   /**
    * This method prints all of the i-nodes along with the linked list of pointers
    * from the FAT for each one. 
    */
   public void printInodes()
   {
      if( inodes.isEmpty() )
      {
         System.out.println( "There are no files currently on the system." );
         return;
      }

      for( int i = 0; i < inodes.size(); i++ )
      {
         Inode cur = inodes.get(i);
         System.out.print( cur.getFilename() + ": " );
         int next = cur.getStart();
         while( next != -1 )
         {
            System.out.print( next );
            next = FAT[next];
            if( next != -1 )
               System.out.print( " -> " );
         }
         System.out.println();
      }
   }
}

