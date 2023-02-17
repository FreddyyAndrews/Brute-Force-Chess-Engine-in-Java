import java.util.ArrayList;
import java.util.Map;

public class MoveGeneration {

    //Offsets for piece movement
    public static final int[] directionOffSets = {8,-8,-1,1,7,-7,9,-9};
    //Store the distance from edge
    public static int[][] numSquaresToEdge;
    //Who's move is it
    int turn; //8 for white or 1 for black
    public MoveGeneration (int turn){
        this.turn = turn;
    }
    public static void precomputedMoveData () {
        //iterate through board
        for(int file = 0; file < 8 ; file++){
            for(int rank = 0; rank< 8; rank++) {
                //Find distance from edge and store in array
                int numNorth = 7 -rank;
                int numSouth = rank;
                int numWest = file;
                int numEast = 7-file;
                int squareIndex = rank*8 +file;
                int[] moveData = {numNorth,numSouth,numWest,numEast,Math.min(numNorth, numWest),Math.min(numSouth, numEast), Math.min(numNorth, numEast), Math.min(numSouth, numWest) };
                numSquaresToEdge[squareIndex] = moveData;
            }
        }
    }

    ArrayList<Move> moves; 

    public ArrayList<Move> generateMoves () {
        moves = new ArrayList<Move>();
        for (int startSquare = 0; startSquare< 64; startSquare++){
            int piece = BoardRepresentation.squares[startSquare];
            if (((piece<16) && turn == 8) || ((piece>16) && turn == 16)){
                if(Piece.isSlidingPiece(piece)){
                    generateSlidingMoves(startSquare, piece);
                }
            }
        }
        return moves;
    }

    private void generateSlidingMoves (int startSquare, int piece) {

        int startDirIndex = (Piece.isType(piece, Piece.bishop)) ? 4 : 0 ;
        int endDirIndex = (Piece.isType(piece, Piece.rook)) ? 4 : 8 ;
        
        for (int directionIndex = startDirIndex; directionIndex < endDirIndex; directionIndex++) {
            for (int n =0; n < numSquaresToEdge[startSquare][directionIndex]; n++){

                int targetSquare = startSquare + directionOffSets[directionIndex]*(n+1);
                int pieceOnTargetSquare = BoardRepresentation.squares[targetSquare];

                // If Blocked by friendly piece

                if (((pieceOnTargetSquare<16) && turn == 8) || ((pieceOnTargetSquare>16) && turn == 16)) {
                    break;
                }

                moves.add(new Move(startSquare, targetSquare));

                //After capturing enemy piece can't move any further

                if(((pieceOnTargetSquare<16) && turn == 16) || ((pieceOnTargetSquare>16) && turn == 8)){
                    break;
                }

            }
        }
    }
    
}
