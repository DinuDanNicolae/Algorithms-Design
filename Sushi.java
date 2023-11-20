import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

class Sushi {

	static int n, m, x;
	static int[] prices;
	static int[][] grades;
	int totalGrade = 0;
	// Sushi(){}

	static int task1() {
		// TODO solve task 1

		int i, j;
		int maxBill = n * x;
		long[][] dp = new long[m + 1][maxBill + 1];

		// vector de "platouri de sushi"
		Sushi[] sushis = new Sushi[m];

		// calculez nota totala a fiecarui "platou de sushi"
		for (j = 0; j < grades[0].length; j++) {
			sushis[j] = new Sushi();
			for (i = 0; i < grades.length; i++) {
				sushis[j].totalGrade += grades[i][j];
			}
		}

		// calculez dp
		for (i = 0; i <= m; ++i) {
			for (int cap = 0; cap <= maxBill; cap++) {

				// construiesc dp pe baza solutiilor subproblemei
				if (i == 0 || cap == 0) {
					dp[i][cap] = 0;
				} else if (prices[i - 1] <= cap) {
					// am suficienti bani pentru platoul i
					long sol_aux = dp[i - 1][cap - prices[i - 1]] + sushis[i - 1].totalGrade;
					dp[i][cap] = Math.max(sol_aux, dp[i - 1][cap]);

				} else { 
					// nu am suficienti bani
					dp[i][cap] = dp[i - 1][cap];
				}

			}
		}
		// solutia finala e pe ultima poizitie din dp
		return (int) dp[m][maxBill];

	}

	static int task2() {
		// TODO solve task 2
		return 0;
	}

	static int task3() {
		// TODO solve task 3
		return 0;
	}

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("sushi.in"));

			final int task = sc.nextInt(); 
			n = sc.nextInt(); 
			m = sc.nextInt(); 
			x = sc.nextInt(); 

			prices = new int[m]; 
			grades = new int[n][m]; 

			// price of each sushi
			for (int i = 0; i < m; ++i) {
				prices[i] = sc.nextInt();
			}

			// each friends rankings of sushi types
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
					grades[i][j] = sc.nextInt();
				}
			}

			int ans;
			switch (task) {
				case 1:
					ans = Sushi.task1();
					break;
				case 2:
					ans = Sushi.task2();
					break;
				case 3:
					ans = Sushi.task3();
					break;
				default:
					ans = -1;
					System.out.println("wrong task number");
			}

			try {
				FileWriter fw = new FileWriter("sushi.out");
				fw.write(Integer.toString(ans) + '\n');
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
