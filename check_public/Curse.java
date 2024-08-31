import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class Curse {
	static class Task {
		public static final String INPUT_FILE = "curse.in";
		public static final String OUTPUT_FILE = "curse.out";

		public void solve() {
			readInput();
		}

		class Graph {
			private int V;
			private ArrayList<ArrayList<Integer>> adj;

			Graph(int v) {
				V = v;
				adj = new ArrayList<ArrayList<Integer>>(v);
				for (int i = 0; i < v; ++i) {
					adj.add(new ArrayList<Integer>());
				}
			}

			boolean hasEdge(int v, int w) {
				return adj.get(v).contains(w);
			}

			void addEdge(int v, int w) {
				if (!hasEdge(v, w)) {
					adj.get(v).add(w);
				}
			}
			/* O functie ajutatoare pentru sortare */ 
			void topologicalSortUtil(int v, boolean[] visited,
					Stack<Integer> stack) {
				visited[v] = true;
				Integer i;
				Iterator<Integer> it = adj.get(v).iterator();
				while (it.hasNext()) {
					i = it.next();
					if (!visited[i]) {
						topologicalSortUtil(i, visited, stack);
					}
				}
				stack.push(v);
			}
			/* Algoritmul de sortare topologica care rezolva problema */
			void topologicalSort() {
				try {
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
							OUTPUT_FILE)));
					Stack<Integer> stack = new Stack<Integer>();
					boolean[] visited = new boolean[V];
					for (int i = 1; i < V; i++) {
						visited[i] = false;
					}
					for (int i = 1; i < V; i++) {
						if (visited[i] == false) {
							topologicalSortUtil(i, visited, stack);
						}
					}
					while (stack.empty() == false) {
						int var = stack.pop();
						System.out.println(var);
						if (!stack.empty()) {
							pw.write(String.valueOf(var) + " ");
						} else {
							pw.write(String.valueOf(var));
						}
					}
					pw.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		int n, m, a;
		int[][] car_race;
		boolean[] verify;
		Graph graph;

		private void readInput() {
			try {
				File file = new File(INPUT_FILE);
				BufferedReader br = new BufferedReader(new FileReader(file));
				String input = br.readLine();
				String[] number = input.split(" ");
				n = Integer.parseInt(number[0]);
				m = Integer.parseInt(number[1]);
				a = Integer.parseInt(number[2]);
				graph = new Graph(m + 1);
				car_race = new int[a][n];
				verify = new boolean[a];
				Arrays.fill(verify, false);
				input = br.readLine();
				number = input.split(" ");
				for (int i = 0; i < n; i++) {
					car_race[0][i] = Integer.parseInt(number[i]);
				}
				input = br.readLine();
				/* Algoritmul propus */
				for (int i = 1; i < a; i++) {
					for (int j = 0; j < n; j++) {
						number = input.split(" ");
						car_race[i][j] = Integer.parseInt(number[j]);
						if (verify[i] == false) {
							if (car_race[i][j] != car_race[i - 1][j]) {
								verify[i] = true;
								graph.addEdge(car_race[i - 1][j], car_race[i][j]);
							}
						}
					}
					System.out.println(input);
					input = br.readLine();
				}
				graph.topologicalSort();
				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public static void main(String[] args) {
		new Task().solve();
	}
}