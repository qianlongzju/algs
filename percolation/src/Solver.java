public class Solver {
    private Node current;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        current = new Node(initial, 0, null);
        MinPQ<Node> minPQ = new MinPQ<Node>();
        MinPQ<Node> twinPQ = new MinPQ<Node>();
        minPQ.insert(current);
        twinPQ.insert(new Node(initial.twin(), 0, null));
        while (true) {
            if (minPQ.isEmpty()) {
                solvable = false;
                break;
            }
            current = minPQ.delMin();
            // StdOut.println("Current Moves: " + current.moves);
            if (current.board.isGoal()) {
                solvable = true;
                break;
            }
            for (Board board : current.board.neighbors())
                if (current.previous == null
                        || (current.previous != null && !board
                                .equals(current.previous.board))) {
                    // StdOut.println("Neighbors: " + board);
                    minPQ.insert(new Node(board, current.moves + 1, current));
                }
            if (twinPQ.isEmpty())
                continue;
            current = twinPQ.delMin();
            if (current.board.isGoal()) {
                solvable = false;
                break;
            }
            for (Board board : current.board.neighbors())
                if (current.previous == null
                        || (current.previous != null && !board
                                .equals(current.previous.board)))
                    twinPQ.insert(new Node(board, current.moves + 1, current));
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solvable)
            return current.moves;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable)
            return null;
        Stack<Board> solution = new Stack<Board>();
        Node temp = current;
        while (temp != null) {
            solution.push(temp.board);
            temp = temp.previous;
        }
        return solution;
    }

    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private Node previous;

        public Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        public int compareTo(Node that) {
            return ((this.board.manhattan() + this.moves)
                    - (that.board.manhattan() + that.moves));
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}