/**
 * This class represents the Process object.
 */
public class Process
{
   int pid;
   int cycles;

   // constructors
   public Process( String p, String c )
   {
      pid = Integer.parseInt( p );
      cycles = Integer.parseInt( c );
   }

   public Process( Process p )
   {
      pid = p.getPid();
      cycles = p.getCycles();
   }

   public int getPid()
   {
      return pid;
   }

   public int getCycles()
   {
      return cycles;
   }

   // decrements cycles by 1 (used when the cycles need to be updated)
   public void decrement()
   {
      cycles--;
   }
}

