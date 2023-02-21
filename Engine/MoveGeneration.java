import java.util.ArrayList;


public class MoveGeneration {

    //Offsets for piece movement
    public static final int[] directionOffSets = {8,-8,-1,1,7,-7,9,-9};
    public static final int[] knightDirectionOffsets = {16,2,-2,-16};
    public static final int[] pawnDirectionOffsets = {7,8,9};

    //Instance Variables
    PrecomputedMoveData dataFinder;
    BoardRepresentation board;
    public int[][] numSquaresToEdge; 
    private ArrayList<Move> moves; 

    //Constructor
    public MoveGeneration( PrecomputedMoveData dataFinder, BoardRepresentation board){

        this.dataFinder = dataFinder;
        this.board = board;
        numSquaresToEdge = dataFinder.numSquaresToEdge;

    }

    public ArrayList<Move> generateMoves () {
        moves = new ArrayList<Move>();
        for (int startSquare = 0; startSquare< 64; startSquare++){
            int piece = board.squares[startSquare];
            if (Piece.isColourToMove(piece, board.colourToMove)){
                if(Piece.isSlidingPiece(piece)){
                    generateSlidingMoves(startSquare, piece);
                }
                else if (Piece.isType(piece, Piece.knight)) {
                    generateKnightMoves(startSquare);
                }
                else if (Piece.isType(piece, Piece.pawn)) {
                    generatePawnMoves(startSquare);
                }
                else if (Piece.isType(piece, Piece.king)) {
                    generateKingMoves(startSquare);
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
                int pieceOnTargetSquare = board.squares[targetSquare];

                // If Blocked by friendly piece

                if (Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) {
                    break;
                }

                moves.add(new Move(startSquare, targetSquare));

                //After capturing enemy piece can't move any further

                if(!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)){
                    break;
                }

            }
        }
    }

    private void addKnightMove(int startSquare, int offset){

        int targetSquare = startSquare + offset;
        int pieceOnTargetSquare = board.squares[targetSquare];

        if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) || Piece.isType(pieceOnTargetSquare, Piece.none)){
            moves.add(new Move(startSquare, targetSquare));
        }
    }

    private void generateKnightMoves (int startSquare) {

        int distWest = numSquaresToEdge[startSquare][2];
        int distNorth = numSquaresToEdge[startSquare][0];
        int distSouth = numSquaresToEdge[startSquare][1];
        int distEast = numSquaresToEdge[startSquare][3];
        

        if(distNorth > 1 && distWest > 0){

            addKnightMove(startSquare, 15);

        }
        if(distNorth > 1 && distEast > 0){

            addKnightMove(startSquare, 17);
        }
        if(distEast > 1 && distNorth > 0){

            addKnightMove(startSquare, 10);

        }
        if(distEast > 1 && distSouth > 0){

            addKnightMove(startSquare, -6);

        }
        if(distSouth > 1 && distWest > 0){

            addKnightMove(startSquare, -17);

        }
        if(distSouth > 1 && distEast > 0){

            addKnightMove(startSquare, -15);

        }
        if(distWest > 1 && distNorth > 0){

            addKnightMove(startSquare, 6);

        }
        if(distWest > 1 && distSouth > 0){

            addKnightMove(startSquare, -10);

        }
    }

    private void generateKingMoves(int startSquare) {

        for (int directionIndex = 0; directionIndex < 8; directionIndex++) {
            for (int n =0; n < Math.min(numSquaresToEdge[startSquare][directionIndex],1); n++){

                int targetSquare = startSquare + directionOffSets[directionIndex]*(n+1);
                int pieceOnTargetSquare = board.squares[targetSquare];

                // If Blocked by friendly piece

                if (Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) {
                    break;
                }

                moves.add(new Move(startSquare, targetSquare));

                //After capturing enemy piece can't move any further

                if(!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)){
                    break;
                }

            }
        }

    }

    private void generatePawnMoves(int startSquare) {
        //White pawns
        if(board.colourToMove == true){

            //Move forward
            int targetSquare = startSquare + 8;
            int pieceOnTargetSquare = board.squares[targetSquare];
            //Checks that no piece blocks
            if (Piece.isType(pieceOnTargetSquare, Piece.none)){
                moves.add(new Move(startSquare, targetSquare));
            }

            //Left diagonal
            if(numSquaresToEdge[startSquare][4] != 0){
                targetSquare = startSquare + 7;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    moves.add(new Move(startSquare, targetSquare));
                    
                }
            }

            //Right Diagonal

            if(numSquaresToEdge[startSquare][6] != 0){
                targetSquare = startSquare + 9;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    moves.add(new Move(startSquare, targetSquare));
                    
                }
            }

        }

        //Black Pawns

        if(board.colourToMove == false){

            //Move forward
            int targetSquare = startSquare - 8;
            int pieceOnTargetSquare = board.squares[targetSquare];
            //Checks that no piece blocks
            if (Piece.isType(pieceOnTargetSquare, Piece.none)){
                moves.add(new Move(startSquare, targetSquare));
            }

            
            if(numSquaresToEdge[startSquare][5] != 0){
                targetSquare = startSquare - 7;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    moves.add(new Move(startSquare, targetSquare));
                    
                }
            }

            //Right Diagonal

            if(numSquaresToEdge[startSquare][7] != 0){
                targetSquare = startSquare - 9;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    moves.add(new Move(startSquare, targetSquare));
                    
                }
            }

        }

    }

    public void doMove(Move move){
        
        //Change value of target square and reset starting square
        board.squares[move.getTargetSquare()] = board.squares[move.getStartSquare()];
        board.squares[move.getStartSquare()] = 0;
        board.switchTurn();

    }
    
}
