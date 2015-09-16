import java.util.Random;


public class QuicksortMedianprob {
	
	public static int count =0;
	public static void quickSort(float array[], int l, int n) {
	      int index = partition(array, l, n);
	      count++;
		  System.out.println("count "+count);
	      if (l < index - 1)
	            quickSort(array, l, index - 1);
	      if (index < n)
	            quickSort(array, index, n);
	}
	
	public static int partition(float array[], int l, int n)
	{
	      int i = l, j = n;
	      float tmp;

	      //Take three random numbers
	      Random rnd = new Random();
	      float pivot1 = array[l + rnd.nextInt(n - l)];
	      float pivot2 = array[l + rnd.nextInt(n - l)];
	      float pivot3 = array[l + rnd.nextInt(n - l)];
	      
	      //Find the median of three random numbers
	      float pivot = median(array, pivot1,pivot2,pivot3);

	      //Move all the elements less than the pivot to left and the elements greater than teh pivot to the right
	      while (i <= j) {
	            while (array[i] < pivot)
	                  i++;
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

	//Find median of 3 random numbers
	public static float median(float arr[], float pivot1, float pivot2, float pivot3){
		if(pivot1 > pivot2) {
	        if(pivot2 > pivot3) return pivot2;
	        if(pivot1 > pivot3) return pivot3;
	        return pivot1;
	    } else {
	        if(pivot2 < pivot3) return pivot2;
	        if(pivot1 < pivot3) return pivot3;
	        return pivot1;
	    }
		
	}
}


