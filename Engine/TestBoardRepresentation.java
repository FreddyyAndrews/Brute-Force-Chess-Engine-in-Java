public class TestBoardRepresentation {

    private static void testFenStringLoading() {
        BoardRepresentation board = new BoardRepresentation("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        for(int piece : board.squares){
            System.out.print(Piece.toString(piece));
            System.out.print(" ");
        }
    }

    private static void testFenStringGenerating() {

        BoardRepresentation board = new BoardRepresentation("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        System.out.println(board.getFenString().equals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
        

        board = new BoardRepresentation("r1bqkbnr/ppppp1pp/n4p2/8/8/3P1N2/PPP1PPPP/RNBQKB1R w KQkq - 0 1");
        System.out.println(board.getFenString());
        System.out.println("r1bqkbnr/ppppp1pp/n4p2/8/8/3P1N2/PPP1PPPP/RNBQKB1R w KQkq - 0 1");

    }

    public static void main(String[] args){
        
        testFenStringLoading();
        testFenStringGenerating();
        
    }
    
}
