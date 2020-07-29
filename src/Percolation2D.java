public class Percolation2D {
    private boolean[] openBlocked;
    private int[] id;
    private int[] size;
    private int opened;
    private final int n;

    public Percolation2D(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        id = new int[n * n + 2];
        size = new int[n * n + 2];
        // gives unique id numbers to each index of the id array
        // at the moment, each element's size is 1;
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            size[i] = 1;
        }

        this.n = n;
        openBlocked = new boolean[n * n + 2];
        openBlocked[0] = true;
        openBlocked[n * n + 1] = true;
        opened = 0;
    }

    public boolean isOpen(int x, int y) {
        if (x < 1 || x > n || y < 1 || y > n) {
            throw new IllegalArgumentException();
        }
        return openBlocked[findIndex(x, y)];
    }
    public boolean isFull(int x, int y) {
        if (x < 1 || x > n || y < 1 || y > n) {
            throw new IllegalArgumentException();
        }
        return isOpen(x, y) && findRoot(0) == findRoot(findIndex(x, y));
    }

    public void open(int x, int y) {
        if (x < 1 || x > n || y < 1 || y > n) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(x, y)) {
            openBlocked[findIndex(x, y)] = true;
            opened += 1;
            if (x != 1 && isOpen(x - 1, y)) {
                union(findIndex(x, y), findIndex(x - 1, y));
            }
            if (x != n && isOpen(x + 1, y)) {
                union(findIndex(x, y), findIndex(x + 1, y));
            }
            if (y != 1 && isOpen(x, y - 1)) {
                union(findIndex(x, y), findIndex(x, y - 1));
            }
            if (y != n && isOpen(x, y + 1)) {
                union(findIndex(x, y), findIndex(x, y + 1));
            }
            if (x == 1) {
                union(0, findIndex(x, y));
            }
            if (x == n) {
                union(n * n + 1, findIndex(x, y));
            }
        }
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return findRoot(0) == findRoot(n * n + 1);
    }

    private int findIndex(int x, int y) {
        return (x - 1) * n + y;
    }

    private int findRoot(int index) {
        if (index > n * n + 2 || index < 0) {
            throw new IllegalArgumentException();
        }
        while (index != id[index]) {
            index = id[index];
        }
        return index;
    }

    private void union(int i, int j) {
        int rootOfI = findRoot(i);
        int rootOfJ = findRoot(j);
        if (rootOfI != rootOfJ) {
            if (size[rootOfJ] > size[rootOfI]) {
                id[rootOfI] = rootOfJ;
                size[rootOfJ] += size[rootOfI];
            } else {
                id[rootOfJ] = rootOfI;
                size[rootOfI] += size[rootOfJ];
            }
        }
    }

    public static void main(String args[]) {

    }
}
