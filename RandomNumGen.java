//Copyright (c) <2015> <Phillip Blakey>
import java.util.Random;


public class RandomNumGen {
	public double getRandomNum(){
		Random rand = new Random();
		double randomNum = rand.nextDouble();
		return randomNum;
	}
}
