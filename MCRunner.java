package com.amazonaws.codesamples.gsg;
//Copyright (c) <2015> <Phillip Blakey>
import java.security.NoSuchAlgorithmException;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class MCRunner {
	private int numThreads = 0;
	private double pi;
	private int numVars = 0;
	GenerateUUID seedArray;
	public MCRunner(int numThreadsIn, int numVarsIn) throws NoSuchAlgorithmException{
		this.numThreads = numThreadsIn;
		this.numVars = numVarsIn;
	seedArray = new GenerateUUID(numThreadsIn);
	}
	public int getNumThreads(){
		return this.numThreads;
	}
	public int getNumVars(){
		return this.numVars;
	}
	public GenerateUUID getSeedArray(){
		return this.seedArray;
	}
	public static void main(String [] args) throws NoSuchAlgorithmException, InterruptedException{
		
		MCRunner mCRunner = new MCRunner(4,2);
		int numThreads = mCRunner.getNumThreads();

		Thread [] threads = new Thread[numThreads];
		MonteCarloSim[] mcs = new MonteCarloSim[numThreads];
		
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setEndpoint("http://localhost:8000");
		DynamoDB dynamoDB = new DynamoDB(client);
		
		Table table = dynamoDB.getTable("Pi_Statistics");
		
		
		for (int i = 0; i < numThreads; i++){
			mcs[i] = new MonteCarloSim(mCRunner.getSeedArray().getSeed(i),1000000,2);
			threads[i] = new Thread(mcs[i]);
			threads[i].start();
		}
		
		for(int i = 0; i < numThreads; i++) {
			table.putItem(new Item()
			.withPrimaryKey("Seed", mCRunner.getSeedArray().getSeed(i))
			.withDouble("Total_excecution_timePB", mcs[i].getExcecutionTime())
			.withDouble("TimePB", mcs[i].getEndTime()));
			for(int j = 0; j < mCRunner.getNumVars(); j++){
				 UpdateItemSpec updateItemSpec = new UpdateItemSpec()
		            .withPrimaryKey("Seed", mCRunner.getSeedArray().getSeed(i))
		            .withUpdateExpression("set info.StandardDeviation"+j+
		            		" = :s, info.Variance"+j+"=:V, info.Mean"+j+"=:m")
		            .withValueMap(( new ValueMap())
		                .withNumber(":s", mcs[i].getStandardDeviation(j))
		                .withNumber(":V", mcs[i].getVarience(j))
		                .withNumber(":m", mcs[i].getMean(j)));

			}
			threads[i].join();
		}
		
		double pi = mcs[numThreads-1].getSumPi()/numThreads;
		System.out.println("Pi = " + pi);
	}
}
