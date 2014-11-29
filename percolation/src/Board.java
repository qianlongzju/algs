public class Board {
    private int N;
    private char[] blocks;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new char[N * N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                this.blocks[i*N + j] = (char) blocks[i][j];
    }
    
    private Board(char[] blocks, int N) {
        this.N = N;
        this.blocks = new char[N * N];
        for (int i = 0; i < N*N; i++)
            this.blocks[i] = blocks[i];
    }
    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < N*N; i++) {
            if (blocks[i] == 0)
                continue;
            if (blocks[i] != i+1)
                distance++;
        }
        return distance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < N*N; i++) {
            if (blocks[i] == 0)
                continue;
            distance += Math.abs(((blocks[i] - 1) / N) - (i/N))
                        + Math.abs(((blocks[i] - 1) % N) - (i % N));
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N*N-1; i++) {
            if (blocks[i] != i+1)
                return false;
        }
        if (blocks[N*N-1] != 0)
            return false;
        return true;
    }

    // a board that is obtained by exchanging two adjacent blocks in the same
    // row
    public Board twin() {
        int row = 0;
        if (blocks[0] == 0 || blocks[1] == 0)
            row = 1;
        char tmp = blocks[row*N];
        blocks[row*N] = blocks[row*N + 1];
        blocks[row*N + 1] = tmp;
        Board twin = new Board(blocks, N);
        blocks[row*N + 1] = blocks[row*N];
        blocks[row*N] = tmp;
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (N != that.dimension())
            return false;
        for (int i = 0; i < N*N; i++)
            if (blocks[i] != that.blocks[i])
                return false;
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        int i = 0, j = 0;
        boolean found = false;
        for (int ii = 0; ii < N; ii++) {
            for (int jj = 0; jj < N; jj++) {
                if (blocks[ii*N + jj] == 0) {
                    i = ii;
                    j = jj;
                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }
        if (i > 0) {
            blocks[i*N + j] = blocks[(i-1)*N + j];
            blocks[(i-1)*N + j] = 0;
            neighbors.push(new Board(blocks, N));
            blocks[(i - 1)*N + j] = blocks[i*N + j];
            blocks[i*N + j] = 0;
        }
        if (i < N - 1) {
            blocks[i*N + j] = blocks[(i + 1)*N + j];
            blocks[(i + 1)*N + j] = 0;
            neighbors.push(new Board(blocks, N));
            blocks[(i + 1)*N + j] = blocks[i*N + j];
            blocks[i*N + j] = 0;
        }
        if (j > 0) {
            blocks[i*N + j] = blocks[i*N + (j - 1)];
            blocks[i*N + (j - 1)] = 0;
            neighbors.push(new Board(blocks, N));
            blocks[i*N + (j - 1)] = blocks[i*N + j];
            blocks[i*N + j] = 0;
        }
        if (j < N - 1) {
            blocks[i*N + j] = blocks[i*N + (j + 1)];
            blocks[i*N + (j + 1)] = 0;
            neighbors.push(new Board(blocks, N));
            blocks[i*N + (j + 1)] = blocks[i*N + j];
            blocks[i*N + j] = 0;
        }
        return neighbors;
    }

    // string representation of this board (in the output format specified
    // below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", (int) blocks[i*N + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

    }
}