import java.util.ArrayList;
import java.util.Random;


public class TestMoveGeneration {

    private static void generateRandomMoves() {

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation("3qk3/8/8/8/8/8/8/4K3 w - - 0 1");
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);
        
        ArrayList<Move> moves = moveGenerator.generateMoves();

        Random rand = new Random();

        int n = rand.nextInt(moves.size());

        moveGenerator.playMove(moves.get(n));

        System.out.println(moves.size());

        System.out.println(board.getFenString());

        System.out.println("############################");

        moves = moveGenerator.generateMoves();

        rand = new Random();

        n = rand.nextInt(moves.size());

        System.out.println(board.colourToMove);

        moveGenerator.playMove(moves.get(n));

        System.out.println(moves.size());

        System.out.println(board.getFenString());

    }

    private static void testLegalMoveGeneration(String fen){

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation(fen);
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);
        
        moveGenerator.generateLegalMoves();

        ArrayList<Move> moves = moveGenerator.getLegalMoves();

        for(Move move: moves){
            System.out.println(Move.toString(move));
        }

        for(EnPassant enPassant: moveGenerator.getLegalEnPassants()){
            System.out.println(EnPassant.toString(enPassant));
        }

        for(Castle castle : moveGenerator.getCastles()){
            System.out.println(Castle.toString(castle));
        }


        System.out.println(moves.size() + moveGenerator.getLegalEnPassants().size() + moveGenerator.getCastles().size() );

        testLegalMoveGenerationStability(fen);

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

        moveGenerator.playMove(moves.get(n));

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

        moveGenerator.playEnPassant(enPassants.get(n));

        System.out.println(board.getFenString());

    }

    private static void testCastling(String fen){

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation(fen);
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);

        ArrayList<Move> moves = moveGenerator.generateMoves();
        
        ArrayList<Castle> castles = moveGenerator.getCastles();

        Random rand = new Random();

        System.out.println(castles.size());

        int n = rand.nextInt(castles.size());

        moveGenerator.playCastle(castles.get(n));

        System.out.println(board.getFenString());

    }

    public static void testLegalMoveGenerationStability(String fen){

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation(fen);
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);
        
        moveGenerator.generateLegalMoves();

        BoardRepresentation boardCompare = new BoardRepresentation(fen);

        for(int i = 0; i<board.squares.length; i++){
            if(board.squares[i] != boardCompare.squares[i]){
                System.out.println("Difference at : " + i);
            }
        }

    } 

    public static void testLegalCastles(String fen) {

        PrecomputedMoveData dataFinder = new PrecomputedMoveData();

        BoardRepresentation board = new BoardRepresentation(fen);
        
        MoveGeneration moveGenerator = new MoveGeneration(dataFinder,board);
        
        moveGenerator.generateLegalMoves();

        for(Castle castle: moveGenerator.getLegalCastles()){
            System.out.println(Castle.toString(castle));
        }
    }
    
    public static void main(String[] args){

        //generateRandomMoves();
        //testPawnPromotions();
        //testEnPassant();
        //testCastling("4k1n1/8/8/8/8/8/8/R3K2R w KQ - 0 1");
        //testLegalMoveGeneration("4k1n1/8/8/8/8/8/8/R3K2R w KQ - 0 1");
        testLegalCastles("6q1/5k2/8/8/8/8/8/R3K2R w KQ - 0 1");
        

    }
    
}
