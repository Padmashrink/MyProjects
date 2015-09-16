import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


public class TopKMedian {

	public void TopkMedian() throws IOException {
		int n,i;
		System.out.println("Select the random numbers to work on: \n1.Normal Distribution data\n2.Uniform Distribution");
    	Scanner s = new Scanner(System.in);
    	String choice = s.next();
    	FileReader inputFile = null;        
    	switch(choice){
	    	case "1":
	    	inputFile = new FileReader("random_normal_dist.txt");
	    	break;
	    	case "2":
	    	inputFile = new FileReader("random.txt");
	    	break;
	    	default:	
	    	System.out.println("Enter a valid choice either 1 or 2");
	    	break;
    	}
    	        
    	if(inputFile!=null){
		//Read the data form the input file
		BufferedReader br = new BufferedReader(inputFile);
		//Take only the 1st argument that is the array size
		String n1 = br.readLine().toString();
		String[] n2 = n1.split(" ");
		n = Integer.valueOf(n2[0]); 
		
		//Take second argument as k
		int k = Integer.valueOf(n2[1]);

		 //Create a new array to put all the data read from the file
		float arr[] = new float[n];
		
		for(i = 0; i<n;i++){
			arr[i] = Float.valueOf(br.readLine().toString());
		}
		
		//Divide A into n/3,5 or 7 groups of 3,5 or 7 elements each
        System.out.println("Divide in groups of 3, 5 or 7");
        Scanner scn =new Scanner(System.in);
        int groupSize = scn.nextInt();
        
		if(!(groupSize==3 || groupSize==5 || groupSize==7))
		{
			System.out.println("Enter a valid number:");
		}
		
		else{
		//Check if the array length is empty
		if (arr.length == 0) {
			System.out.println("Empty Array");
			System.exit(0);
		} 
		//Check if the array length is one
		else if (arr.length == 1) {
			System.out.println("Array has one element");
		} 
		
		else {
			//Else call top k method to find top k elements
			TopKGenerateMedian topme = new TopKGenerateMedian();
			double stime = System.nanoTime();
	    	System.out.println(stime);
	    	topme.topk(arr, 0, n-1, k, groupSize);
			double etime = System.nanoTime();
	        System.out.println(etime);
	        double time = etime-stime;
	        System.out.println("time= "+time);
		}

		//write the output into the new file
		File ranfile = new File("Top_k_Median_of_Median.txt");
		@SuppressWarnings("resource")
		PrintStream write = new PrintStream(ranfile);
		for (i = arr.length-1; i >= arr.length - k; i--){
			write.println(arr[i]+" ");
		}
	}
		}
}
}




