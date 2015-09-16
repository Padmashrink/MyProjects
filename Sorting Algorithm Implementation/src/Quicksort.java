
public class Quicksort {
	public static int count =0;
	public static void quickSort(float[] array, int f, int n)
	{
		if (f >= n)
			return;
		//Partition the elements based on the pivot
		int pivot = partition(array, f, n);
		
		count++;
		System.out.println("count "+count);
		
		//call quicksort from 1st element to pivot -1 and quicksort from pivot +1 to last element recursively
		quickSort(array, f, pivot - 1);	
		quickSort(array, pivot + 1, n);	
	
	}
	
	public static void swap(float[] array, int i, int j)
	{
		//Swap the elements
		float temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	
	public static int partition(float[] array, int f, int n)
	{
		//Choose 1st element as pivot
		float pivot = array[f];
		int left = f;
		int right = n;
		
		//till the 1st element is less than the last element do
		while (left < right)
		{
			//choose the elements less than the pivot and move them to the left of the pivot and also choose the numbers more than the pivot and move it to the right of the pivot and call quick sort recursively 
			while (array[left] <= pivot && left < right)
				left++;
			
			while (array[right] > pivot)
				right--;
			
			if (left < right)
				swap(array, left, right);
		}
		
		swap(array, f, right);
		return right;
	}
	
}
