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

        if(piece<16){
            piece = piece - 8;
        }else{
            piece = piece -16;
        }
        return piece == type;
    }

}
