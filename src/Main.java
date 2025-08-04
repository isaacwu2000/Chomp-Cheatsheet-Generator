import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static int n=3;
    private static ArrayList<int[]> positions = new ArrayList<int[]>();

    private static class Move {
        public int x;
        public int y;
        Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class CheatReference {
        public int[] position = new int[n];
        public Move move;
        CheatReference(int[] position, Move move) {
            this.position = position;
            this.move = move;
        }
        // Create easy way to print this out
    }

    // Overcomplicated recursive method of getting a list of all possible board positions
    private static void setPositions(int dimension, int n, ArrayList<Integer> cols) {
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
                setPositions(dimension, i, cols);
            }
            cols.remove(cols.size()-1); // Resets the array for the next position
        }
    }

    private static int[] doMove(int[] position, Move move) {
        int[] resultingPosition = new int[n];
        for (int i=0; i<position.length; i++) {
            if (i>=move.x && position[i]>=move.y) {
                resultingPosition[i] = move.y;
            }
            else {
                resultingPosition[i] = position[i];
            }
        }
        return resultingPosition;
    }

    private static ArrayList<Move> getValidMoves(int[] position) {
        ArrayList<Move> validMoves = new ArrayList<Move>();
        for (int col=0; col<position.length; col++) {
            for (int row=0; row<position[col]; row++) {
                validMoves.add(new Move(col, row));
            }
        }
        return validMoves;
    }

    public static void main(String[] args) {
        //setPositions(n, n, new ArrayList<Integer>());
        int[] pos = {2,1,0};
        for (Move move : getValidMoves(pos)) {
            System.out.println("x: "+Integer.toString((Integer)move.x)+", y: "+Integer.toString((Integer)move.y));
        }
    }
}