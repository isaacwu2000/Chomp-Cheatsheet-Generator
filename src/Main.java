import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static int n=3;
    private static ArrayList<int[]> positions = new ArrayList<int[]>();

    // Overcomplicated recursive method of getting a list of all possible board positions
    private static void  loop(int dimension, int n, ArrayList<Integer> cols) {
        for (int i=0; i<=n; i++) {
            cols.add(i);
            if (cols.size()==dimension) {
                // Creating a copy of cols to be an array of integers. This array is then added to the positions
                int[] colsIntArray = new int[cols.size()];
                int count = 0;
                for (Integer col : cols) {
                    colsIntArray[count] = (int) col;
                    count++;
                }
                positions.add(colsIntArray);
            }
            else {
                loop(dimension, i, cols);
            }
            cols.remove(cols.size()-1); // Resets the array for the next position
        }
    }

    public static void main(String[] args) {
        loop(n, n, new ArrayList<Integer>());
        for (int[] position : positions) {
            System.out.println(Arrays.toString(position));
        }
    }
}