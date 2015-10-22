//Copyright (c) <2015> <Phillip Blakey>
import java.util.Random;
public class TrialRunner {
	private double maxValue = 0;
	private double minValue = 1;
	private int trials = 0;
	private double successes = 0;
	private double pSuccess = 0;
	private int numVars;
	
	public TrialRunner(int trialsIn, int numVarsIn){
		this.trials = trialsIn;
		this.numVars = numVarsIn;
	}
	public int getNumVars(){
		return this.numVars;
	}
	public double getMaxValue(){
		return this.maxValue;
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
		RandomNumGen numGen = new RandomNumGen();// needs seed
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
		
	}
	public void runNVar(NVarFunction nVarF){
		//Random rand = new Random();
		
		RandomNumGen numGen = new RandomNumGen();//needs seed
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
