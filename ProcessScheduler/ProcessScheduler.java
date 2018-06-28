import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * This program simulates several process scheduling algorithms on lists of processes provided in 
 * input files.
 */
public class ProcessScheduler
{
   public static void main( String[] args ) throws IOException
   {
      LinkedList<Process> processes = new LinkedList<Process>();
      BufferedReader br = new BufferedReader( new FileReader( args[0] ) );
      String curLine;
      while( (curLine = br.readLine()) != null )
      {
         String[] arr = curLine.split( "," );
         Process p = new Process( arr[0], arr[1] );
         processes.add( p );
      }

      // first-come, first-served scheduler
      LinkedList<Process> temp = copy( processes );
      firstComeFirstServed( temp );

      // shortest first scheduler     
      temp = copy( processes );
      shortestFirst( temp );

      // round robin scheduler with quantum 50
      temp = copy( processes );
      roundRobin( temp, 50 );

      // round robin scheduler with quantum 100
      temp = copy( processes );
      roundRobin( temp, 100 );

      // random scheduler with quantum 50
      temp = copy( processes );
      random( temp, 50 );
   }

   /**
    * First-Come, First-Served Scheduler:
    * Runs each process to completion in the order they are submitted.
    */
   public static void firstComeFirstServed( LinkedList<Process> processes )
   {
      System.out.println( "Running first-come, first-served scheduler." );
      int count = 0;
      int total = 0;
      int size = processes.size();
      while( processes.size() != 0 )
      {
         Process p = processes.remove();
         for( int i = 1; i <= p.getCycles(); i++ )
         {
            count++;
         }
         total += count;
         System.out.println( "Process " + p.getPid() + " finishes on cycle " + count + ".");
      }
      double turnaround =  (double)total / size;
      System.out.println( "Average turnaround time: " + turnaround + ".\n" );
   }

   /**
    * Shortest First Scheduler:
    * Runs each process to completion in the order of shortest to longest (in terms of cycles).
    */
   public static void shortestFirst( LinkedList<Process> processes )
   {
      // sorts the linked list of processes in order from shortest cycles to longest cycles
      System.out.println( "Running shortest first scheduler." );
      Collections.sort( processes, new Comparator<Process>()
      {
         public int compare( Process p1, Process p2 )
         {
            if( p1.getCycles() >= p2.getCycles() )
               return 1;
            else
               return -1;
         }
      } );
      firstComeFirstServed( processes );
   }

   /**
    * Round Robin Scheduler:
    * Goes through the processes in order of submission and executes each for a small amount of time 
    * (quantum) before switching to the next process.
    */
   public static void roundRobin( LinkedList<Process> processes, int quantum )
   {
      System.out.println( "Running round robin scheduler with quantum " + quantum + "." );
      int count = 0;
      int total = 0;
      int size = processes.size();
      while( processes.size() != 0 )
      {
         Process p = processes.remove();
         for( int i = 1; i <= quantum; i++ )
         {
            if( p.getCycles() != 0 )
            {
               p.decrement();
               count++;
            }
         }
         if( p.getCycles() != 0 )
            processes.add( p );
         else
         {  
            total += count;
            System.out.println( "Process " + p.getPid() + " finishes on cycle " + count + "." );
         }
      }
      double turnaround = (double)total / size;
      System.out.println( "Average turnaround time: " + turnaround + ".\n" );
   }

   /**
    * Random Scheduler:
    * Randomly chooses a process to run for a quantum weighted by how many cycles are remaining for 
    * each process.
    */
   public static void random( LinkedList<Process> processes, int quantum )
   {
      System.out.println( "Running random scheduler with quantum " + quantum + "." );
      int count = 0;
      int total = 0;
      int size = processes.size();
      int denominator = 0;
      for( int i = 0; i < processes.size(); i++ )
      {
         Process p = processes.get( i );
         denominator += p.getCycles();
      }
      while( processes.size() != 0 )
      {
         Random rand = new Random();
         int random = rand.nextInt( denominator );
         int cumulativeSum = 0;
         for( int i = 0; i < processes.size(); i++ )
         {
            // chooses a random process by probability (based on cycles remaining)
            if( cumulativeSum < random && random < (cumulativeSum + processes.get(i).getCycles()) )
            {
               Process p = processes.get(i);
               for( int j = 1; j <= quantum; j++ )
               {
                  if( p.getCycles() != 0 )
                  {
                     p.decrement();
                     count++;
                  }
               }
               if( p.getCycles() == 0 )
               {
                  processes.remove( p );
                  total += count;
                  System.out.println( "Process " + p.getPid() + " finishes on cycle " + count + "." );
               }
            }
            else
               cumulativeSum += processes.get(i).getCycles();
         }
      }
      double turnaround = (double)total / size;
      System.out.println( "Average turnaround time: " + turnaround + ".\n" );
   }

   // returns a copy of a given linked list
   public static LinkedList<Process> copy( LinkedList<Process> l )
   {
      LinkedList<Process> list = new LinkedList<Process>();
      for( int i = 0; i < l.size(); i++ )
      {
         Process p = l.get( i );
         Process temp = new Process( p );
         list.add( temp );
      }
      return list;
   }
}

