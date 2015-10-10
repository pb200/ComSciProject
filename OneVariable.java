//Copyright (c) <2015> <Phillip Blakey>
import java.util.Random;
public class OneVariable {
	private double min = 0;
	private double max = 0;
	private double maxValue = 0;
	private double minValue = 1;
	private int trials = 0;
	private double successes = 0;
	private double pSuccess = 0;
	
	public OneVariable(double minIn, double maxIn, int trialsIn){
		this.min = minIn;
		this.max = maxIn;
		this.trials = trialsIn;
	}
	public double getMax(){
		return this.max;
	}
	public double getMin(){
		return this.min;
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
	public double randomDouble(Random rand){
		double randomNum = this.min + (this.max- this.min) * rand.nextDouble();;
		return randomNum;
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

	
	public void run(OneVarFunction oneVarF){
		Random rand = new Random();
		Statistics trial = new Statistics();
		for (int i = 1; i < this.getTrials()+1; i++ ){
			double randomVar =  this.randomDouble(rand);
			trial.run(randomVar);
			//double oneVarValue = oneVarF.Check(randomVar);
			double oneVarValue = randomVar;
			this.incrementSuccesses(oneVarValue);
		}
		trial.calculuateStdDev();
		trial.calculateVarience();
		trial.calculateMean();
		System.out.println(trial);
		this.setProbabilities();
		System.out.println(this);
	}
	public String toString(){
		String answer = "The probability of success is " + this.pSuccess + "\n" + 
				"The probability of failure is " + (100- this.pSuccess) + "\n";	
		return answer;
	}

}