import java.util.Formatter;

// Process Control Block (PCB) Class
public class PCB
{
   private static int nextPid = 1; // contains value for next pid
   private int pid;
   private String program;
   private String user;
   private int status;
   private int pc, sp, r0, r1, r2, r3;

   // constructor that creates a new PCB object
   public PCB( String p, String u, int s, int[] registers )
   {
      pid = nextPid++;
      program = p;
      user = u;
      status = s;
      pc = registers[0];
      sp = registers[1];
      r0 = registers[2];
      r1 = registers[3];
      r2 = registers[4];
      r3 = registers[5];
   }

   public int getPID()
   {
      return pid;
   }

   public String getProgram()
   {
      return program;
   }

   public String getUser()
   {
      return user;
   }

   public int getStatus()
   {
      return status;
   }

   public int[] getRegisters()
   {
      int[] registers = new int[]{ pc, sp, r0, r1, r2, r3 };
      return registers;
   }

   public void setProgram( String p )
   {
      program = p;
   }

   public void setUser( String u )
   {
      user = u;
   }

   public void setStatus( int s )
   {
      status = s;
   }

   public void setRegisters( int[] registers )
   {
      pc = registers[0];
      sp = registers[1];
      r0 = registers[2];
      r1 = registers[3];
      r2 = registers[4];
      r3 = registers[5];
   }

   public String toString()
   {
      String tempProgram = program;
      String tempUser = user;
      if( program.length() > 12 )
         tempProgram = tempProgram.substring(0, 12);
      if( user.length() > 8 )
         tempUser = tempUser.substring(0, 8);

      String format = "%4d %12s %8s %6d %10s %10s %10s %10s %10s %10s\n";
      String pc = "0x" + String.format( "%08x", this.pc );
      String sp = "0x" + String.format( "%08x", this.sp );
      String r0 = "0x" + String.format( "%08x", this.r0 );
      String r1 = "0x" + String.format( "%08x", this.r1 );
      String r2 = "0x" + String.format( "%08x", this.r2 );
      String r3 = "0x" + String.format( "%08x", this.r3 );
      String str = String.format( format, pid, tempProgram, tempUser, status, pc, sp, r0, r1, r2, r3 );
      return str;   
   }
}

