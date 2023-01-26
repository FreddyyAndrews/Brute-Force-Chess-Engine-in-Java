import java.util.HashMap;
import java.util.Map;

public class BoardRepresentation {
    //Array to store Board representation
    int[] squares = new int[64];
    Piece piece = new Piece();

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
        
        pieceMap.put('k', piece.king);
        pieceMap.put('n', piece.knight);
        pieceMap.put('q', piece.queen);
        pieceMap.put('p', piece.pawn);
        pieceMap.put('b', piece.bishop);
        pieceMap.put('r', piece.rook);

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
                    int pieceColour = (Character.isUpperCase(fen.charAt(i))) ? piece.white : piece.black ;
                    int pieceType = pieceMap.get(Character.toLowerCase(fen.charAt(i)));
                    //Adds piece to the board representation
                    squares[rank*8+file] = pieceType + pieceColour;
                    file++;
                }
            }
        }
    }
} 