public class Castle extends Movement {

    //Structure for Castling
    private int kingstartSquare;
    private int kingEndSquare;
    private int rookStartSquare;
    private int rookEndSquare;
    
    public Castle (int kingstartSquare, int kingEndSquare, int rookStartSquare, int rookEndSquare) {
        this.kingstartSquare = kingstartSquare;
        this.kingEndSquare = kingEndSquare;
        this.rookEndSquare = rookEndSquare;
        this.rookStartSquare = rookStartSquare;
        
    }

    public int getKingStartSquare() {
        return kingstartSquare;
    }

    public int getKingEndSquare() {
        return kingEndSquare;
    }

    public int getRookStartSquare(){
        return rookStartSquare;
    }

    public int getRookEndSquare(){
        return rookEndSquare;
    }

    public static String toString(Castle castle){
        return castle.getKingStartSquare() + "," + castle.getKingEndSquare() + "," + castle.getRookStartSquare() + "," + castle.getRookEndSquare() ;
    }

}
