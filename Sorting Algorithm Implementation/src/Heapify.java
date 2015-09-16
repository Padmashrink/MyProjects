
public class Heapify {

	private static int n;

	public void sort(float array[])
	{       
			//Call heapify
			heapify(array);        
			for (int i = n; i > 0; i--)
			{	
				//Swap the elements and call heap
				swap(array,0, i);
				n = n-1;
				heap(array, 0);

            }
	}     

	public static void heapify(float array[])
    {
			//for array length half, perform maxheap. Taking array length half because right most chaild is 2*i +1. For example we have array length 10, so for i =5, left child is 2i max is 10, so just the half array
    		n = array.length-1;
    		for (int i = n/2; i >= 0; i--)
    				heap(array, i);        

    }
	
	public static void swap(float arr[], int i, int j)
	{
				//swapping the elements
	    		float tmp = arr[i];
	    		arr[i] = arr[j];
	    		arr[j] = tmp; 
	}

    public static void heap(float array[], int i)
    { 
    		//Given i, left child is 2i and right child is 2i+1
    		int leftchild = 2*i ;
    		int rightchild = 2*i + 1;
    		int m = i;
    		//If the root element is smaller than the right and left child then choose a larger element from the subtree and swap with the root element
    		if (leftchild <= n && array[leftchild] > array[i])
    				m = leftchild;
    		if (rightchild <= n && array[rightchild] > array[m])        
    				m = rightchild;
    		if (m != i)
    		{
    			swap(array, i, m);
    			heap(array, m);
    		}
    }    
 
}
