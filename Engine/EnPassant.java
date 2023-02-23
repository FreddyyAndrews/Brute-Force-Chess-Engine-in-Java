public class EnPassant {

    //Structure for movements
    private int startSquare;
    private int targetSquare;
    private int targetPieceSquare;
    

    public EnPassant (int startSquare, int targetSquare, int targetPieceSquare) {
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        this.targetPieceSquare = targetPieceSquare;
    }


    public int getStartSquare() {
        return startSquare;
    }

    public int getTargetSquare() {
        return targetSquare;
    }

    public int getTargetPieceSquare(){
        return targetPieceSquare;
    }

    public static String toString(EnPassant enPassant){
        return enPassant.getStartSquare() + "," + enPassant.getTargetSquare() + "," + enPassant.getTargetPieceSquare();
    }
    
}
