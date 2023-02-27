public class Move {
    //Structure for movements
    private int startSquare;
    private int targetSquare;
    private int newPiece;
    
    
    //Regular move
    public Move (int startSquare, int targetSquare, int newPiece) {
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        this.newPiece = newPiece;
        
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
