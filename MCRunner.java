//Copyright (c) <2015> <Phillip Blakey>
import java.security.NoSuchAlgorithmException;


public class MCRunner {
	private int numThreads = 0;
	private double pi;
	public MCRunner(int numThreadsIn){
		this.numThreads = numThreadsIn;
	}
	public int getNumThreads(){
		return this.numThreads;
	}
	public static void main(String [] args) throws NoSuchAlgorithmException, InterruptedException{
		
		MCRunner mCRunner = new MCRunner(4);
		int numThreads = mCRunner.getNumThreads();
		GenerateUUID seedArray = new GenerateUUID(numThreads);
		Thread [] threads = new Thread[numThreads];
		MonteCarloSim[] mcs = new MonteCarloSim[numThreads];
		for (int i = 0; i < numThreads; i++){
			mcs[i] = new MonteCarloSim(seedArray.getSeed(i),1000000,2);
			threads[i] = new Thread(mcs[i]);
			threads[i].start();
		}
		
		for(int i = 0; i < numThreads; i++) {
			threads[i].join();
			//System.out.println(mcs[i].getSumPi());
		}
		double pi = mcs[numThreads-1].getSumPi()/numThreads;
		System.out.println("Pi = " + pi);
	}
}
