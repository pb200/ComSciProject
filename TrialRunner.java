//Copyright (c) <2015> <Phillip Blakey>
import java.util.Random;
public class TrialRunner {
	private double maxValue = 0;
	private double minValue = 1;
	private int trials = 0;
	private double successes = 0;
	private double pSuccess = 0;
	private int numVars;
	private long seed;
	private double pi = 0;
	public TrialRunner(int trialsIn, int numVarsIn, long seedIn){
		this.trials = trialsIn;
		this.numVars = numVarsIn;
		this.seed = seedIn;
	}
	public double getPi(){
		return this.pi;
	}
	public int getNumVars(){
		return this.numVars;
	}
	public double getMaxValue(){
		return this.maxValue;
	}
	public long getSeed(){
		return this.seed;
	}
	public double getMinValue(){
		return this.minValue;
	}
	public int getTrials(){
		return this.trials;
	}
	public void incrementSuccesses(double input){
		this.successes = successes+input;
	}
	public double getSuccesses(){
		return this.successes;
	}
	public void setProbabilities(){
		this.pSuccess = ((double)(this.successes)/(double)(this.trials)) * 100.0;
	}
	public void setPi(double piIn){
		this.pi = piIn;
	}
	public double getProbabilities(){
		return this.pSuccess;
	}
	public double getSuccessP(){
		return this.pSuccess;
	}
	

	/*
	 * This method generates a random number using the randomnumgen class,
	 * takes in an instance of oneVarFunction-oneVarFunction
	 * takes in a random number and returns a value-puts the random number
	 * into an instance of the statistics class then puts the random number into the 
	 * instance of OneVariableFunction.
	 * 
	 */
	
	public void runOneVar(OneVarFunction oneVarF){
		Random rand = new Random();
		Statistics trial = new Statistics();
		RandomNumGen numGen = new RandomNumGen(this.getSeed());// needs seed
		for (int i = 1; i < this.getTrials()+1; i++ ){
			double randomVar =  numGen.getRandomNum();
			trial.run(randomVar);
			double oneVarValue = oneVarF.Check(randomVar);
			this.incrementSuccesses(oneVarValue);
		}
		trial.calculuateStdDev();
		trial.calculateVarience();
		trial.calculateMean();
		System.out.println(trial);
		this.setProbabilities();
		System.out.println(this);
		this.pi = 4*(successes)/(trials);
	}
	public void runNVar(NVarFunction nVarF){
		//Random rand = new Random();
		
		RandomNumGen numGen = new RandomNumGen(this.getSeed());
		double [] randomVars = new double[this.getNumVars()];
		for (int i = 1; i < this.getTrials()+1; i++ ){
			Statistics [] trialStats = new Statistics [this.getNumVars()];
			for(int j = 0; j < this.getNumVars();j++){
				double randomVar =  numGen.getRandomNum();
				randomVars[j] = randomVar;
				trialStats[j] = new Statistics();
				trialStats[j].run(randomVars[j]);
			}
			double nVarValue = nVarF.Check(randomVars);
			this.incrementSuccesses(nVarValue);
			for(int j = 0; j < this.getNumVars();j++){
				trialStats[j].calculuateStdDev();
				trialStats[j].calculateVarience();
				trialStats[j].calculateMean();
				this.pi = 4*(successes)/(trials);
			}
			//System.out.println(trial);

			
		}
		
		this.setProbabilities();
		System.out.println(this);
		
	}
	public String toString(){
		String answer = "The probability of success is " + this.pSuccess + "\n" + 
				"The probability of failure is " + (100- this.pSuccess) + "\n" +
				"Successes = "+ this.successes+"\n"+
				"Pi = " + 4*(successes)/(trials);	
		return answer;
	}

}
