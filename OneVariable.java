import java.util.Random;

//Copyright (c) <2015> <Phillip Blakey>
public class OneVariable {
	private double min = 0;
	private double max = 0;
	private int trials = 0;
	private int successes = 0;
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
	public int getTrials(){
		return this.trials;
	}
	public void incrementSuccesses(){
		this.successes++;
	}
	public int getSuccesses(){
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
	public static void main(String[] args) {
		Random rand = new Random();
		OneVariable instance = new OneVariable(0.0,1.0,10000);
		for (int i = 0; i < instance.getTrials();i++ ){
			double randomVar = instance.randomDouble(rand);
			if(OneVarFunction.Check(randomVar)==true){
				instance.incrementSuccesses();
			}
			instance.setProbabilities();
		}
		System.out.println(instance);
	}
	public String toString(){
		String answer = "The probability of success is " + this.pSuccess + " and the probability of failure is " + (100- this.pSuccess);
		return answer;
	}

}
