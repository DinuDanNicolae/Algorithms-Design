import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Semnale {

	static int sig_type, x, y;
	static final int mod = 1000000007;

	Semnale() {
	}

	static long[][] l = new long[2003][2003];
	static long[][] dp = new long[2003][2003];

	static void init1() {
		int i, j;

		l[0][0] = 1;

		// crearea matricei pe baza triunghiului lui Pascal
		for (i = 1; i < x + 2; i++) {
			l[i][0] = 1;
			for (j = 1; j < i + 1; j++) {
				l[i][j] = (l[i - 1][j - 1] % mod + l[i - 1][j] % mod) % mod;

			}
		}
	}

	static void init2() {
		dp[0][0] = 0;
		dp[0][1] = 1;
		dp[0][2] = 1;

		int i, j;

		for (i = 1; i < x + 1; i++) {
			dp[i][0] = 1;
		}

		// crearea matricei de combinari
		for (i = 1; i < x + 2; i++) {
			for (j = 1; j < y + 2; j++) {
				if (j < 3) {
					dp[i][j] = (dp[i][j - 1] % mod + dp[i - 1][j] % mod) % mod;
				} else {
					dp[i][j] = ((((dp[i - 1][j - 1] % mod) + (dp[i - 1][j] % mod)) % mod 
						+ (dp[i - 1][j - 2] % mod)) % mod) % mod;
				}
			}
		}
	}

	static int type1() {
		// TODO Compute the number of type 1 signals.

		// crearea matricei pe baza triunghiului lui Pascal
		init1();

		return (int) (l[x + 1][y]) % mod;
	}

	static int type2() {
		// TODO Compute the number of type 2 signals.

		// crearea matricei pe baza triunghiului lui Pascal
		init2();

		return (int) (dp[x][y]) % mod;
	}

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("semnale.in"));

			sig_type = sc.nextInt();
			x = sc.nextInt();
			y = sc.nextInt();

			int ans;
			switch (sig_type) {
				case 1:
					ans = Semnale.type1();
					break;
				case 2:
					ans = Semnale.type2();
					break;
				default:
					ans = -1;
					System.out.println("wrong task number");
			}

			try {
				FileWriter fw = new FileWriter("semnale.out");
				fw.write(Integer.toString(ans));
				fw.close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
