import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Beamdrone {
	static class Task {
		public static final String INPUT_FILE = "beamdrone.in";
		public static final String OUTPUT_FILE = "beamdrone.out";

		public void solve() {
			readInput();
			getResult();
		}

		int n, m;
		int xi, yi, xf, yf;
		char[][] grid;
		boolean[][] obst;
		int res;

		class Cell implements Comparable<Cell> {
			public int cost;
			public int i;
			public int j;
			public char dir;

			public Cell(int cost, int i, int j, char dir) {
				this.cost = cost;
				this.i = i;
				this.j = j;
				this.dir = dir;
			}

			public int compareTo(Cell var) {
				return Integer.compare(cost, var.cost);
			}
		}

		private void readInput() {
			try {
				File file = new File(INPUT_FILE);
				BufferedReader br = new BufferedReader(new FileReader(file));
				String input = br.readLine();
				String[] number = input.split(" ");
				n = Integer.parseInt(number[0]);
				m = Integer.parseInt(number[1]);
				input = br.readLine();
				number = input.split(" ");
				xi = Integer.parseInt(number[0]);
				yi = Integer.parseInt(number[1]);
				xf = Integer.parseInt(number[2]);
				yf = Integer.parseInt(number[3]);
				input = br.readLine();
				grid = new char[n][m];
				obst = new boolean[n][m];
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						grid[i][j] = input.charAt(j);
						if (input.charAt(j) == 'W') {
							obst[i][j] = true; 
						} else {
							obst[i][j] = false;
						}
					}
					input = br.readLine();
				}
				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/* Se iau in calcul toate cazurile pentru simplitate */

		int get_cost(char curr_dir, char new_dir) {
			if (curr_dir == 'S' && (new_dir == 'E' || new_dir == 'W')) {
				return 1; 
			} else if (curr_dir == 'S' && new_dir == 'N') {
				return 2; 
			}
			if (curr_dir == 'W' && (new_dir == 'N' || new_dir == 'S')) {
				return 1; 
			} else if (curr_dir == 'W' && new_dir == 'E') {
				return 2; 
			}
			if (curr_dir == 'E' && (new_dir == 'N' || new_dir == 'S')) {
				return 1; 
			} else if (curr_dir == 'E' && new_dir == 'W') {
				return 2; 
			}
			if (curr_dir == 'N' && (new_dir == 'E' || new_dir == 'W')) {
				return 1; 
			} else if (curr_dir == 'N' && new_dir == 'S') {
				return 2; 
			}
			return 0;
		}

		void getResult() {

			int i, j;
			int[][] minCost = new int[n][m];

			for (i = 0; i < n; i++) {
				for (j = 0; j < m; j++) {
					minCost[i][j] = Integer.MAX_VALUE;
				}
			}

			PriorityQueue<Cell> dq = new PriorityQueue<>();

			dq.add(new Cell(0, xi, yi, 'C'));
			minCost[xi][yi] = 0;

			/* BFS-ul aplicat */

			while (!dq.isEmpty()) {
				Cell top = dq.poll();
				int currI = top.i;
				int currJ = top.j;
				int costCell = top.cost;
				char direction = top.dir;
				int moveCost;

				if (currI == xf && currJ == yf) {
					writeOutput(minCost[xf][yf]);
				}
				/* Cazul Nord */
				if (currI - 1 >= 0 && obst[currI - 1][currJ] == false) {
					int k = currI;
					while (k >= 0 && obst[k][currJ] == false) {
						moveCost = get_cost(direction, 'N') + costCell;
						if (minCost[k][currJ] == Integer.MAX_VALUE) {
							minCost[k][currJ] = moveCost;
							dq.add(new Cell(moveCost, k, currJ, 'N'));
						}
						k--;
					}
				}
				/* Cazul Sud */
				if (currI + 1 < n && obst[currI + 1][currJ] == false) {
					int k = currI;
					while (k < n && obst[k][currJ] == false) {
						moveCost = get_cost(direction, 'S') + costCell;
						if (minCost[k][currJ] == Integer.MAX_VALUE) {
							minCost[k][currJ] = moveCost;
							dq.add(new Cell(moveCost, k, currJ, 'S'));
						}
						k++;
					}
				}
				/* Cazul Est */
				if (currJ + 1 < m && obst[currI][currJ + 1] == false) {
					int k = currJ;
					while (k < m && obst[currI][k] == false) {
						moveCost = get_cost(direction, 'E') + costCell;
						if (minCost[currI][k] == Integer.MAX_VALUE) {
							minCost[currI][k] = moveCost;
							dq.add(new Cell(moveCost, currI, k, 'E'));
						}
						k++;
					}
				}
				/* Cazul Vest */
				if (currJ - 1 >= 0 && obst[currI][currJ - 1] == false) {
					int k = currJ;
					while (k >= 0 && obst[currI][k] == false) {
						moveCost = get_cost(direction, 'W') + costCell;
						if (minCost[currI][k] == Integer.MAX_VALUE) {
							minCost[currI][k] = moveCost;
							dq.add(new Cell(moveCost, currI, k, 'W'));
						}
						k--;
					}

				}
			}
		}

		private void writeOutput(int val) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
						OUTPUT_FILE)));
				pw.write(String.valueOf(val));
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
