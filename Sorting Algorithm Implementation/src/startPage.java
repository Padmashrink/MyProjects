import java.io.IOException;
import java.util.Scanner;

public class startPage {
     
	    @SuppressWarnings("resource")
		public static void main(String args[]) throws IOException {
	        
	    	System.out.println("Choose among the following :\n1: Random-Number-Uniform-Distribution-Generation\n" +
	    			"2: Random-Number-Normal-Distribution-Generation\n" +
	    			"3: Heapsort\n4: Quicksort-First\n5: Quicksort-Random\n6: Quicksort-Insertion(Pivot 1st element)\n7: Quicksort-Median\n8: Java's Implementation\n" +
	    			"9: Quicksort-Insertion(Random Pivot)\n10: Quicksort-Insertion(3 Median Heuristics)\n");
	    	
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
	               Heapsort hs = new Heapsort();
	               hs.heapsort();
	               break;
	          case "4":
	        	   QuicksortMain qm = new QuicksortMain();
	        	   qm.QuicksortMain();
	        	   break;
	          case "5":
	        	  QuicksortrandomMain rqs = new QuicksortrandomMain();
	        	  rqs.RandomQuicksortMain();
	        	  break;
	          case "6":
	        	  QuicksortInsertionMain qsi = new QuicksortInsertionMain();
	        	  qsi.QuicksortInsertionMain();
	        	  break;
	          case "7":
	        	  QuicksortMedian qmm = new QuicksortMedian();
	        	  qmm.QuicksortMedian();
	        	  break;
	          case "8":
	        	  mainClass mc = new mainClass();
	        	  mc.mainClass();
	        	  break;
	          case "9":
	        	  QuicksortInsertionRandomM rqim = new QuicksortInsertionRandomM();
	        	  rqim.QuicksortInsertionRandomM();
	        	  break;
	          case "10":
	        	  QuicksortInsertionMedianM rqimm = new QuicksortInsertionMedianM();
	        	  rqimm.QuicksortInsertionMedianM();
	        	  break;
	         default:
	               System.out.println("Program exited!");
	               break;
	         }
	    }
	}