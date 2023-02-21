import java.util.HashMap;
import java.util.Map;

public class Piece {
    //Associate Number with Each Piece
    static final int none = 0;
    static final int king = 1;
    static final int pawn = 2;
    static final int knight = 3;
    static final int bishop = 4;
    static final int rook = 5;
    static final int queen = 6;

    //Associate number with each colour
    static final int white = 8;
    static final int black = 16;

    public static boolean isSlidingPiece(int piece){

        if(piece<16){
            piece = piece - 8;
        }else{
            piece = piece -16;
        }

        if(piece == 5 || piece == 6 || piece == 4){
            return true;
        }

        return false;

    }

    public static boolean isType(int piece, int type){
        if(piece != 0){
            if(piece<16){
                piece = piece - 8;
            }else{
                piece = piece -16;
            }
        }
        
        return piece == type;
    }

    public static boolean isColourToMove(int piece, boolean colourToMove) {

        if (((piece<16) && colourToMove == true) || ((piece>16) && colourToMove == false)){
            if (!Piece.isType(piece, Piece.none)){
                return true;
            }  
        }
        return false;
    }

    public static boolean isWhite(int piece){

        if(piece<16){
            return true;
        }
        return false;
        
    }

    public static char getType(int piece){

        Map<Integer, Character> pieceMap = new HashMap<Integer, Character>();
        
        pieceMap.put(Piece.king, 'k');
        pieceMap.put(Piece.knight, 'n');
        pieceMap.put(Piece.queen, 'q');
        pieceMap.put(Piece.pawn, 'p');
        pieceMap.put(Piece.bishop, 'b');
        pieceMap.put(Piece.rook, 'r');

        return pieceMap.get(piece);

    }

    public static String toString(int piece) {
        if(piece == 0){
            return "none";
        }
        else if(piece>16){
            piece = piece -16;
            return (Character.toString(getType(piece))).toLowerCase() ;
        }
        else if(piece<16){
            piece = piece -8;
            return (Character.toString(getType(piece))).toLowerCase() ;
        }
        return "";
    }

}
