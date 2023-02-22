import java.util.HashMap;
import java.util.Map;

public class BoardRepresentation {
    //Array to store Board representation
    public int[] squares = new int[64];
    public boolean colourToMove;
    public boolean wqCastle;
    public boolean wkCastle;
    public boolean bkCastle;
    public boolean bqCastle;

    //Board Constructor with Fen given
    BoardRepresentation(String fen){
        loadPositionFromFenString(fen);
        
    }

    //Default Board constructor
    BoardRepresentation(){
        loadPositionFromFenString("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w");
        
    }

    public void loadPositionFromFenString(String fen) {
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
        int i;
        for(i=0; i< fen.length(); i++) {
            if(fen.charAt(i) == ' '){
                break;
            }
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

        //Get turn
        char turn = fen.charAt(i+1);
        if(Character.toLowerCase(turn) == 'w'){
            colourToMove = true;
        }
        else if (Character.toLowerCase(turn) == 'b'){
            colourToMove = false;
        }

    }

    public void switchTurn(){
        colourToMove = !colourToMove;
    }

    public String getFenString() {

        Map<Integer, Character> pieceMap = new HashMap<Integer, Character>();
        
        pieceMap.put(Piece.king, 'k');
        pieceMap.put(Piece.knight, 'n');
        pieceMap.put(Piece.queen, 'q');
        pieceMap.put(Piece.pawn, 'p');
        pieceMap.put(Piece.bishop, 'b');
        pieceMap.put(Piece.rook, 'r');

        
        String returnVal ="";
        int counter = 0;
        int rank = 7;
        for (int i = 0; i<64; i++) {

            
            int file = i%8;
            
            if(squares[rank*8+file] == 0) {
                counter++;
            }else{
                if(counter != 0){
                    returnVal = returnVal+counter;
                    counter = 0;
                }
                int piece = squares[rank*8+file];
                if (Piece.isWhite(piece)){
                    piece = piece -8;
                    returnVal = returnVal + Character.toUpperCase(pieceMap.get(piece));
                } else {
                    piece = piece -16;
                    returnVal = returnVal + Character.toLowerCase(pieceMap.get(piece));
                }
            }
            if(file ==7){
                rank--;
                if(counter != 0){
                    returnVal = returnVal+counter;
                    counter = 0;
                }
                if(i != 63) {
                    returnVal = returnVal + "/";
                }
                
            }
            
        }
        
        if(colourToMove){ //white's turn
            returnVal = returnVal + " " + "w";
        }else{ //Black's turn
            returnVal = returnVal + " " + "b";
        }
        return returnVal;
    }


} 