//Copyright (c) <2015> <Phillip Blakey>
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MonteCarloSim {
	static Timer timer = new Timer();
	static int seconds = 0;
	public static void main(String [] args){
		Statistics stats= new Statistics();
		OneVariable instance = new OneVariable(0.0,1.0,100000000);
		OneVarFunction OneVarF = new OneVarFunction();
		final long startTime = System.currentTimeMillis();
		instance.run(OneVarF);
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
	}
}
