/*
 * Acest schelet citește datele de intrare și scrie răspunsul generat de voi,
 * astfel că e suficient să completați cele două metode.
 *
 * Scheletul este doar un punct de plecare, îl puteți modifica oricum doriți.
 *
 * Dacă păstrați scheletul, nu uitați să redenumiți clasa și fișierul.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Vector;

public class Nostory {
	public static void main(final String[] args) throws IOException {
		var scanner = new MyScanner(new FileReader("nostory.in"));

		var task = scanner.nextInt();
		var n = scanner.nextInt();
		var moves = task == 2 ? scanner.nextInt() : 0;

		var a = new int[n];
		for (var i = 0; i < n; i += 1) {
			a[i] = scanner.nextInt();
		}

		var b = new int[n];
		for (var i = 0; i < n; i += 1) {
			b[i] = scanner.nextInt();
		}

		try (var printer = new PrintStream("nostory.out")) {
			if (task == 1) {
				printer.println(solveTask1(a, b));
			} else {
				printer.println(solveTask2(a, b, moves));
			}
		}
	}

	private static long solveTask1(int[] a, int[] b) {

		int nrElem = 2 * a.length;
		Vector<Long> v = new Vector<Long>(nrElem);
		int i;
		long aux;
		int j;

		//introducem elementele din prima lista in vector
		for (i = 0; i < a.length; i++) {
			v.add(i,(long)a[i]);
		}

		//introducem elementele din a doua lista in vector
		for (i = a.length; i < nrElem; i++) {
			v.add(i,(long)b[i - a.length]);
		}

		//sortam vectorul
		v.sort(Comparator.reverseOrder());

		long score = 0;
		//calculam scorul cu primele n elemente din vectorul sortat
		for (i = 0; i < a.length; i++) {
			score += v.get(i);
		}

		return score;
	}

	private static long solveTask2(int[] a, int[] b, int moves) {

		int nrElem = 2 * a.length;
		long[] v = new long[nrElem];
		long[] freqVector = new long[nrElem];
		int i;
		int k = 0;
		long score = 0;

		Vector<Long> vect = new Vector<Long>();
		Integer[] indices = new Integer[nrElem];

		// introducem elementele din prima si a doua lista in vector
		for (i = 0; i < a.length; i++) {
			v[i] = a[a.length - i - 1];
			v[i + a.length] = b[a.length - i - 1];
		}

		// intializam vectorul de frecv, vectorul de sortare si cel de indici
		for (i = 0; i < nrElem; i++) {
			vect.add(v[i]);
			indices[i] = i;
			freqVector[i] = 0;
		}

		// sortam vectorul de indici in functie de valoarea din vectorul de sortare
		Arrays.sort(indices, new Comparator<Integer>() {
			public int compare(Integer i1, Integer i2) {
				return vect.get(i1).compareTo(vect.get(i2));
			}
		});

		// nou vector sortat
		Vector<Long> sortedV = new Vector<Long>();
		for (i = 0; i < nrElem; i++) {
			sortedV.add(vect.get(indices[i]));
		}

		int count = 0;

		// calculam frecventa elementelor
		for (i = 0; i < nrElem; i++, count++) {
			if (count == a.length) {
				break;
			}
			if (v[i] > v[i + a.length]) {
				freqVector[i] = 1;
			} else {
				freqVector[i + a.length] = 1;
			}
		}

		int c = 0;

		// calculam scorul
		for (i = 0; i < nrElem; i++) {

			if (freqVector[indices[nrElem - i - 1]] == 1 && c < a.length) {
				c++;
				score += sortedV.get(nrElem - i - 1);
			} else {
				// adugam o mutare
				k++;
				if (k <= moves && c < a.length) {
					c++;
					score += sortedV.get(nrElem - i - 1);
				}

			}

		}

		return score;
	}

	/**
	 * A class for buffering read operations, inspired from here:
	 * https://pastebin.com/XGUjEyMN.
	 */
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
