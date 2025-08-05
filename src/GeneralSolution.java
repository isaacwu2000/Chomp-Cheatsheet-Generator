import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* Citations
I didn't use Generative AI for this project except for Google AI Search Overviews
I only used the internet (primarily Geeks for Geeks and W3 Schools) for reference
I copied quite a bit of code from this Java file guide: https://www.w3schools.com/java/java_files_create.asp
- Isaac Wu, August 4th, 2025
*/

public class GeneralSolution {
    private static int n=9;
    private static ArrayList<int[]> positions = new ArrayList<int[]>();

    private static class Move {
        public int x;
        public int y;
        Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "(" + Integer.toString((Integer) x) + ", " + Integer.toString((Integer) y) + ")";
        }
    }

    private static class CheatReference {
        public int[] position = new int[n];
        public Move winningMove;
        public boolean lost = false;
        // If there is a winning move
        CheatReference(int[] position, Move move) {
            this.position = position;
            this.winningMove = move;
        }
        // if there is a winning move
        CheatReference(int[] position, boolean lost) {
            this.position = position;
            this.lost = true;
        }
        @Override
        public String toString() {
            if (lost) {
                return "position: " + Arrays.toString(position) + ", winning move: none";
            }
            else {
                return "position: " + Arrays.toString(position) + ", winning move: " + winningMove.toString();
            }
        }
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
                if (col!=0 || row!=0) {
                    validMoves.add(new Move(col, row));
                }
            }
        }
        return validMoves;
    }

    public static ArrayList<CheatReference> CreateCheatsheet() {
        setPositions(n, n, new ArrayList<Integer>()); // The ArrayList 'positions' now contains all the positions
        positions.remove(0); // Removing [0,0,0..
        ArrayList<CheatReference> CheatReferences = new ArrayList<CheatReference>();
        CheatReferences.add(new CheatReference(positions.get(0), true));

        // Iterating over all the positions and seeing if there is a winning move for each position
        for (int i=1; i<positions.size(); i++) {
            int[] position = positions.get(i);
            boolean winningPosition = false;

            ArrayList<Move> validMoves = getValidMoves(position);
            for (Move move : validMoves) {
                int[] resultingPosition = doMove(position, move);
                // Seeing if the move on results in a losing position for the opponent according to the CheatReferences
                for (int a=0; a<CheatReferences.size(); a++) {
                    CheatReference ref = CheatReferences.get(a);
                    if (ref.lost && Arrays.equals(ref.position, resultingPosition)) {
                        CheatReferences.add(new CheatReference(position, move));
                        winningPosition = true;
                        break;
                    }
                    if (winningPosition) {break;}
                }
                if (winningPosition) {break;}
            }
            if (!winningPosition) {
                CheatReferences.add(new CheatReference(position, true));
            }

        }
        return CheatReferences;
    }

    public static String CreateCheatsheetFile() {
        String filename = "cheatsheet_"+String.valueOf(n)+"x"+String.valueOf(n)+".txt";
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return filename;
    }

    public static void WriteCheatsheet(String text) {
        String filename = CreateCheatsheetFile();
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(text);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String CreateCheatsheetString(ArrayList<CheatReference> CheatReferences) {
        String cheatsheetString = "Chomp Cheatsheet for a " + String.valueOf(n) + "x" + String.valueOf(n) + " Board\n";
        for (CheatReference ref : CheatReferences) {
            cheatsheetString += ref.toString() + "\n";
        }
        return cheatsheetString;
    }

    public static void main(String[] args) {
        System.out.println("Running");
        ArrayList<CheatReference> CheatReferences = CreateCheatsheet();
        System.out.println("Created Cheatsheet");
        WriteCheatsheet(CreateCheatsheetString(CheatReferences));
        System.out.println("Wrote Cheatsheet to a file");
    }
}