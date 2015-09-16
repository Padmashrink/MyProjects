import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;


public class RandomNumber {
	
	public void randomNumber_uniform_dist() throws FileNotFoundException {
		
		int i,n,k,firstrange,lastrange;
	    @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
	    Random random = new Random();
	    //System.out.println("Enter the starting range number");
	    firstrange = 1;
	    //System.out.println("Enter the ending range number");
	    lastrange = 100000;
	    System.out.println("Enter the number of random numbers to be generated");
	    n = scanner.nextInt();
	    System.out.println("Enter the kth element");
	    k = scanner.nextInt();
	    File ranfile = new File("random.txt");
	    @SuppressWarnings("resource")
		PrintStream write = new PrintStream(ranfile);
	    write.println(n+" "+k);

	    for (i= 1; i<= n; ++i){
	      
	    	if (firstrange > lastrange) {
	    		throw new IllegalArgumentException("Wrong Range. Example (1,1000)");
	    	}
	    	
	    	long range = (long)lastrange - (long)firstrange + 1;
		    long f = (long)(range * random.nextDouble());
		    float randomNumber =  (f + firstrange);
		    
		    write.println(randomNumber);
	    }
	    System.out.println("Random Numbers Generated in random.txt file");
	    write.close();
	    
	  }
} 