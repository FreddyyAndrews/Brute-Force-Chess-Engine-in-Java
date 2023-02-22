import java.util.ArrayList;
import java.util.Random;


public class TestMoveGeneration {

    private static void generateRandomMoves() {

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation();
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);
        
        ArrayList<Move> moves = moveGenerator.generateMoves();

        Random rand = new Random();

        int n = rand.nextInt(moves.size());

        System.out.println(board.colourToMove); //White

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
    
    public static void main(String[] args){
        generateRandomMoves();
        testPawnPromotions();
    }
    
}
