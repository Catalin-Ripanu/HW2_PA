#include <string.h>
#include <bits/stdc++.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <set>
#include <queue>
using namespace std;

using namespace std;

#define INF 1e15

class Task {
 public:
    void solve() {
        read_input();
        get_result();
        print_output();
    }

 private:
/* Structuri necesare pentru ceea ce va urma */
    int n, m, b;
    long long k;
    vector<int> barb_list;
    vector<bool> verif_list;
    vector<vector<pair<int, long long>>> adj_list;
    long long min;

    void read_input() {
        /* Se citesc datele */
        ifstream fin("fortificatii.in");
        fin >> n >> m >> k >> b;
        barb_list = vector<int>(b + 1);
        verif_list = vector<bool>(n + 1, false);
        int input;
        for (int i = 1; i <= b; i++) {
            fin >> input;
            barb_list[i] = input;
            verif_list[input] = 1;
        }
        /* Se formeaza lista de adiacenta */
        adj_list = vector<vector<pair<int, long long>>>(n + 1);
        int a, b, c;
        for (int i = 1; i <= m; i++) {
            fin >> a >> b >> c;
            adj_list[a].push_back({b, c});
            adj_list[b].push_back({a, c});
        }
        fin.close();
    }
    /* Functia care returneaza rezultatul final */
    void get_res_final(vector<long long> dist_list) {
        int begin = 1;
        min = dist_list[0];
        bool finish = false;

        for (; k != 0 && finish == false;) {
            if (min < dist_list[dist_list.size() - 1]) {
                int frt = dist_list[dist_list.size() - 1] - min;
                for (int i = begin; i < (int)dist_list.size(); i++, begin++) {
                    if (dist_list[i] > min) {
                        frt = dist_list[i] - min;
                        break;
                    }
                }
                int left = k / begin;

                if (begin == 1) {
                    if (frt >= k) {
                        min = min + k;
                        finish = true;
                    } else {
                        min = min + frt;
                        k = k - frt;
                    }
                } else {
                    if (left >= frt) {
                        min = min + frt;
                        for (int j = 1; j < begin; j++) {
                            dist_list[j] += frt;
                        }
                        k = k - frt * begin;
                    } else {
                        if (k >= begin) {
                            min = min + left;
                        }
                        finish = true;
                    }
                }
            } else {
                if (k >= (int)dist_list.size()) {
                    min = min + k / dist_list.size();
                }
                finish = true;
            }
        }
    }

    void get_result() {
        int source = 1;
        vector<long long> res = dijkstra(source);
        vector<long long> dist_list;
        for (int &brb : barb_list) {
            for (pair<int, long long> &iter : adj_list[brb]) {
                if (res[iter.first] == INF) {
                    continue;
                }
                dist_list.push_back(res[iter.first] + iter.second);
            }
        }
        /* Se sorteaza pentru a lua costul/distanta minim */
        sort(dist_list.begin(), dist_list.end());
        get_res_final(dist_list);
    }
  /* Functia care implementeaza algoritmul lui Dijkstra */
    vector<long long> dijkstra(int source) {
        vector<long long> d(n + 1, INF);
        set<pair<long long, int>> pq;
        d[source] = 0;
        pq.insert({d[source], source});

        while (!pq.empty()) {
            auto top = pq.begin();
            int node = (*top).second;
            pq.erase(top);

            for (auto pair_node : adj_list[node]) {
                int neigh = pair_node.first;
                long long w = pair_node.second;
                if (verif_list[neigh]) {
                    continue;
                }

                if (d[node] + w < d[neigh]) {
                    pq.erase({d[neigh], neigh});
                    d[neigh] = d[node] + w;
                    pq.insert({d[neigh], neigh});
                }
            }
        }

        return d;
    }

    void print_output() {
        ofstream fout("fortificatii.out");
        fout << min;
        fout.close();
    }
};

int main() {
    auto *task = new (nothrow) Task();
    if (!task) {
        cerr << "Error!\n";
        return -1;
    }
    task->solve();
    delete task;
    return 0;
}
