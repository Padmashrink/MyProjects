import java.util.HashSet;


public class TopKGenerateMedian {
	
	public void topk(float[] array, int l, int n, int k, int groupSize){
		int pivotIndex = partition(array, l, n, k, groupSize);
		if(pivotIndex == k){
			return;
		}
		
		else if(k < pivotIndex){
			topk(array, l, pivotIndex -1, k,groupSize);
		}
		
		else{
			topk(array, pivotIndex +1 , n, k,groupSize);
		}
	}

	public static int partition(float[] array, int f, int n, int k, int groupSize) {
		  int i = f;
	      int j = n;
	      float tmp;
	      
	      //Choose pivot as median of medians
	      float pivot = select(array, k, groupSize);
	      
	      while (i <= j) {
	    	   //Take all the elements less than the pivot and move to the left of the array
	            while (array[i] < pivot)
	                  i++;
	            //Take all the elements more than the pivot and move to the right of the array
	            while (array[j] > pivot)
	                  j--;
	            if (i <= j) {
	                  tmp = array[i];
	                  array[i] = array[j];
	                  array[j] = tmp;
	                  i++;
	                  j--;
	            }
	      };

	      return i;
	
	}
	
	static float select(float[] array, int k, int groupSize)
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
	
	static float Pivot(float[] array, int groupSize)
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
	
	static float median(float[] subset)
    {
        if (subset.length == 1)
        {
            return subset[0];
        }
        
        int scount = 0;
        
        for (int i = 0 ; i < subset.length ; i++)
        {
            for (int j = 0 ; j < subset.length ; j++)
            {
                if (subset[i] < subset[j])
                {
                    scount++;
                }
            }
            
            if (scount == (subset.length - 1)/2)
            {
                return subset[i];
            }
            scount = 0;
        }
        
        return -1; 
    }
}

