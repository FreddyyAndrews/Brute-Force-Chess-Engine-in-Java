public class TestBoardRepresentation {

    private static void testFenStringLoading() {
        BoardRepresentation board = new BoardRepresentation();
        for(int piece : board.squares){
            System.out.print(Piece.toString(piece));
            System.out.print(" ");
        }
    }

    private static void testFenStringGenerating() {

        BoardRepresentation board = new BoardRepresentation();
        System.out.println(board.getFenString() == "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");

        board = new BoardRepresentation("r1bqkbnr/ppppp1pp/n4p2/8/8/3P1N2/PPP1PPPP/RNBQKB1R");
        System.out.println(board.getFenString() == "r1bqkbnr/ppppp1pp/n4p2/8/8/3P1N2/PPP1PPPP/RNBQKB1R");

    }

    public static void main(String[] args){
        testFenStringLoading();
        testFenStringGenerating();
        
    }
    
}
