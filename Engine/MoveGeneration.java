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

                moves.add(new Move(startSquare, targetSquare, board.squares[startSquare]));

                //After capturing enemy piece can't move any further

                if(!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    break;
                }

            }
        }
    }

    private void addKnightMove(int startSquare, int offset){

        int targetSquare = startSquare + offset;
        int pieceOnTargetSquare = board.squares[targetSquare];

        if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) || Piece.isType(pieceOnTargetSquare, Piece.none)){
            moves.add(new Move(startSquare, targetSquare, board.squares[startSquare]));
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

                moves.add(new Move(startSquare, targetSquare, board.squares[startSquare]));

                //After capturing enemy piece can't move any further

                if(!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)){
                    break;
                }

            }
        }

    }

    private void addWhitePawnMove(int startSquare, int targetSquare){

        if(targetSquare>55 && targetSquare < 64) {
            for (int i = 3; i<7; i++){
                moves.add(new Move(startSquare, targetSquare, Piece.white+i));
            }
        } else {
            moves.add(new Move(startSquare, targetSquare, board.squares[startSquare]));
        }
    }

    private void addBlackPawnMove(int startSquare, int targetSquare){

        if(targetSquare>-1 && targetSquare < 8) {
            for (int i = 3; i<7; i++){
                moves.add(new Move(startSquare, targetSquare, Piece.black+i));
            }
        } else {
            moves.add(new Move(startSquare, targetSquare, board.squares[startSquare]));
        }
    }
    

    private void generatePawnMoves(int startSquare) {
        //White pawns
        if(board.colourToMove == true){

            //Move one forward
            int targetSquare = startSquare + 8;
            int pieceOnTargetSquare = board.squares[targetSquare];
            //Checks that no piece blocks
            if (Piece.isType(pieceOnTargetSquare, Piece.none)){

                addWhitePawnMove(startSquare, targetSquare);
                
            }

            //Logic for moving 2 forward
            if(startSquare> 7 && startSquare<16){
                targetSquare = startSquare + 16;
                pieceOnTargetSquare = board.squares[targetSquare];
                int pieceInbetween = board.squares[targetSquare-8];
                if (Piece.isType(pieceOnTargetSquare, Piece.none) && Piece.isType(pieceInbetween, Piece.none)){
                    moves.add(new Move(startSquare, targetSquare, board.squares[startSquare]));
                    
                }

            }

            //Left diagonal
            if(numSquaresToEdge[startSquare][4] != 0){
                targetSquare = startSquare + 7;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    addWhitePawnMove(startSquare, targetSquare);
                    
                }
            }

            //Right Diagonal

            if(numSquaresToEdge[startSquare][6] != 0){
                targetSquare = startSquare + 9;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    addWhitePawnMove(startSquare, targetSquare);
                    
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
                addBlackPawnMove(startSquare, targetSquare);
            }
            //Logic for move 2 forwards 
            if(startSquare> 47 && startSquare<56){
                targetSquare = startSquare - 16;
                pieceOnTargetSquare = board.squares[targetSquare];
                int pieceInbetween = board.squares[targetSquare+8];
                if (Piece.isType(pieceOnTargetSquare, Piece.none) && Piece.isType(pieceInbetween, Piece.none)){
                    moves.add(new Move(startSquare, targetSquare, board.squares[startSquare]));
                    
                }

            }

            if(numSquaresToEdge[startSquare][5] != 0){
                targetSquare = startSquare - 7;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    addBlackPawnMove(startSquare, targetSquare);
                    
                }
            }

            //Right Diagonal

            if(numSquaresToEdge[startSquare][7] != 0){
                targetSquare = startSquare - 9;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    addBlackPawnMove(startSquare, targetSquare);
                    
                }
            }

        }

    }

    public void doMove(Move move){
        
        //Change value of target square and reset starting square
        board.squares[move.getTargetSquare()] = move.getNewPiece();
        board.squares[move.getStartSquare()] = 0;
        board.switchTurn();

    }
    
}
