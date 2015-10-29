//Copyright (c) <2015> <Phillip Blakey>
import java.util.Timer;

public class MonteCarloSim implements Runnable{
	static Timer timer = new Timer();
	static int seconds = 0;
	private long seed = 0;
	private int trials = 0;
	private int numVars = 0;
	private static double sumPi = 0;
	public MonteCarloSim(long seedIn, int trialsIn, int numVarsIn){
		this.seed = seedIn;
		this.trials = trialsIn;
		this.numVars = numVarsIn;
	}
	public static double getSumPi(){
		return sumPi;
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
			for(int j = 0; j < instance.getNumVars();j++){
				instance.getTrialStat(j).calculuateStdDev();
				instance.getTrialStat(j).calculateVarience();
				instance.getTrialStat(j).calculateMean();	
				
			}
			sumPi = sumPi+ 4*(instance.getSuccesses()/instance.getTrials());
			final long endTime = System.currentTimeMillis();
			
			String outputStats = "";
			for (int i = 0; i < numVars; i++){
				outputStats = outputStats + "Standard Deviation Var" + i+ ": "+ instance.getTrialStat(numVars -1).getStandardDev() + "\n" +
						"Varience Var" + i+ ": " + instance.getTrialStat(numVars -1).getVarience() + "\n" + 
						"Mean Var" + i+ ": "+ instance.getTrialStat(numVars -1).getMean()+"\n";	
			}
			System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds" + "\n"+
			"Pi: " + sumPi + "\n" + outputStats);		
	      }
	    }
}
