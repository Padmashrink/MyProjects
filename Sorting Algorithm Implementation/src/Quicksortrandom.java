import java.util.Random;

public class Quicksortrandom {
	public static int count =0;
	public static void quickSort(float array[], int l, int n) {
		  //Call partition to find pivot and partition the array
	      int pivot = partition(array, l, n);
	      
	      count++;
		  System.out.println("count "+count);
			
	      //Take all the elements less than the pivot and move to the left of the array
	      if (l < pivot - 1)
	            quickSort(array, l, pivot - 1);
	      //Take all the elements more than the pivot and move to the right of the array
	      if (pivot < n)
	            quickSort(array, pivot, n);
	}

	public static int partition(float array[], int l, int n)
	{
	      int i = l;
	      int j = n;
	      float tmp;

	      //Choose pivot Randomly
	      Random random = new Random();
	      float pivot = array[l + random.nextInt(n - l)];

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
}


