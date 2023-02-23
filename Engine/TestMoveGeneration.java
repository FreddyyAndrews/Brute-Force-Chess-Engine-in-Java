import java.util.ArrayList;
import java.util.Random;


public class TestMoveGeneration {

    private static void generateRandomMoves() {

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);
        
        ArrayList<Move> moves = moveGenerator.generateMoves();

        Random rand = new Random();

        int n = rand.nextInt(moves.size());

        moveGenerator.doMove(moves.get(n));

        System.out.println(moves.size());

        System.out.println(board.getFenString());

        System.out.println("############################");

        moves = moveGenerator.generateMoves();

        rand = new Random();

        n = rand.nextInt(moves.size());

        System.out.println(board.colourToMove);

        moveGenerator.doMove(moves.get(n));

        System.out.println(moves.size());

        System.out.println(board.getFenString());

    }

    private static void testPawnPromotions(){

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation("8/P7/8/8/8/8/p7/8 b");
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);
        
        ArrayList<Move> moves = moveGenerator.generateMoves();

        Random rand = new Random();

        int n = rand.nextInt(moves.size());

        System.out.println(board.colourToMove); //White

        System.out.println("List of moves:");

        for(Move move: moves){
            System.out.println(Move.toString(move));
        }

        moveGenerator.doMove(moves.get(n));

        System.out.println(board.getFenString());
    }

    private static void testEnPassant() {

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation("8/8/8/1Pp5/8/8/8/8 w - c6 0 1");
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);

        ArrayList<Move> moves = moveGenerator.generateMoves();
        
        ArrayList<EnPassant> enPassants = moveGenerator.getEnPassants();

        Random rand = new Random();

        System.out.println(enPassants.size());

        int n = rand.nextInt(enPassants.size());

        moveGenerator.doEnPassant(enPassants.get(n));

        System.out.println(board.getFenString());

    }

    private static void testCastling(){

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation("8/8/8/8/8/8/8/R1B1K2R w KQkq - 0 1");
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);

        ArrayList<Move> moves = moveGenerator.generateMoves();
        
        ArrayList<Castle> castles = moveGenerator.getCastles();

        Random rand = new Random();

        System.out.println(castles.size());

        int n = rand.nextInt(castles.size());

        moveGenerator.doCastle(castles.get(n));

        System.out.println(board.getFenString());

    }
    
    public static void main(String[] args){
        //generateRandomMoves();
        //testPawnPromotions();
        //testEnPassant();
        testCastling();

    }
    
}
