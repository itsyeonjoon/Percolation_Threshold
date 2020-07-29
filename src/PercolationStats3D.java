import java.util.*;

public class PercolationStats3D {

    private final double[] tests;
    private Percolation3D test;
    private static final double CONFIDENCE_95 = 1.96;
    private Random rand;
    private static Scanner scanner;

    // perform independent trials on an n-by-n grid
    public PercolationStats3D(int n, int trials) {
        rand = new Random();
        scanner = new Scanner(System.in);

        tests = new double[trials];
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < trials; i++) {
            test = new Percolation3D(n);
            while (!test.percolates()) {
                int row = rand.nextInt(n) + 1;
                int col = rand.nextInt(n) + 1;
                int height = rand.nextInt(n) + 1;
                test.open(row, col, height);
            }
            tests[i] = ((double) test.numberOfOpenSites()) / (n * n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0.0;
        for (int i = 0; i < tests.length; i++) {
            sum += tests[i];
        }
        return (sum / tests.length);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double diff = 0.0;
        double mean = mean();
        for (int i = 0; i < tests.length; i++) {
            diff += Math.pow((tests[i] - mean), 2);
        }
        double std = Math.sqrt(diff / (tests.length - 1));
        return std;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(tests.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(tests.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.print("# of n on a n x n x n cube: ");
        int n = scanner.nextInt();
        System.out.print("# of independent computational experiments: ");
        int t = scanner.nextInt();

        PercolationStats3D P1 = new PercolationStats3D(n, t);
        System.out.println("mean:                   = " + P1.mean());
        System.out.println("stddev                  = " + P1.stddev());
        System.out.println("95% confidence interval = [" + P1.confidenceLo() + ", " + P1.confidenceHi() + "]");
    }
}
