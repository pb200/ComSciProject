//Copyright (c) <2015> <Phillip Blakey>
import java.security.NoSuchAlgorithmException;


public class MCRunner {
	private int numThreads = 0;
	
	public MCRunner(int numThreadsIn){
		this.numThreads = numThreadsIn;
	}
	public int getNumThreads(){
		return this.numThreads;
	}
	public static void main(String [] args) throws NoSuchAlgorithmException{
		
		MCRunner mCRunner = new MCRunner(4);
		int numThreads = mCRunner.getNumThreads();
		GenerateUUID seedArray = new GenerateUUID(numThreads);
		Thread [] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++){
			threads[i]=new Thread(new MonteCarloSim(seedArray.getSeed(i),100000,2));
			threads[i].start();
		}
	}
}
