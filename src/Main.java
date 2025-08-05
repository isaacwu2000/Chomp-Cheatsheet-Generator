import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// This file is optimized for creating a 10x10 cheatsheet
// The more general solution that is less optimized is in GeneralSolution.java

/* Citations
I didn't use Generative AI for this project except for Google AI Search Overviews
I only used the internet (primarily Geeks for Geeks and W3 Schools) for reference
I copied quite a bit of code from this Java file guide: https://www.w3schools.com/java/java_files_create.asp
- Isaac Wu, August 4th, 2025
*/

/*
Efficiency stuff:
- use 10 for-loops
- minimize variables, method calls, iterations
- todo: remove the "move" class and make everything with the x and y directly
- todo: remove the "cheatreference" class
- *make separate list for lost*
 */

public class Main {
    private static ArrayList<CheatReference> CheatSheet = new ArrayList<CheatReference>();
    private static ArrayList<int[]> LostPositions = new ArrayList<int[]>(); // This allows faster reference for determining winningMove
    private static int[] GameOverPosition = {0, 0, 0};//, 0, 0, 0, 0, 0, 0, 0};

    private static class Move {
        public int x;
        public int y;
        Move(int x, int y) {this.x = x; this.y = y;}
        @Override
        public String toString() {return "(" + Integer.toString((Integer) x) + ", " + Integer.toString((Integer) y) + ")";}
    }

    private static class CheatReference {
        public int[] position;
        public Move winningMove;
        public boolean lost = false;
        CheatReference(int[] position, Move move) {this.position = position; this.winningMove = move;}
        CheatReference(int[] position, boolean lost) {this.position = position; this.lost = true;}
        @Override
        public String toString() {
            if (lost) {return "position: " + Arrays.toString(position) + ", winning move: none";}
            else {return "position: " + Arrays.toString(position) + ", winning move: " + winningMove.toString();}
        }
    }

    public static void CreateCheatsheet() {
        // Iterating through all possible valid positions
        for (int a=1; a<=3; a++) {for (int b=0; b<=a; b++) {for (int c=0; c<=b; c++){// for (int d=0; d<=c; d++){for (int e=0; e<=d; e++){for (int f=0; f<=e; f++){for (int g=0; g<=f; g++){for (int h=0; h<=g; h++){for (int i=0; i<=h; i++){for (int j=0; j<=i; j++){
            int[] position = {a, b, c};//, d, e, f, g, h, i, j};
            boolean winningPosition = false; // Once ONE winning position is found, we can break out all the search loops
            if (Arrays.equals(position, GameOverPosition)) {LostPositions.add(position);}
            // Iterating over all valid moves
            else {
                for (int col = 0; col < position.length; col++) {
                    for (int row = 0; row < position[col]; row++) {
                        if (col != 0 || row != 0) {
                            // Doing the move and recording the resulting position
                            Move move = new Move(col, row);
                            int[] resultingPosition = new int[3];
                            for (int column = 0; column < 3; column++) {
                                if (column >= move.x && position[column] >= move.y) {
                                    resultingPosition[column] = move.y;
                                } else {
                                    resultingPosition[column] = position[column];
                                }
                            }
                            // Checking if a given move is winning by seeing if it results in a losing position for the opponent
                            for (int[] losingPosition : LostPositions) {
                                if (Arrays.equals(resultingPosition, losingPosition)) {
                                    CheatSheet.add(new CheatReference(position, move));
                                    winningPosition = true;
                                    break;
                                }
                            }
                            if (winningPosition) {break;}
                        }
                    }
                    if (winningPosition) {break;}
                }
                if (!winningPosition) {LostPositions.add(position);}
            }
        }}}//}}}}}}}
    }
    public static String CreateCheatsheetFile() {
        String filename = "cheatsheet_3x3.txt";
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
        String cheatsheetString = "Chomp Cheatsheet for a 3x3 Board\n";
        for (CheatReference ref : CheatReferences) {
            cheatsheetString += ref.toString() + "\n";
        }
        return cheatsheetString;
    }

    public static void main(String[] args) {
        System.out.println("starting");
        CreateCheatsheet();
        System.out.println("done");
        WriteCheatsheet(CreateCheatsheetString(CheatSheet));
    }
}