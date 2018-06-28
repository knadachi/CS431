/**
 * This class represents an i-node which contains the file's name and the starting 
 * block.
 */
public class Inode
{
   private String filename;
   private int start;

   public Inode( String fn, int s )
   {
      filename = fn;
      start = s;
   }

   /**
    * Accessor method for file's name.
    */
   public String getFilename()
   {
      return filename;
   }

   /**
    * Accessor method for starting block.
    */
   public int getStart()
   {
      return start;
   }

   /**
    * This method is used to check if one i-node is the same as another by comparing file 
    * names; returns true if they are equal and false otherwise.
    */
   public boolean equals( Object o )
   {
      if( o == this )
         return true;
      if( !( o instanceof Inode ) )
         return false;
      Inode n = (Inode)o;
      return this.filename.equals( n.filename );
   }
}

