package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF site;
    private WeightedQuickUnionUF site2;
    private int N;
    private int topSite;
    private int bottomSite;
    private int numOpen;
    private boolean[][] flagOpen;
    private int xyto1D(int x, int y) {
        return x * N + y;
    }
    private void validateRange(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }
    private void unionNeighbor(int row, int col, int newRow, int newCol) {
        if ((newRow >= 0 && newCol >= 0) && (newRow < N && newCol < N)) {
            if (flagOpen[newRow][newCol]) {
                site.union(xyto1D(row, col), xyto1D(newRow, newCol));
                site2.union(xyto1D(row, col), xyto1D(newRow, newCol));
            }
        }
    }
    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        topSite = N * N;    // 虚拟节点
        bottomSite = N * N + 1; // 虚拟节点
        site = new WeightedQuickUnionUF(N * N + 2); // +2是包含虚拟头底节点
        site2 = new WeightedQuickUnionUF(N * N + 1); // +1是包含虚拟头节点
        flagOpen = new boolean[N][N];
        numOpen = 0;
        // 将topSite与所有的头节点连接
        for (int i = 0; i < N; i++) {
            site.union(xyto1D(0, i), topSite);
            site2.union(xyto1D(0, i), topSite);
        }
        // 将bottomSite与所有的尾节点连接
        for (int i = 0; i < N; i++) {
            site.union(xyto1D(N - 1, i), bottomSite);
        }
        // 所有的节点都是关闭的
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                flagOpen[i][j] = false;
            }
        }
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRange(row, col);
        if (!flagOpen[row][col]) {
            flagOpen[row][col] = true;
            numOpen++;
            unionNeighbor(row, col, row, col - 1);
            unionNeighbor(row, col, row, col + 1);
            unionNeighbor(row, col, row + 1, col);
            unionNeighbor(row, col, row - 1, col);
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRange(row, col);
        return flagOpen[row][col];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRange(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return site2.connected(xyto1D(row, col), topSite);
    }
    // number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }
    // does the system percolate?
    public boolean percolates() {
        if (numOpen <= 0) {
            return false;
        }
        return site.connected(topSite, bottomSite);
    }
}
