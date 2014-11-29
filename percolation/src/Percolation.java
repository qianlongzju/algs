public class Percolation {
    private WeightedQuickUnionUF wquf;
    private WeightedQuickUnionUF wqufForBackWash;
    private boolean[] sites;
    private int N;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        this.N = N;
        wquf = new WeightedQuickUnionUF(N * N + 2);
        wqufForBackWash = new WeightedQuickUnionUF(N * N + 1);
        sites = new boolean[N*N];
        for (int i = 0; i < N*N; i++)
            sites[i] = false;
    }

    private int xyTo1D(int i, int j)
    {
        return (i-1)*N + (j-1);
    }
    private boolean checkIndex(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N)
            return false;
        return true;
    }

    public void open(int i, int j) {
        if (!checkIndex(i, j))
            throw new IndexOutOfBoundsException();
        if (!isOpen(i, j)) {
            sites[xyTo1D(i, j)] = true;
            if (checkIndex(i, j - 1) && isOpen(i, j - 1))
            {
                wquf.union(xyTo1D(i, j), xyTo1D(i, j - 1));
                wqufForBackWash.union(xyTo1D(i, j), xyTo1D(i, j - 1));
            }
            if (checkIndex(i, j + 1) && isOpen(i, j + 1))
            {
                wquf.union(xyTo1D(i, j), xyTo1D(i, j + 1));
                wqufForBackWash.union(xyTo1D(i, j), xyTo1D(i, j + 1));
            }
            if (checkIndex(i - 1, j) && isOpen(i - 1, j))
            {
                wquf.union(xyTo1D(i, j), xyTo1D(i - 1, j));
                wqufForBackWash.union(xyTo1D(i, j), xyTo1D(i - 1, j));
            }
            if (checkIndex(i + 1, j) && isOpen(i + 1, j))
            {
                wquf.union(xyTo1D(i, j), xyTo1D(i + 1, j));
                wqufForBackWash.union(xyTo1D(i, j), xyTo1D(i + 1, j));
            }
            if (i == 1)
            {
                wquf.union(xyTo1D(i, j), N * N);
                wqufForBackWash.union(xyTo1D(i, j), N * N);
            }
            if (i == N)
                wquf.union(xyTo1D(i, j), N * N + 1);
        }
    }

    public boolean isOpen(int i, int j) {
        if (checkIndex(i, j))
            return sites[xyTo1D(i, j)];
        else
            throw new IndexOutOfBoundsException();
    }

    public boolean isFull(int i, int j) {
        if (checkIndex(i, j))
            return wqufForBackWash.connected(N * N, xyTo1D(i, j));
        else
            throw new IndexOutOfBoundsException();
    }

    public boolean percolates() {
        return wquf.connected(N * N, N * N + 1);
    }

    public static void main(String[] args) {

    }
}