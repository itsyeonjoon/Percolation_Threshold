public class Percolation3D {
    private boolean[] openBlocked;
    private int[] id;
    private int[] size;
    private int opened;
    private final int n;

    public Percolation3D(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n shouldn't be less than 1.");
        }

        id = new int[n * n * n + 2];
        size = new int[n * n * n + 2];

        for (int i = 0; i < (n * n * n + 2); i++) {
            id[i] = i;
            size[i] = 1;
        }

        this.n = n;
        openBlocked = new boolean[n * n * n + 2];
        openBlocked[0] = true; // entrance
        openBlocked[n * n * n + 1] = true; // end
        opened = 0;
    }

    public boolean isOpen(int x, int y, int z) {
        if (x < 1 || x > n || y < 1 || y > n || z < 1 || z > n) {
            throw new IllegalArgumentException();
        }
        return openBlocked[findIndex(x, y, z)];
    }
    public boolean isFull(int x, int y, int z) {
        if (x < 1 || x > n || y < 1 || y > n || z < 1 || z > n) {
            throw new IllegalArgumentException();
        }
        return isOpen(x, y, z) && findRoot(0) == findRoot(findIndex(x, y, z));
    }

    public void open(int x, int y, int z) {
        if (x < 1 || x > n || y < 1 || y > n) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(x, y, z)) {
            openBlocked[findIndex(x, y, z)] = true;
            opened += 1;
            if (x != 1 && isOpen(x - 1, y, z)) {
                union(findIndex(x, y, z), findIndex(x - 1, y, z));
            }
            if (x != n && isOpen(x + 1, y, z)) {
                union(findIndex(x, y, z), findIndex(x + 1, y, z));
            }
            if (y != 1 && isOpen(x, y - 1, z)) {
                union(findIndex(x, y, z), findIndex(x, y - 1, z));
            }
            if (y != n && isOpen(x, y + 1, z)) {
                union(findIndex(x, y, z), findIndex(x, y + 1, z));
            }
            if (z != 1 && isOpen(x, y, z - 1)) {
                union(findIndex(x, y, z), findIndex(x, y, z - 1));
            }
            if (z != n && isOpen(x, y, z + 1)) {
                union(findIndex(x, y, z), findIndex(x, y, z + 1));
            }
            if (z == 1) {
                union(0, findIndex(x, y, z));
            }
            if (z == n) {
                union(n * n * n + 1, findIndex(x, y, z));
            }
        }
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public boolean percolates() {
        return findRoot(0) == findRoot(n * n * n + 1);
    }

    private int findIndex(int x, int y, int z) {
        return ((x - 1) * n + y) + (z - 1) * n * n;
    }

    private int findRoot(int index) {
        if (index > n * n * n + 2 || index < 0) {
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
