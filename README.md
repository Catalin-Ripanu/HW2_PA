## An Algorithmic Assignment illustrating Graph Algos like BFS / DFS, Dijkstra's, Topological Sort, etc

## Curatare
Obviously, it's noticeable that the problem supports applying the BFS/Backtracking algorithm.
Unfortunately, I didn't have enough time to implement the Backtracking part. I would have liked to achieve the following:
- Separate BKT from BFS
- BFS for all robots as source
- BFS for all dirty areas as source
- BFS for each robot and each stain (8 BFS's)
- BKT to generate all possibilities in which stains are taken
- BKT to determine the number of cells for each robot
Given that only BFS was implemented, the complexity is O(N+M).

### Problems with Curatare:
- There were no problems that required difficult debugging, BFS is well-known.

## Fortificatii
It's easily noticeable that this problem can be solved with Dijkstra's algorithm.
I use a boolean vector to store barbarian cells.
A slightly modified version of Dijkstra's algorithm is used because
A vector of costs is formed from each attacker (barbarian) to the source (which is 1), also,
the distance to the node that
Sorting is used to determine the minimum cost/distance.
If the minimum is not the first element in the cost vector, an attempt is made to check the other elements after it.
There's a chance to add all fortifications if the first element after the minimum is large.

### Problems with Fortificatii:
-> There were problems with the time limit, initially I wrote a very complicated and inefficient function implementing Dijkstra's algorithm.
-> Obviously, I read various documents (graph algorithms) and solutions to similar problems (codeforces, infoarena).
-> Debugging was acceptable as the solution was written in C++, not in Java.
The complexity is O(M*logN + N + K*B).

## Beamdrome
It's noticeable that the problem can be solved using BFS + cases to correctly calculate the minimum time.
It should be mentioned that I spent the most time on this problem due to TLE and WA errors (last tests).
A Cell structure is formed to model a cell, there is a 'current_direction' field to simplify things.
Obviously, all neighbors from a certain direction are added as they have the same cost (no rotation is performed).
A get_cost function was used to write less, it calculates the correct cost at a certain moment in time.
BFS was implemented using a Priority Queue (Cell must implement Comparable for this).
Execution stops when the end is reached (point (xf,yf)).
The complexity is O(Max(M,N)*(M+N)) (worst case, that parenthesis O(M+N) represents the complexity of BFS).

### Problems with Beamdrome:
-> Problems with time limit, initially I didn't use optimal elements.
-> A lot of documentation was needed (geeks, stack), I also asked 2 female colleagues and a male colleague for some inspiration (I have inefficient ideas at the beginning, they recommended Lee's algorithm).
-> Debugging was no longer trivial, I had to rewrite the source in C++ to compare results.

## Curse
It's noticeable that the problem is solved with a topological sort applied to a graph constructed based on the input matrix.
Processing begins when reading input data, if there are 2 different elements on the same column, the edge that forms in the graph is added. I also used a verify vector to simplify the proposed algorithm.
At the end, topological sorting is used on the obtained graph.
The complexity of the algorithm is the complexity of topological sorting (O(M+N)).

## References:
- Geeks
- StackOverflow
- Codeforces
- Infoarena
- OCW
