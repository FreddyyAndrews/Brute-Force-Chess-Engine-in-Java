import java.util.ArrayList;

public class MoveGeneration  {

    //Offsets for piece movement
    private static final int[] directionOffSets = {8,-8,-1,1,7,-7,9,-9};
    
    //Instance Variables
    PrecomputedMoveData dataFinder;
    BoardRepresentation board;
    
    private int[][] numSquaresToEdge; 
    //Moves
    private ArrayList<Move> moves; 
    private ArrayList<EnPassant> enPassants;
    private ArrayList<Castle> castles;
    //Legal Moves
    private ArrayList<Move> legalMoves; 
    private ArrayList<EnPassant> legalEnPassants;
    private ArrayList<Castle> legalCastles;
    //Save last piece captured
    private int lastPieceCaptured;

    //Constructor
    public MoveGeneration( PrecomputedMoveData dataFinder, BoardRepresentation board){

        this.dataFinder = dataFinder;
        this.board = board;
        numSquaresToEdge = dataFinder.numSquaresToEdge;

    }

    public ArrayList<EnPassant> getEnPassants(){
        return enPassants;
    }

    public ArrayList<Castle> getCastles(){
        return castles;
    }

    public ArrayList<EnPassant> getLegalEnPassants(){
        return legalEnPassants;
    }

    public ArrayList<Castle> getLegalCastles(){
        return legalCastles;
    }

    public ArrayList<Move> getLegalMoves(){
        return legalMoves;
    }

    //Generate legal moves and filter out moves that put king in check
    public void generateLegalMoves (){
        
        //Generate all moves, moves are stored in instance ArrayLists
        ArrayList<Move> moveTester = generateMoves(); //w
        ArrayList<EnPassant> enPassantTester = enPassants;
        ArrayList<Castle> castleTester = castles;

        //Regular moves
        legalMoves = new ArrayList<Move>();
        boolean moveDone = false;
        //For ever move generated
        for(Move move : moveTester){

            //Start by assuming the move is legal
            boolean moveLegal = true;
            
            //Do the move do get the next position
            doMove(move); //b
            moveDone = true;
            //For each returning opponent move
            for(Move opMove : generateMoves()){//b

                //if the move would capture the king then we know that the previous move was illegal
                if(Piece.isType(board.squares[opMove.getTargetSquare()], Piece.king)){

                    //Set move legality to false
                    moveLegal = false;
                    break;

                }
            }

            //Add legal moves to legal moves
            if(moveLegal){

                legalMoves.add(move);

            }

            //Reset board
            if(moveDone){
                undoMove(move);
            }
            
        }
 
        //EnPassants

        legalEnPassants = new ArrayList<EnPassant>();
        moveDone = false;
        for(EnPassant enPassant : enPassantTester){

            //Start by assuming the enpassant is legal
            boolean moveLegal = true;

            //Do the move do get the next position
            doEnPassant(enPassant);
            moveDone = true;
            //For each returning opponent move
            for(Move opMove : generateMoves()){

                //if the move would capture the king then we know that the previous move was illegal
                if(Piece.isType(board.squares[opMove.getTargetSquare()], Piece.king)){

                    //Set move legality to false
                    moveLegal = false;

                }
            }

            //Add legal enpassant to legal enpassants
            if(moveLegal){

                legalEnPassants.add(enPassant);

            }

            //Reset board

            if(moveDone){
                undoMove(enPassant);
            }
        }

        //Castles

        legalCastles = new ArrayList<Castle>();
        moveDone = false;
        for(Castle castle: castleTester){

            //Start by assuming the castle is legal
            boolean moveLegal = true;
            //Switch turn to generate opponent moves
            board.switchTurn();
            
            //For each returning opponent move
            for(Move opMove : generateMoves()){

                //if the move would bring opponent piece to square the king is castling through the castle is illegal
                //Doesn't recognize issue here
                if(board.squares[opMove.getTargetSquare()] >= castle.getKingStartSquare() && board.squares[opMove.getTargetSquare()] <= castle.getKingEndSquare()){

                    //Set move legality to false
                    moveLegal = false;
                    
                }
                //if the move would bring opponent piece to square the king is castling through the castle is illegal
                //Doesn't recognize issue here
                if(board.squares[opMove.getTargetSquare()] <= castle.getKingStartSquare() && board.squares[opMove.getTargetSquare()] >= castle.getKingEndSquare()){

                    //Set move legality to false
                    moveLegal = false;

                }
                
            }

            //Add legal castle to legal enpassants
            if(moveLegal){
                legalCastles.add(castle);
            }
            
            board.switchTurn();
            
        }
 
    }

    public ArrayList<Move> generateMoves () {
        moves = new ArrayList<Move>();
        enPassants =  new ArrayList<EnPassant>();
        castles = new ArrayList<Castle>();
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

        //Generate Castles

        if(board.wkCastle && board.colourToMove){
            Castle castle = new Castle(4,6,7,5);
            if(MoveGeneration.castleLegalityCheck(board, castle)){
                castles.add(castle);
            }
        }
        if(board.wqCastle && board.colourToMove){

            Castle castle = new Castle(4,2,0,3);
            if(MoveGeneration.castleLegalityCheck(board, castle)){
                castles.add(castle);
            }

        }
        if(board.bkCastle && !board.colourToMove){

            Castle castle = new Castle(60,62,63,61);
            if(MoveGeneration.castleLegalityCheck(board, castle)){
                castles.add(castle);
            }

        }
        if(board.bqCastle && !board.colourToMove){

            Castle castle = new Castle(60,58,56,59);
            if(MoveGeneration.castleLegalityCheck(board, castle)){
                castles.add(castle);
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
                //En Passant
                if(targetSquare == board.enPassantSquare){
                    enPassants.add(new EnPassant(startSquare, targetSquare, targetSquare-8));
                }
            }

            //Right Diagonal

            if(numSquaresToEdge[startSquare][6] != 0){
                targetSquare = startSquare + 9;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    addWhitePawnMove(startSquare, targetSquare);
                    
                }
                //En Passant
                if(targetSquare == board.enPassantSquare){
                    enPassants.add(new EnPassant(startSquare, targetSquare, targetSquare-8));
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
            //Diagonal
            if(numSquaresToEdge[startSquare][5] != 0){
                targetSquare = startSquare - 7;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    addBlackPawnMove(startSquare, targetSquare);
                    
                }
                //En Passant
                if(targetSquare == board.enPassantSquare){
                    enPassants.add(new EnPassant(startSquare, targetSquare, targetSquare+8));
                }
            }
            //Diagonal
            if(numSquaresToEdge[startSquare][7] != 0){
                targetSquare = startSquare - 9;
                pieceOnTargetSquare = board.squares[targetSquare];

                if((!Piece.isColourToMove(pieceOnTargetSquare, board.colourToMove)) && !Piece.isType(pieceOnTargetSquare, Piece.none)){
                    
                    addBlackPawnMove(startSquare, targetSquare);
                    
                }
                //En Passant
                if(targetSquare == board.enPassantSquare){
                    enPassants.add(new EnPassant(startSquare, targetSquare, targetSquare+8));
                }
            }

        }

    }

    public void playMove(Move move){

        //Fullmove clock
        if(board.colourToMove == false){ //Black's turn
            board.fullMoveClock = board.fullMoveClock + 1;
        }
        //Halfmove clock
        if(!Piece.isType(board.squares[move.getStartSquare()], Piece.pawn) || !Piece.isType(board.squares[move.getTargetSquare()], Piece.none)){
            board.halfMoveClock = board.halfMoveClock +1;
        }else{
            board.halfMoveClock = 0;
        }
        
        doMove(move);
        board.proccessCastlingRequirements();
    }

    public void playEnPassant(EnPassant enPassant){

        //Fullmove clock
        if(board.colourToMove == false){ //Black's turn
            board.fullMoveClock = board.fullMoveClock + 1;
        }
        //Halfmove clock
        board.halfMoveClock = 0;
        
        doEnPassant(enPassant);
        board.proccessCastlingRequirements();

    }

    public void playCastle(Castle castle){
        //Fullmove clock
        if(board.colourToMove == false){ //Black's turn
            board.fullMoveClock = board.fullMoveClock + 1;
        }
        //Halfmove clock
        board.halfMoveClock = board.halfMoveClock+1;
        
        doCastle(castle);
        board.proccessCastlingRequirements();
    }

    private void doMove(Move move) {

    
        //Change value of target square and reset starting square
        lastPieceCaptured = board.squares[move.getTargetSquare()];
        board.squares[move.getTargetSquare()] = move.getNewPiece();
        board.squares[move.getStartSquare()] = 0;

        
        
        board.switchTurn();

    }

    private void doEnPassant(EnPassant enPassant) {

        
        //Change value of target square and reset starting square
        board.squares[enPassant.getTargetSquare()] = board.squares[enPassant.getStartSquare()];
        board.squares[enPassant.getStartSquare()] = 0;
        board.squares[enPassant.getTargetPieceSquare()] = 0;

        
        board.switchTurn();

    }

    private void doCastle(Castle castle) {

        
        //Change value of target square and reset starting square
        board.squares[castle.getRookEndSquare()] = board.squares[castle.getRookStartSquare()];
        board.squares[castle.getKingEndSquare()] = board.squares[castle.getKingStartSquare()];

        board.squares[castle.getKingStartSquare()] = 0;
        board.squares[castle.getRookStartSquare()] = 0;

        
        
        board.switchTurn();
    }

    private static boolean castleLegalityCheck(BoardRepresentation board, Castle castle){ //Checks if castle goes through pieces or checks
        
        //Checking if there are pieces between king and rook

        if(castle.getKingStartSquare()> castle.getRookStartSquare()){
            for(int i = 1; i< castle.getKingStartSquare(); i++){
                if(!Piece.isType(board.squares[castle.getRookStartSquare()+i], Piece.none)){
                    return false;
                }
            }
        }

        if(castle.getKingStartSquare() < castle.getRookStartSquare()){
            for(int i = castle.getKingStartSquare() + 1; i< castle.getRookStartSquare(); i++){
                if(!Piece.isType(board.squares[i], Piece.none)){
                    return false;
                }
            }
        }

        return true;

    }

    private void undoMove(Movement play) {

        if(play instanceof Move){

            Move move = (Move) play;
            
            if(!move.promotion){

                board.squares[move.getStartSquare()] = board.squares[move.getTargetSquare()];
                board.squares[move.getTargetSquare()] = lastPieceCaptured;

            }
            if(move.promotion){
                if(Piece.isWhite(board.squares[move.getTargetSquare()])){
                    board.squares[move.getStartSquare()] = Piece.pawn +Piece.white;
                    board.squares[move.getTargetSquare()] = 0;
                }else{
                    board.squares[move.getStartSquare()] = Piece.pawn +Piece.black;
                    board.squares[move.getTargetSquare()] = 0;
                }
            }
            
            board.switchTurn(); 

        }else if(play instanceof EnPassant){

            EnPassant enPassant = (EnPassant) play;

            if(Piece.isWhite(board.squares[enPassant.getTargetSquare()])){
                board.squares[enPassant.getTargetSquare()] = 0;
                board.squares[enPassant.getStartSquare()] = Piece.pawn + Piece.white;
                board.squares[enPassant.getTargetPieceSquare()] = Piece.pawn + Piece.black;
            }else{
                board.squares[enPassant.getTargetSquare()] = 0;
                board.squares[enPassant.getStartSquare()] = Piece.pawn + Piece.black;
                board.squares[enPassant.getTargetPieceSquare()] = Piece.pawn + Piece.white;
            }
            

        }else if(play instanceof Castle){

        }

    }

}
