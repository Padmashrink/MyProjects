
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

public class KthSmallest 
{
    public void kthsmallest()
    {
    	try{
    	//Read the data form the input file
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
    	Scanner sc = new Scanner(inputFile);
    	String[] firstLine;
    	
    	int i=0;
    	firstLine = sc.nextLine().split(" ");
    	int number = Integer.parseInt(firstLine[1]);
    	int size = Integer.parseInt(firstLine[0]);
    	float[] input = new float[size];
    	while(sc.hasNext()){
    		input[i]=Float.parseFloat(sc.nextLine());
    		i++;
    	}
    	
    	//Divide A into n/3,5 or 7 groups of 3,5 or 7 elements each
        System.out.println("Divide in groups of 3, 5 or 7 or random pivot (R)?: ");
        Scanner scn =new Scanner(System.in);
        int tmp = scn.nextInt();
        int groupSize = 0;
        if(tmp == 3){
        	groupSize = 3;
        }
        
        else if(tmp == 5){
        	groupSize = 5;
        }
        
        else if(tmp == 7){
        	groupSize = 7;
        }
        
        else
        	System.out.println("Invalid number");
        
    	 KthSmallest m = new KthSmallest();
    	 double stime = System.nanoTime();
    	 System.out.println(stime);
         System.out.println("The " + number + "th smallest element in the input is " + m.select(input, number, groupSize));
         double etime = System.nanoTime();
         System.out.println(etime);
         double time = etime-stime;
         System.out.println("time= "+time);
    	}
    }
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }     
    //find the median of a array
    float median(float[] array)
    {
        if (array.length == 1)
        {
            return array[0];
        }
        
        int scount = 0;
        
        for (int i = 0 ; i < array.length ; i++)
        {
            for (int j = 0 ; j < array.length ; j++)
            {
                if (array[i] < array[j])
                {
                    scount++;
                }
            }
            
            if (scount == (array.length - 1)/2)
            {
                return array[i];
            }
            scount = 0;
        }
        return -1; 
    }
    
    // finds pivot element of a given array recursively using DeterministicSelect
    float Pivot(float[] array, int groupSize)
    {       
        if (array.length == 1)
        {
            return array[0];
        }
       
        
        int groups = array.length / groupSize;
        if (array.length % groupSize > 0)
        {
            groups++;
        }
        
        float[] setOfMedians = new float[groups];
        
        for (int i = 0 ; i < groups ; i++)
        {
            float[] subset;
            
            if(array.length % groupSize > 0)
            {
            	
                if (i == groups - 1)
                {
                    subset = new float[array.length % groupSize];
                }
                
                else
                {
                    subset = new float[groupSize];
                }
            }
            
            else
            {
                subset = new float[groupSize];
            }
            
            for (int j = 0; j < subset.length ; j++)
            {
                subset[j] = array[groupSize*i+j];
            }
            
            //Find the median of each group
            setOfMedians[i] = median(subset);
        }
        
        //Use select to find the median, p, of the n/5 medians
        float goodPivot = select(setOfMedians, setOfMedians.length/2, groupSize );
        return goodPivot;
    }
 
    float select(float[] array, int k, int groupSize)
    {   
    	if (array.length == 1)
        {
            return array[0];
        }
    	
        float pivot = Pivot(array, groupSize);
        
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
        	return select(r ,k, groupSize);
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
            
        	return select(r , k - A1.size() - 1, groupSize);
        }
    }
}