import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.StringTokenizer;

public class Badgpt {

	static final int mod = 1000000007;

	public static long fibo(long n) {

		long[][] F = new long[][] { { 1, 1 }, { 1, 0 } };

		// cazul de baza
		if (n == 0) {
			return 0;
		}
		power(F, n - 1);

		return F[0][0] % mod;
	}

	static void multiply(long[][] F, long[][] M) {
		long x;
		x = (((F[0][0] % mod) * (M[0][0] % mod)) % mod) 
			+ ((F[0][1] % mod) * (M[1][0] % mod) % mod) % mod;
		long y;
		y = (((F[0][0] % mod) * (M[0][1] % mod)) % mod) 
			+ ((F[0][1] % mod) * (M[1][1] % mod) % mod) % mod;
		long z;
		z = (((F[1][0] % mod) * (M[0][0] % mod)) % mod) 
			+ ((F[1][1] % mod) * (M[1][0] % mod) % mod) % mod;
		long w;
		w = (((F[1][0] % mod) * (M[0][1] % mod)) % mod) 
			+ ((F[1][1] % mod) * (M[1][1] % mod) % mod) % mod;
		F[0][0] = x % mod;		
		F[0][1] = y % mod; 
		F[1][0] = z % mod; 
		F[1][1] = w % mod;
	}

	static void power(long[][] F, long n) {
		if (n == 0 || n == 1) {
			return;
		}
		long[][] M = new long[][] { { 1, 1 }, { 1, 0 } };

		power(F, n / 2);
		multiply(F, F);

		if (n % 2 != 0) {
			multiply(F, M);
		}	
	}

	public static long substrings(String string) {

		int i, j;
		long count = 1;

		// parcurgem sirul de caractere
		for (i = 0; i < string.length(); i++) {

			// daca gasim un 'n' sau 'u' in sir, cautam numarul de aparitii
			if (string.charAt(i) == 'n' || string.charAt(i) == 'u') {

				j = i + 1;

				// incepem o noua parcurgere pentru a determina numarul de aparitii
				while (j < string.length() && Character.isDigit(string.charAt(j))) {
					j++;
				}
				
				// extragem numarul de aparitii
				String nrStr = string.substring((int) (i + 1), (int) j);

				// convertim numarul de aparitii din string in long
				long nr = Long.parseLong(nrStr);

				// calculam numarul de subsecvente
				long fibo_nr = fibo(nr + 1);
				count = (count % mod) * (fibo_nr % mod) % mod;
			}

		}

		return count;
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

	public static void main(String[] args) {

		try {

			FileReader inputFile = new FileReader("badgpt.in");
			var data = new MyScanner(inputFile);

			String s;

			s = data.nextLine();

			PrintStream outFile = new PrintStream("badgpt.out");

			long rez = substrings(s);
			outFile.print(rez);
			outFile.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error: input file not found");
			return;
		}
	}
}

