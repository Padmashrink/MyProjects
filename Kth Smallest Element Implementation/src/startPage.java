import java.io.IOException;
import java.util.Scanner;

public class startPage {
     
	    @SuppressWarnings("resource")
		public static void main(String args[]) throws IOException {
	        
	    	System.out.println("Choose among the following :\n1: Random-Number-Uniform-Distribution-Generation\n" +
	    			"2: Random-Number-Normal-Distribution-Generation\n" +
	    			"3: Top-k-Random\n" +
	    			"4: KthSmallest\n5: KthSmallest-Random-Pivot\n6: Top-k-MedianOfMedian\n");
	    	
	    	Scanner scanner = new Scanner(System.in);
	       	String choice = scanner.next();
	    	choose(choice);
	    	
	    }
	     
	    public static void choose(String choice) throws IOException {
	         switch(choice) {
	          case "1":
	               RandomNumber rm = new RandomNumber();
	               rm.randomNumber_uniform_dist();
	               break;
	          case "2":
	               RandomNumberNormalDist rmd = new RandomNumberNormalDist();
	               rmd.randomNumber_normal_dist();
	               break;
	          case "3":
	        	  TopkRandom topk = new TopkRandom();
	        	  topk.TopkRandom();
	        	  break;
	          case "4":
	        	  KthSmallest ks = new KthSmallest();
	        	  ks.kthsmallest();
	        	  break;
	          case "5":
	        	  KthSmallestRandomPivot ksrp = new KthSmallestRandomPivot();
	        	  ksrp.Kthsmallestrandompivot();
	        	  break;
	          case "6":
	        	  TopKMedian topm = new TopKMedian();
	        	  topm.TopkMedian();
	        	  break;
	         default:
	               System.out.println("Program exited!");
	               break;
	         }
	    }
	}