import java.util.ArrayList;

public class Main {
    private static int n=3;
    private static ArrayList<Integer[]> positions = new ArrayList<Integer[]>();

    // Overcomplicated recursive method of getting a list of all possible board positions
    private static void  loop(int dimension, int n, ArrayList<Integer> cols) {
        for (int i=0; i<=n; i++) {
            cols.add(i);
            if (cols.size()==dimension) {
                System.out.println(cols);
                cols.remove(cols.size()-1);
            }
            else {
                loop(dimension, i, cols);
                cols.remove(cols.size()-1);
            }
        }
    }

    public static void main(String[] args) {
        loop(n, n, new ArrayList<Integer>());
    }
}