import java.util.Random;
import java.util.Formatter;

// Central Processing Unit (CPU) Class
public class CPU
{
   private int pc, sp, r0, r1, r2, r3;

   public int[] getRegisters()
   {
      int[] registers = new int[]{ pc, sp, r0, r1, r2, r3 };
      return registers;
   }

   // Generates new random numbers for each of the 6 registers.
   public void initRegisters()
   {
      Random rand = new Random();
      pc = rand.nextInt();
      sp = rand.nextInt();
      r0 = rand.nextInt();
      r1 = rand.nextInt();
      r2 = rand.nextInt();
      r3 = rand.nextInt();
   }

   // Sets registers with given values.
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
      String format = "%7s %10s %7s %10s\n";
      String pc = "0x" + String.format( "%08x", this.pc );
      String sp = "0x" + String.format( "%08x", this.sp );
      String r0 = "0x" + String.format( "%08x", this.r0 );
      String r1 = "0x" + String.format( "%08x", this.r1 );
      String r2 = "0x" + String.format( "%08x", this.r2 );
      String r3 = "0x" + String.format( "%08x", this.r3 );
      String str = "CPU:\n" + String.format( format, "PC =", pc, "SP =", sp ) + String.format( format, "R0 =", r0, "R1 =", r1 ) + String.format( format, "R2 =", r2, "R3 =", r3 );
      return str;
   }
}
