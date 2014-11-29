public class PercolationStats {
    private double[] threshold;
    private int T;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        this.T = T;
        threshold = new double[T];
        for (int t = 0; t < T; t++) {
            Percolation percolation = new Percolation(N);
            int n = 0;
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    n++;
                }
            }
            threshold[t] = n * 1.0 / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(N, T);
        StdOut.println("mean\t\t\t= " + percolationStats.mean());
        StdOut.println("stddev\t\t\t= " + percolationStats.stddev());
        StdOut.println("91% confidence interval\t= "
                + percolationStats.confidenceLo() + ", "
                + percolationStats.confidenceHi());
    }
}