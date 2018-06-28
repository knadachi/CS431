import java.util.*;

// Process Table Class
public class ProcTable
{
   private CPU cpu;
   private HashMap<Integer, PCB> processes; //(key, value) = (PID, PCB)
   private int runningPID; //holds the PID of the currently running process

   // process table starts with:
   // program id = 1; program name = init; user = root; status = 0 (running); random values in registers 
   public ProcTable()
   {
      cpu = new CPU();
      cpu.initRegisters();
      processes = new HashMap<Integer, PCB>();
      processes.put( 1, new PCB( "init", "root", 0, cpu.getRegisters() ));
      runningPID = 1;
   }

   public CPU getCPU()
   {
      return cpu;
   }

   // fork: command that makes a copy of the currently process with status 1 (ready) and unique PID.
   public void fork()
   {
      PCB cur = processes.get( runningPID );
      PCB pcb = new PCB( cur.getProgram(), cur.getUser(), 1, cpu.getRegisters() );
      processes.put( pcb.getPID(), pcb );
   } 

   // kill pid: command that kills the process with the specified PID only if either the current 
   //           running process has user root or it is the same user as the process being killed.
   public void kill( int pid )
   {
      PCB cur = processes.get( runningPID );
      PCB pcb = processes.get( pid );
      if( pcb == null || pcb.getStatus() == 0 )
         return;
      if( cur.getUser().equals( "root" ) || cur.getUser().equals( pcb.getUser() ) )
         processes.remove( pid );
   }

   // execve program user: command that switches the program and user name for the currently running 
   //                      process to the values specified and sets all the register contents to 
   //                      newly randomized values.
   public void execve( String program, String user )
   {
      PCB cur = processes.get( runningPID );
      cpu.initRegisters();
      cur.setProgram( program );
      cur.setUser( user );
      cur.setRegisters( cpu.getRegisters() );
   }

   // block: command that puts the status of the currently running process into state 2 (blocked). It is 
   //        unloaded from the CPU and a randomly chosen ready process is loaded into the CPU.
   public void block()
   {
      PCB[] ready = getReadyArray();
      if( ready.length == 0 )
         return;
      
      PCB cur = processes.get( runningPID );
      cur.setStatus( 2 );
      
      Random rand = new Random();
      int index = rand.nextInt( ready.length );
      PCB pcb = ready[index];
      pcb.setStatus( 0 );
      cpu.setRegisters( pcb.getRegisters() );
      runningPID = pcb.getPID();
   }

   // yield: command that puts the currently running process into state 1 (ready). It is unloaded from 
   //        the CPU and a randomly chosen ready process is loaded into the CPU. If there are no ready 
   //        processes, nothing occurs.
   public void yield()
   {  
      PCB[] ready = getReadyArray();
      if( ready.length == 0 )
         return;

      PCB cur = processes.get(runningPID );
      cur.setStatus( 1 );

      Random rand = new Random();
      int index = rand.nextInt( ready.length );
      PCB pcb = ready[index];
      pcb.setStatus( 0 );
      cpu.setRegisters( pcb.getRegisters() );
      runningPID = pcb.getPID();
   }

   // exit: command that causes the currently running process to exit (removed from the process table). A 
   //       randomly chosen ready process is loaded into the CPU. If there are no ready processes, nothing 
   //       occurs.
   public void exit()
   { 
      PCB[] ready = getReadyArray();
      if( ready.length == 0 )
         return;

      PCB cur = processes.remove( runningPID );
      Random rand = new Random();
      int index = rand.nextInt( ready.length );
      PCB pcb = ready[index];
      pcb.setStatus( 0 );
      cpu.setRegisters( pcb.getRegisters() );
      runningPID = pcb.getPID();
   }

   // unblock pid: command that moves the blocked process with the specified pid to the ready state.
   public void unblock( int pid )
   {
      PCB cur = processes.get( pid );
      if(( cur == null && cur.getStatus() != 2 ) || processes.size() == 1 )
         return;

      cur.setStatus( 1 );
   }

   public String toString()
   {
      String format = "%4s %12s %8s %6s %10s %10s %10s %10s %10s %10s\n";
      String str = "Process Table:\n" + String.format( format, "PID", "Program", "User", "Status", "PC", "SP", "R0", "R1", "R2", "R3" );
      
      Set set = processes.entrySet();
      Iterator i = set.iterator();
      while( i.hasNext() )
      {
         Map.Entry me = (Map.Entry)i.next();
         str += me.getValue().toString();
      }

      return str;
   }

   // method that returns an array of processes that are in state 1 (ready)
   private PCB[] getReadyArray()
   {
      Set<PCB> readySet = new HashSet<PCB>();
      Set set = processes.entrySet();
      Iterator i = set.iterator();
      while( i.hasNext() )
      {
         Map.Entry me = (Map.Entry)i.next();
         PCB temp = (PCB)me.getValue();
         if( temp.getStatus() == 1 )
            readySet.add( temp );
      }

      return readySet.toArray( new PCB[0] );
   }
}

