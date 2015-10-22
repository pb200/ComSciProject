//Copyright (c) <2015> <Phillip Blakey>
import java.util.Timer;

public class MonteCarloSim {
	static Timer timer = new Timer();
	static int seconds = 0;
	public static void main(String [] args){
		Statistics stats= new Statistics();
		TrialRunner instance = new TrialRunner(10000000,2);
		NVarFunction nVarF = new NVarFunction();
		final long startTime = System.currentTimeMillis();
		instance.runNVar(nVarF);
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
	}
}
