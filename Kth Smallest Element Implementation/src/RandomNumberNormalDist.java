import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public final class RandomNumberNormalDist {
  
  public void randomNumber_normal_dist() throws FileNotFoundException{
	RandomNumberNormalDist gaussian = new RandomNumberNormalDist();
    int MEAN = 1000000; 
    int VARIANCE = 800000;
    int i,n,k,firstrange,lastrange;
    @SuppressWarnings("resource")
	Scanner scanner = new Scanner(System.in);
  
    System.out.println("Enter the number of random numbers to be generated");
    n = scanner.nextInt();
    System.out.println("Enter the kth element");
    k = scanner.nextInt();
    File ranfile = new File("random_normal_dist.txt");
    @SuppressWarnings("resource")
	PrintStream write = new PrintStream(ranfile);
    write.println(n+" "+k);

    for (i= 1; i<= n; ++i){
    	write.println(Math.abs(gaussian.getGaussian(MEAN, VARIANCE)));
    }
  }
    
  private Random fRandom = new Random();
  
  private float getGaussian(int aMean, int aVariance){
    float num = (int) (aMean + fRandom.nextGaussian() * aVariance);
    return num;
  }
}
