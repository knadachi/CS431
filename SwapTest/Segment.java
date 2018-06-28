
/* This class acts as a node for a list of segments */
public final class Segment
{
   // id of the process in a given segment
   private int pid;

   // address where the segment begins
   private int start;

   // length of the segment
   private int length;

   // points to the next segment in the list
   private Segment next;

   public Segment( int pid, int start, int length, Segment next )
   {
      this.pid = pid;
      this.start = start;
      this.length = length;
      this.next = next;
   }

   public int getPid()
   {
      return pid;
   }

   public int getStart()
   {
      return start;
   }

   public int getLength()
   {
      return length;
   }

   public Segment getNext()
   {
      return next;
   }

   public void setPid( int pid )
   {
      this.pid = pid;
   }

   public void setStart( int start )
   {
      this.start = start;
   }

   public void setLength( int length )
   {
      this.length = length;
   }

   public void setNext( Segment next )
   {
      this.next = next;
   }

   @Override
   public String toString()
   {
      return String.format( "(%d %d %d)", pid, start, length );
   }
}

