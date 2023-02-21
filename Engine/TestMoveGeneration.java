import java.util.ArrayList;
import java.util.Random;


public class TestMoveGeneration {

    private static void generateRandomMove() {

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

        System.out.println("############################");

        moves = moveGenerator.generateMoves();

        rand = new Random();

        n = rand.nextInt(moves.size());

        System.out.println(board.colourToMove);

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
    
    public static void main(String[] args){
        generateRandomMove();
    }
    
}
