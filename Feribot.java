import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Feribot {

	public static long numberOfFerries(long[] weights, long cost, long k) {

		long nrFerries = 0;
		int currentCar = 0;
		long sum = 0;

		// cautam numarul de feriboturi necesare pentru costul dat

		while (currentCar < weights.length) {

			if (sum + weights[currentCar] > cost) {
				
				nrFerries++;
				// Conditie in cazul in care depasim numarul de feriboturi disponibile
				if (nrFerries > k) {
					return ++nrFerries;
				}

				sum = 0;
			}

			sum += weights[currentCar];
			currentCar++;
		}

		return nrFerries + 1;
	}

	public static long minimCost(long lowerBound, long upperBound, long[] weights, long NrOfFerys) {

		Long currentMinCost = (long) 0;

		// Binary search pentru a gasi costul minim

		while (lowerBound < upperBound) {

			long midPoint;

			midPoint = (lowerBound + upperBound) / 2;
			System.out.println(numberOfFerries(weights, midPoint, NrOfFerys));

			// Daca nr de feriboturi gasit este mai mic decat cel disponibil, ne mutam in
			// stanga

			if (numberOfFerries(weights, midPoint, NrOfFerys) <= NrOfFerys) {

				currentMinCost = midPoint;
				upperBound = midPoint;
			} else { // altfel, ne mutam in dreapta
				lowerBound = midPoint + 1;
			}

		}

		return currentMinCost;
	}

	public static void main(String[] args) {

		int NrOfCars, NrOfFerys;
		long totalSum = 0;
		long maxWeight = 0;
		long[] weights;

		try {

			FileReader inputFile = new FileReader("feribot.in");
			var data = new MyScanner(inputFile);

			NrOfCars = data.nextInt();
			NrOfFerys = data.nextInt();

			weights = new long[NrOfCars];

			// calculam suma totala si greutatea maxima (limitele inferioare si superioare)

			for (int i = 0; i < NrOfCars; i++) {
				weights[i] = data.nextLong();
				if (weights[i] > maxWeight) {
					maxWeight = weights[i];
				}
				totalSum += weights[i];
			}

		} catch (FileNotFoundException e) {

			System.out.println("Error: input file not found");
			return;
		}

		try {

			PrintStream outFile = new PrintStream("feribot.out");

			long costulminim = minimCost(maxWeight, totalSum, weights, NrOfFerys);
			outFile.print(costulminim);
			outFile.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error: output file not found");
			return;

		}
	}

	private static class MyScanner {
		private BufferedReader br;
		private StringTokenizer st;

		public MyScanner(Reader reader) {
			br = new BufferedReader(reader);
		}

		public String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}