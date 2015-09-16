
public class QuicksortInsertion {
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
        int left = l;
        int right = n;
        //Choose 1st element as pivot
        float pivot = array[l];
        //Until 1st element < last element
        while (left < right)
        {
        	//Move the elements less than the pivot to the left of the array
            if (array[left] < pivot)
            {
                left++;
                continue;
            }
            //Move the elements more than the pivot to the right of the array
            if (array[right] > pivot)
            {
                right--;
                continue;
            }
            //Swap the elements
            float tmp = array[left];
            array[left] = array[right];
            array[right] = tmp;
            left++;
        }
        return left;
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
}

