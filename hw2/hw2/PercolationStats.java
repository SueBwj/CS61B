package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int T; // 进行T次蒙特卡洛模拟
    private double[] openFraction; // 满足渗透条件下打开格子的比例
    private final double c = 1.96;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        Percolation percolation = pf.make(N);
        for (int i = 0; i < T; i++) {
            while (!percolation.percolates()) {
                int x;
                int y;
                do {
                    x = StdRandom.uniform(N);
                    y = StdRandom.uniform(N);
                } while (percolation.isOpen(x, y));
                percolation.open(x, y);
            }
            openFraction[i] = (double) percolation.numberOfOpenSites() / N * N;
        }
    }
    public double mean() {
        return StdStats.mean(openFraction);
    }
    public double stddev() {
        return StdStats.stddev(openFraction);
    }
    public double confidenceLow() {
        return mean() - c * stddev() / Math.sqrt(T);
    }
    public double confidenceHigh() {
        return mean() + c * stddev() / Math.sqrt(T);
    }
}
