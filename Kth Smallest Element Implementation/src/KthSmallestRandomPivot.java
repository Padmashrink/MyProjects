
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

public class KthSmallestRandomPivot 
{
 
    public void Kthsmallestrandompivot(){
    {
    	try{
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
	    	Scanner scn = new Scanner(inputFile);
	    	String[] firstLine;
    	
	    	int i=0;
	    	
	    	firstLine = scn.nextLine().split(" ");
	    	int number = Integer.parseInt(firstLine[1]);
	    	int size = Integer.parseInt(firstLine[0]);
	    	float[] input = new float[size];
	    	while(scn.hasNext()){
	    		input[i]=Float.parseFloat(scn.nextLine());
	    		i++;
	    	}
	    	
	    	 KthSmallestRandomPivot m = new KthSmallestRandomPivot();
	    	 double stime = System.nanoTime();
	         System.out.println("\nThe " + number + "th smallest element in the input is " + m.select(input, number));
	         double etime = System.nanoTime();
	 	     double time = etime-stime;
	 	     System.out.println("time= "+time);
        	}
    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	       
	    }
	 }
    
    // finds pivot element of a given array recursively using select
    float Pivot(float[] array)
    {       
            return array[0];
    }
    
    float select(float[] array, int k)
    {   
    	if (array.length == 1)
        {
            return array[0];
        }
    	
        float pivot = Pivot(array);
        
        //set is used to ignore duplicate values
        HashSet<Float> A1 = new HashSet<Float>();
        HashSet<Float> A2 = new HashSet<Float>();
        
        for (int i = 0; i < array.length ; i++)
        {
            if (array[i] < pivot)
            {
                A1.add(array[i]);
            }
            else if (array[i] > pivot)
            {
                A2.add(array[i]);
            }
        }
        
        if (A1.size() >= k)
        {
            float[] r = new float[A1.size()];
            java.util.Iterator<Float> i =A1.iterator();
            int index=1;
            r[0] = i.next();
            while(i.hasNext() && index<A1.size()){
            	r[index] = i.next();
            	index++;
            }
        	return select(r ,k);
        }
        
        else if (A1.size() == k-1)
        {
            return pivot;
        }
        
        else
        {
            float[] r = new float[A2.size()];
            java.util.Iterator<Float> i =A2.iterator();
            int index=1;
            r[0] = i.next();
            while(i.hasNext() && index<A2.size()){
            	r[index] = i.next();
            	index++;
            }
        	return select(r , k - A1.size() - 1);
        }
    }
}