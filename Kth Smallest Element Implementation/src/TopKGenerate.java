import java.util.Random;

public class TopKGenerate {
	
	public static void topk(float[] array, int l, int n, int k){
		//calls the method topk which calls partition to find random pivot
		int pivotIndex = partition(array, l, n);
		if(pivotIndex == k){
			return;
		}
		//Call partition recursively from first to pivot - 1
		else if(k < pivotIndex){
			topk(array, l, pivotIndex -1, k);
		}else{
			//Call partition recursively from pivot+1 to n
			topk(array, pivotIndex +1 , n, k);
		}
	}

	public static int partition(float[] array, int p, int q) {
		//Pick a random Pivot
		Random rand = new Random();
		int pivotIndex = p + rand.nextInt(q - p + 1);
		float temp1 = array[pivotIndex];
		array[pivotIndex] = array[p];
		array[p]=temp1;

		float x = array[p];
		System.out.println("x= "+x);
		int i = p;

		for (int j = (p + 1); j <= q; j++) {
			if (array[j] <= x) {
				i = i + 1;
				if(i < j){
					float temp = array[j];
					array[j] = array[i];
					array[i] = temp;
				}
			}
		}
		float temp2 = array[p];
		array[p] = array[i];
		array[i] = temp2;

		return i;
	}
}