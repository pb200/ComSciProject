//Copyright (c) <2015> <Phillip Blakey>
import java.util.Timer;

public class MonteCarloSim implements Runnable{
	static Timer timer = new Timer();
	static int seconds = 0;
	private long seed = 0;
	private int trials = 0;
	private int numVars = 0;
	public MonteCarloSim(long seedIn, int trialsIn, int numVarsIn){
		this.seed = seedIn;
		this.trials = trialsIn;
		this.numVars = numVarsIn;
	}
	 public void run()
	    {
	      {
	        try
	        {
	          Thread.sleep(1000);
	        }
	        catch (InterruptedException ie)
	        {
	          System.err.println("Interrupted");
	          return;
	        }
	        System.out.println("Thread ID "+Thread.currentThread().getId()+" running");
	        //Statistics stats= new Statistics();
			TrialRunner instance = new TrialRunner(this.trials,this.numVars,this.seed);
			NVarFunction nVarF = new NVarFunction();
			final long startTime = System.currentTimeMillis();
			instance.runNVar(nVarF);
			final long endTime = System.currentTimeMillis();
			System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");

	      }
	    }
}
