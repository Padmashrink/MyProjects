import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


public class Heapsort {

    public void heapsort() throws IOException {
        int n,i;
        //Read the data form the input file
        System.out.println("Select the random numbers to work on: \n1.Normal Distribution data\n2.Uniform Distribution");
        Scanner s = new Scanner(System.in);
        String choice = s.next();
        FileReader file = null;
        
        switch(choice){
        case "1":
        	file = new FileReader("random_normal_dist.txt");
        	break;
        case "2":
        	file = new FileReader("random.txt");
        	break;
        default:	
        	System.out.println("Enter a valid choice either 1 or 2");
        	break;
        }
        
        if(file!=null){
        BufferedReader br = new BufferedReader(file);
      
        //Take only the 1st argument that is the array size
        String n1 = br.readLine().toString();
        String[] n2 = n1.split(" ");
        n = Integer.valueOf(n2[0]);
       
        //Create a new array to put all the data read from the file
        float array[] = new float[n];
        for(i = 0; i<n;i++){
            array[i] = Float.valueOf(br.readLine().toString());
        }
        //call the heapsort function
        double stime = System.nanoTime();
        Heapify hp = new Heapify();
        hp.sort(array);
        double etime = System.nanoTime();
	    double time = etime-stime;
       
        //write the output into the new file
        File ranfile = new File("Heapsort.txt");
        @SuppressWarnings("resource")
        PrintStream write = new PrintStream(ranfile);
        for (i = 0; i < n; i++){
            write.println(array[i]+" ");
        }
        
	    System.out.println("time= "+time);
      }
   }
}