#include <bits/stdc++.h>
using namespace std;

ifstream fin("curatare.in");
ofstream fout("curatare.out");

class Cell {
 public:
	int row;
	int col;
	int dist;
	Cell(int x, int y, int w)
		: row(x), col(y), dist(w) {
	}
};
/* Functia care calculeaza distanta minima intre un robot si o pata*/
int min_dist(vector<vector<char>> &table, int N, int M) {
	Cell source(0, 0, 0);

	bool visit[N][M];
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			if (table[i][j] == 'X') {
				visit[i][j] = true;
			} else {
				visit[i][j] = false;
			}
			if (table[i][j] == 'R') {
				source.row = i;
				source.col = j;
				table[i][j] = '.';
			}
		}
	}

	queue<Cell> queue;
	queue.push(source);
	visit[source.row][source.col] = true;
	/* Algoritmul BFS (fara BKT pentru o rezolvare completa)*/
	while (!queue.empty()) {
		Cell iter = queue.front();
		queue.pop();
		if (table[iter.row][iter.col] == 'S') {
			table[iter.row][iter.col] = 'R';
			return iter.dist;
		}

		if (iter.row + 1 < N &&
			visit[iter.row + 1][iter.col] == false) {
				/* Se adauga in coada vecinul */
			queue.push(Cell(iter.row + 1, iter.col, iter.dist + 1));
			visit[iter.row + 1][iter.col] = true;
		}

		if (iter.row - 1 >= 0 &&
			visit[iter.row - 1][iter.col] == false) {
				/* Se adauga in coada vecinul */
			queue.push(Cell(iter.row - 1, iter.col, iter.dist + 1));
			visit[iter.row - 1][iter.col] = true;
		}

		if (iter.col - 1 >= 0 &&
			visit[iter.row][iter.col - 1] == false) {
				/* Se adauga in coada vecinul */
			queue.push(Cell(iter.row, iter.col - 1, iter.dist + 1));
			visit[iter.row][iter.col - 1] = true;
		}


		if (iter.col + 1 < M &&
			visit[iter.row][iter.col + 1] == false) {
				/* Se adauga in coada vecinul */
			queue.push(Cell(iter.row, iter.col + 1, iter.dist + 1));
			visit[iter.row][iter.col + 1] = true;
		}
	}
	table[source.row][source.col] = 'R';
	return -1;
}

int main() {
	int N, M;
	fin >> N >> M;
	int n_m = 0;
	vector<vector<char>> table(N, vector<char>(M));
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			fin >> table[i][j];
			if(table[i][j] == 'S')
			n_m ++;
		}
	}
    int s = 0;
	while(n_m != 0) {
		s += min_dist(table, N, M);
		n_m --;
	}
	fout << s << '\n';
	fin.close();
	fout.close();
	return 0;
}
