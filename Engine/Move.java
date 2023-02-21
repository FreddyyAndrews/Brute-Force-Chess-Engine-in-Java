public class Move {
    //Structure for movements
    private int startSquare;
    private int targetSquare;

    public Move (int startSquare, int targetSquare) {
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
    }

    public int getStartSquare() {
        return startSquare;
    }

    public int getTargetSquare() {
        return targetSquare;
    }
}
