import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


public class QuicksortInsertionRandomM {
	public void QuicksortInsertionRandomM() throws IOException {
		int n,i;
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
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(inputFile);

			//Take only the 1st argument that is the array size
			String n1 = br.readLine().toString();
			String[] n2 = n1.split(" ");
			n = Integer.valueOf(n2[0]);
			System.out.println(n);

			System.out.println("first no "+n);

			//Create a new array to put all the data read from the file
			float arr[] = new float[n];
			for(i = 0; i<n;i++){
				arr[i] = Float.valueOf(br.readLine().toString());
			}
			//call quicksort and Insertion Sort
			double stime = System.nanoTime();
			QuicksortInsertionRandom qsir = new QuicksortInsertionRandom();
			qsir.quickSort(arr);
			double etime = System.nanoTime();
			double time = etime-stime;


			//write the output into the new file
			File ranfile = new File("Quicksort_Insertion_Random.txt");
			@SuppressWarnings("resource")
			PrintStream write = new PrintStream(ranfile);
			for (i = 0; i < n; i++){
				write.println(arr[i]+" ");
			}
			System.out.println("time= "+time);
		}	
	}
}