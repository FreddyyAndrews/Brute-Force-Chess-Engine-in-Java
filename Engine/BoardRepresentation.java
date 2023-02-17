import java.util.HashMap;
import java.util.Map;

public class BoardRepresentation {
    //Array to store Board representation
    public static int[] squares = new int[64];
    private static int[] colourValues = {8,16};
    public static int colourToMove = colourValues[0];

    //Board Constructor with Fen given
    BoardRepresentation(String fen){
        loadPositionFromFenString(fen);
    }

    //Default Board constructor
    BoardRepresentation(){
        loadPositionFromFenString("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    private void loadPositionFromFenString(String fen) {
        //Map Char representation of pieces to numerical representation
        Map<Character, Integer> pieceMap = new HashMap<Character, Integer>();
        
        pieceMap.put('k', Piece.king);
        pieceMap.put('n', Piece.knight);
        pieceMap.put('q', Piece.queen);
        pieceMap.put('p', Piece.pawn);
        pieceMap.put('b', Piece.bishop);
        pieceMap.put('r', Piece.rook);

        //Set File and Rank Before Iteration
        int file = 0;
        int rank = 7;

        //Begins Iterating through chars in fen string
        for(int i=0; i< fen.length(); i++) {
            if(fen.charAt(i) == '/'){
                file = 0;
                rank--;
            } else {
                if(Character.isDigit(fen.charAt(i))){
                    int jump = Integer.parseInt(String.valueOf(fen.charAt(i)));
                    file = file + jump;
                } else {
                    //Determines piece type and colour from character
                    int pieceColour = (Character.isUpperCase(fen.charAt(i))) ? Piece.white : Piece.black ;
                    int pieceType = pieceMap.get(Character.toLowerCase(fen.charAt(i)));
                    //Adds piece to the board representation
                    squares[rank*8+file] = pieceType + pieceColour;
                    file++;
                }
            }
        }
    }

    static void switchTurn(){
        if(colourToMove == 8){
            colourToMove = colourValues[1];
        }else {
            colourToMove = colourValues[0];
        }
    }
} 