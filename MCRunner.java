package com.amazonaws.codesamples.gsg;

//Copyright (c) <2015> <Phillip Blakey>
import java.security.NoSuchAlgorithmException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class MCRunner {
	private int numThreads = 0;
	private int numVars = 0;
	private int numTrials = 0;
	GenerateUUID seedArray;

	public MCRunner(int numThreadsIn, int numTrialsIn, int numVarsIn) throws NoSuchAlgorithmException {
		this.numThreads = numThreadsIn;
		this.numVars = numVarsIn;
		this.numTrials = numTrialsIn;
		seedArray = new GenerateUUID(numThreadsIn);
	}

	public int getNumThreads() {
		return this.numThreads;
	}

	public int getNumVars() {
		return this.numVars;
	}

	public GenerateUUID getSeedArray() {
		return this.seedArray;
	}

	public void runMC() throws NoSuchAlgorithmException, InterruptedException {
		Thread[] threads = new Thread[numThreads];
		MonteCarloSim[] mcs = new MonteCarloSim[numThreads];

		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setEndpoint("http://localhost:8000");
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("Pi_Statistics");

		for (int i = 0; i < numThreads; i++) {
			mcs[i] = new MonteCarloSim(seedArray.getSeed(i), numTrials, 2);
			threads[i] = new Thread(mcs[i]);
			threads[i].start();
		}
		for (int i = 0; i < numThreads; i++) {
			threads[i].join();
		}
		for (int i = 0; i < numThreads; i++) { // TODO (phil) Check thread lifetimes
			String std = "";
			String mean = "";
			String varience = "";
			for (int j = 0; j < numVars; j++) {
				std = std + ", " + mcs[i].getStandardDeviation(j);													// num vars
				mean = mean + ", " + mcs[i].getMean(j);
				varience = varience + ", " + mcs[i].getVarience(j);
			}
			
// TODO (phil) Do a unit test to see the difference between toString and what I do in terms of length
			System.out.println("MCRunner Seed0!: " + seedArray.getSeed(0)); 
			System.out.println("MCRunner Seed: " + seedArray.getSeed(i));
			System.out.println("Excecution time: " + mcs[i].getExcecutionTime());
			System.out.println("End time: " + mcs[i].getEndTime());
			table.putItem(new Item().withPrimaryKey("Seed", seedArray.getSeed(i))
					.withDouble("Total_excecution_timePB", mcs[i].getExcecutionTime())
					.withDouble("TimePB", mcs[i].getEndTime())
					.withString("Std", std)
					.withString("Mean", mean)
					.withString("Varience", varience)
					.withDouble("Successes", mcs[i].getSuccesses())
					.withNumber("Trials", mcs[i].getNumTrials()));

		}
	}
}
