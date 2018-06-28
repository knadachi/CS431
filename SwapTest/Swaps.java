import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class maintains the linked list of segments and manages the allocation/deallocation 
 * of memory 
 */
public class Swaps
{
   // first segment of the linked list
   private Segment first;

   // used to keep track of the last hole used for the Next Fit Algorithm
   private Segment lastCur;

   // used to keep track of the previous segment of the last hole used for the Next Fit Algorithm
   private Segment lastPrev;

   // keeps track of the list of jobs
   private ArrayList<Job> list = new ArrayList<Job>();

   // used to check if the job pid already exists (key is pid, value is a segment)
   private HashMap<Integer, Job> jobs = new HashMap<Integer, Job>();

   public Swaps()
   {
      first = new Segment( 0, 0, 100, null );
      lastCur = first;
      lastPrev = first;
   }

   /* returns true if job is added successfully and false otherwise */
   public boolean addJob( int n, int s )
   {
      if( n == 0 )
         return false;
      Job j = new Job( n, s );
      if( !jobs.containsKey( n ) )
      {
         jobs.put( n, j );
         list.add( j );
         return true;
      }
      return false;
   }

   /**
    * First Fit: the list is scanned for the first hole that is large enough to fit the job with 
    *            pid n; returns true if the allocation is successful and false otherwise
    */
   public boolean firstFit( int n )
   {
      Job j = jobs.get( n );
      if( j == null || exists( j ) )
         return false;
      Segment cur = first;
      Segment prev = first;
      while( cur != null )
      {
         if( cur.getPid() == 0 )
         {
            if( cur.getLength() >= j.getSize() )
            {
               Segment seg = new Segment( j.getPid(), cur.getStart(), j.getSize(), cur );
               if( cur.getStart() == 0 )
                  first = seg;
               else  
                  prev.setNext( seg );
               cur.setStart( cur.getStart() + j.getSize() );
               cur.setLength( cur.getLength() - j.getSize() );
               if( cur.getLength() == 0 )
                  seg.setNext( null );
               return true; 
            }
         }
         if( cur.getStart() != 0 )
            prev = prev.getNext();
         cur = cur.getNext();
      }
      return false;
   }

   /**
    * Next Fit: the list is scanned for the first hole that is large enough to fit the job with 
    *           pid n but starts from the last hole used rather than the first segment; returns 
    *           true if the allocation is successful and false otherwise
    */
   public boolean nextFit( int n )
   { 
      Job j = jobs.get( n );
      if( j == null || exists( j ) )
         return false;
      Segment cur = lastCur;
      Segment prev = lastPrev;
      boolean exit = false;
      while( !exit )
      {
         if( cur.getPid() == 0 )
         {
            if( cur.getLength() >= j.getSize() )
            {
               Segment seg = new Segment( j.getPid(), cur.getStart(), j.getSize(), cur );
               if( cur.getStart() == 0 )
                  first = seg;
               else
                  prev.setNext( seg );
               cur.setStart( cur.getStart() + j.getSize() );
               cur.setLength( cur.getLength() - j.getSize() );
               if( cur.getLength() == 0 )
                  seg.setNext( null );
               lastCur = cur;
               lastPrev = seg;
               return true; 
            }
         }
         if( cur.getStart() != 0 )
            prev = prev.getNext();
         if( cur.getNext() == null )
         {
            cur = first;
            prev = first;
         }
         else
            cur = cur.getNext();
         if( cur.getStart() == lastCur.getStart() )
            exit = true;
      }
      return false;
   }

   /**
    * Best Fit: the entire list is scanned and the smallest hole available that is large 
    *           enough to fit the job of pid n is used; returns true if the allocation is 
    *           successful and false otherwise
    */
   public boolean bestFit( int n )
   {
      Job j = jobs.get( n );
      if( j == null || exists( j ) )
         return false;
      Segment cur = first;
      Segment prev = first;
      Segment hole = null;
      Segment prevHole = null;
      while( cur != null )
      {
         if( cur.getPid() == 0 )
         {
            if( ( hole == null || hole.getLength() > cur.getLength() ) && cur.getLength() >= j.getSize() )
            {
               hole = cur;
               prevHole = prev;
            }
         } 
         if( cur.getStart() != 0 )
            prev = prev.getNext();
         cur = cur.getNext();
      }

      if( hole != null )
      {
         Segment seg = new Segment( j.getPid(), hole.getStart(), j.getSize(), hole ); 
         if( hole.getStart() == 0 )
            first = seg;
         else
            prevHole.setNext( seg );
         hole.setStart( hole.getStart() + j.getSize() );
         hole.setLength( hole.getLength() - j.getSize() );
         if( hole.getLength() == 0 )
            seg.setNext( null );
         return true; 
      }
      return false;
   }

   /**
    * Worst Fit: the entire list is scanned and the biggest hole available that also fits 
    *            the job with pid n is used; returns true if the allocation is successful and 
    *            false otherwise
    */
   public boolean worstFit( int n )
   { 
      Job j = jobs.get( n );
      if( j == null || exists( j ) )
         return false;
      Segment cur = first;
      Segment prev = first;
      Segment hole = null;
      Segment prevHole = null;
      while( cur != null )
      {
         if( cur.getPid() == 0 )
         {
            if( ( hole == null || hole.getLength() < cur.getLength() ) && cur.getLength() >= j.getSize() )
            {
               hole = cur;
               prevHole = prev;
            }
         } 
         if( cur.getStart() != 0 )
            prev = prev.getNext();
         cur = cur.getNext();
      }

      if( hole != null )
      {
         Segment seg = new Segment( j.getPid(), hole.getStart(), j.getSize(), hole ); 
         if( hole.getStart() == 0 )
            first = seg;
         else
            prevHole.setNext( seg );
         hole.setStart( hole.getStart() + j.getSize() );
         hole.setLength( hole.getLength() - j.getSize() );
         if( hole.getLength() == 0 )
            seg.setNext( null );
         return true; 
      }
      return false;
   }

   /**
    * Deallocation: considers 4 cases and 2 special cases
    *   (1) job exists between two jobs
    *   (2) job exists before but not after
    *   (3) job exists after but not before
    *   (4) job is between two holes
    *   (5) job is at the beginning of the list
    *   (6) job is at the end of the list
    *   after these cases are considered, it returns true if the deallocation is successful or 
    *   false otherwise
    */
   public boolean deallocate( int n )
   {
      Segment cur = first;
      Segment prev = first;
      while( cur != null && (cur.getPid() != n ) )
      {
         if( cur.getStart() != 0 )
            prev = prev.getNext();
         cur = cur.getNext();
      }

      if( cur == null )
         return false;

      Segment next = cur.getNext();
      if( cur.getStart() == 0 )
      {
         if( next.getPid() != 0 )
            first.setPid( 0 );
         else
         {
            next.setStart( 0 );
            next.setLength( cur.getLength() + next.getLength() );
            first = next;
            cur.setNext( null );
         }
         return true;
      }
      else if( next == null )
      {
         if( prev.getPid() == 0 )
         {
            prev.setLength( prev.getLength() + cur.getLength() );
            prev.setNext( null );
            cur.setNext( null );
         }
         else
            cur.setPid( 0 );
         return true;
      }
      else if( prev.getPid() != 0 && next.getPid() != 0 )
      {
         cur.setPid( 0 );
         return true;
      }
      else if( prev.getPid() != 0 && next.getPid() == 0 )
      {
         next.setStart( cur.getStart() );
         next.setLength( cur.getLength() + next.getLength() );
         prev.setNext( next );
         cur.setNext( null );
         return true;
      }
      else if( prev.getPid() == 0 && next.getPid() != 0 )
      {
         prev.setLength( prev.getLength() + cur.getLength() );
         prev.setNext( next );
         cur.setNext( null );
         return true;
      }
      else if( prev.getPid() == 0 && next.getPid() == 0 )
      {
         prev.setLength( prev.getLength() + cur.getLength() + next.getLength() );
         prev.setNext( next.getNext() );
         cur.setNext( null );
         return true;
      }
      return false;
   }

   /* returns true if the job exists on the system and false otherwise */
   public boolean exists( Job j )
   {
      Segment cur = first;
      while( cur != null )
      {
         if( cur.getPid() == j.getPid() )
            return true;
         cur = cur.getNext();
      }
      return false;
   }

   /* returns the linked list as a string */
   public String toString()
   {
      String segmentList = "";
      Segment cur = first;
      while( cur != null )
      {
         segmentList += cur.toString() + " ";
         cur = cur.getNext();
      }
      return segmentList;
   }

   /* returns the job list as a string */
   public String printJobList()
   {
      String jobList = "";
      for( int i = 0; i < list.size(); i++ )
      {
         jobList += ( list.get(i).toString() + "\n" );
      }
      return jobList;
   }
}

