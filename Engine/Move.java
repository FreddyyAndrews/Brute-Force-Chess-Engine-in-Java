public class Move extends Movement{
    //Structure for movements
    private int startSquare;
    private int targetSquare;
    private int newPiece;
    public boolean promotion;
    
    
    //Regular move
    public Move (int startSquare, int targetSquare, int newPiece) {
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        this.newPiece = newPiece;
        this.promotion = false;
        
    }

    //Promotion
    public Move (int startSquare, int targetSquare, int newPiece, boolean promotion) {
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        this.newPiece = newPiece;
        this.promotion = promotion;
        
    }
    
    public int getStartSquare() {
        return startSquare;
    }

    public int getTargetSquare() {
        return targetSquare;
    }

    public int getNewPiece(){
        return newPiece;
    }

    public static String toString(Move move){
        return move.getStartSquare() + "," + move.getTargetSquare() + "," + move.getNewPiece();
    }
}
