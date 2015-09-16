import java.util.Random;


public class QuicksortInsertionMedian {

	public static int count =0;
	public static void quickSort(float[] array) {
		//Call Quicksort
        QuickSort(array, 0, array.length - 1);
        //Call Insertion sort
        insertionSort(array, 0, array.length - 1);
	}
	
	public static void QuickSort(float[] array, int l, int n) {
        int size = n - l + 1;
        //When the subarray size becomes less than 6, then call Insertion Sort
        if (size < 9)
            insertionSort(array, l, n);
        //Else recursively call Quicksort
        else {
        	count++;
    		System.out.println("count "+count);
        	//Find the pivot and swap elements less to pivot to the left of the array and elements greater than the pivot to the right of teh array and recursively call Quicksort
            int pivot = partitionIt(array, l, n);
            QuickSort(array, l, pivot - 1);
            QuickSort(array, pivot + 1, n);
        }
    }

    public static int partitionIt(float[] array, int l, int n) {
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
  
   public static void insertionSort(float[] array, int l, int n) {
        int i, j;

        //When the array size is less than the specified l, then perform insertion sort.Find the right place for the element and swap it.
        for (j = l + 1; j <= n; j++) {
            float temp = array[j];
            i = j;

            while (i > l && array[i - 1] >= temp) {
                array[i] = array[i - 1];
                --i;
            }
            array[i] = temp;
        }
    }
   
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

