import java.util.HashMap;
import java.util.Map;

public class BoardRepresentation {
    //Array to store Board representation
    public int[] squares = new int[64];
    public boolean colourToMove;
    public boolean wqCastle;
    public boolean wkCastle;
    public boolean bkCastle;
    public boolean bqCastle;
    public int enPassantSquare;
    public int halfMoveClock;
    public int fullMoveClock;

    //Board Constructor with Fen given
    BoardRepresentation(String fen){
        loadPositionFromFenString(fen);
        
    }

    //Default Board constructor
    BoardRepresentation(){
        loadPositionFromFenString("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        
    }

    public void loadPositionFromFenString(String fen) {
        //Map Char representation of pieces to numerical representation
        Map<Character, Integer> pieceMap = new HashMap<Character, Integer>();
        
        pieceMap.put('k', Piece.king);
        pieceMap.put('n', Piece.knight);
        pieceMap.put('q', Piece.queen);
        pieceMap.put('p', Piece.pawn);
        pieceMap.put('b', Piece.bishop);
        pieceMap.put('r', Piece.rook);

        //Set File and Rank Before Iteration
        int file = 0;
        int rank = 7;

        //Break fen up into parts

        String[] fenSplited = fen.split("\\s+");


        //Begins Iterating through chars in fen string
        for(int i=0; i< fenSplited[0].length(); i++) {
            

            if(fenSplited[0].charAt(i) == '/'){
                file = 0;
                rank--;
            } else {
                if(Character.isDigit(fenSplited[0].charAt(i))){
                    int jump = Integer.parseInt(String.valueOf(fenSplited[0].charAt(i)));
                    file = file + jump;
                } else {
                    //Determines piece type and colour from character
                    int pieceColour = (Character.isUpperCase(fenSplited[0].charAt(i))) ? Piece.white : Piece.black ;
                    int pieceType = pieceMap.get(Character.toLowerCase(fenSplited[0].charAt(i)));
                    //Adds piece to the board representation
                    squares[rank*8+file] = pieceType + pieceColour;
                    file++;
                }
            }
            
        }

        //Determine turn from fen
        if(fenSplited[1].charAt(0) == 'w'){
            colourToMove =true;
        }else{
            colourToMove = false;
        }

        //Default Castling availability to false
        wqCastle=false;
        wkCastle=false;
        bkCastle=false;
        bqCastle=false;

        //Determine castling availability

        for(int i=0; i < fenSplited[2].length();i++){

            //Set white can castle king side
            if(fenSplited[2].charAt(i)== 'K'){
                wkCastle = true;
            }
            //Set Black and castle king side
            if(fenSplited[2].charAt(i)== 'k'){
                bkCastle = true;
            }
            //Set white can castle queen side
            if(fenSplited[2].charAt(i)== 'Q'){
                wqCastle = true;
            }
            //Set black can castle queen side
            if(fenSplited[2].charAt(i)== 'q'){
                bqCastle = true;
            } 

        }
        //Verify that pieces are in right place for castling
        proccessCastlingRequirements();

        //En passant Square Info
        if(!fenSplited[3].equals("-")){
           
            //Map letter to numerical value
            Map<Character, Integer> fileMap = new HashMap<Character, Integer>();
        
            fileMap.put('a', 0);
            fileMap.put('b', 1);
            fileMap.put('c', 2);
            fileMap.put('d', 3);
            fileMap.put('e', 4);
            fileMap.put('f', 5);
            fileMap.put('g', 6);
            fileMap.put('h', 7);
            //Calculate En passant Square
            enPassantSquare = fileMap.get(fenSplited[3].charAt(0)) + (Integer.parseInt(String.valueOf(fenSplited[3].charAt(1)))-1)*8;
        }

        //Set Half move and full move clock
        halfMoveClock = Integer.parseInt(String.valueOf(fenSplited[4]));
        fullMoveClock = Integer.parseInt(String.valueOf(fenSplited[5]));
    }

    public void switchTurn(){
        colourToMove = !colourToMove;
    }

    public String getFenString() {

        Map<Integer, Character> pieceMap = new HashMap<Integer, Character>();
        
        pieceMap.put(Piece.king, 'k');
        pieceMap.put(Piece.knight, 'n');
        pieceMap.put(Piece.queen, 'q');
        pieceMap.put(Piece.pawn, 'p');
        pieceMap.put(Piece.bishop, 'b');
        pieceMap.put(Piece.rook, 'r');

        
        String returnVal ="";
        int counter = 0;
        int rank = 7;
        for (int i = 0; i<64; i++) {

            
            int file = i%8;
            
            if(squares[rank*8+file] == 0) {
                counter++;
            }else{
                if(counter != 0){
                    returnVal = returnVal+counter;
                    counter = 0;
                }
                int piece = squares[rank*8+file];
                if (Piece.isWhite(piece)){
                    piece = piece -8;
                    returnVal = returnVal + Character.toUpperCase(pieceMap.get(piece));
                } else {
                    piece = piece -16;
                    returnVal = returnVal + Character.toLowerCase(pieceMap.get(piece));
                }
            }
            if(file ==7){
                rank--;
                if(counter != 0){
                    returnVal = returnVal+counter;
                    counter = 0;
                }
                if(i != 63) {
                    returnVal = returnVal + "/";
                }
                
            }
            
        }
        
        if(colourToMove){ //white's turn
            returnVal = returnVal + " " + "w";
        }else{ //Black's turn
            returnVal = returnVal + " " + "b";
        }
        //Check if dash needed
        boolean castleDashFlag = true;
        //Add Castling Info
        if(wkCastle){
            castleDashFlag = false;
            returnVal = returnVal + " " + "K";
        }
        if(wqCastle){
            castleDashFlag = false;
            if(castleDashFlag){
                returnVal = returnVal + " " + "Q";
            }else{
                returnVal = returnVal +  "Q";
            }
            
        }
        if(bkCastle){
            castleDashFlag = false;
            if(castleDashFlag){
                returnVal = returnVal + " " + "k";
            }else{
                returnVal = returnVal +  "k";
            }
        }
        if(bqCastle){
            castleDashFlag = false;
            if(castleDashFlag){
                returnVal = returnVal + " " + "q";
            }else{
                returnVal = returnVal +  "q";
            }
        }
        if(castleDashFlag){
            returnVal = returnVal + " -";
        }

        //Add en Passant Info

        if(enPassantSquare != 0){
            //Convert int to letter-number representation

            Map<Integer, Character> fileMap = new HashMap<Integer, Character>();
        
            fileMap.put(0, 'a');
            fileMap.put(1, 'b');
            fileMap.put(2, 'c');
            fileMap.put(3, 'd');
            fileMap.put(4, 'e');
            fileMap.put(5, 'f');
            fileMap.put(6, 'g');
            fileMap.put(7, 'h');

            int enPassantFile = enPassantSquare % 8;
            char enPassantFileLetter = fileMap.get(enPassantFile);
            int enPassantRank = (enPassantSquare - enPassantFile)/8;

            returnVal = returnVal + (" " + enPassantFileLetter) + enPassantRank;

        }else{
            returnVal = returnVal + " -";
        }

        returnVal = returnVal + " " + halfMoveClock;
        returnVal = returnVal + " " + fullMoveClock;

        return returnVal;
    }

    public void proccessCastlingRequirements(){

        if(squares[0] != 13){ //Queen side white rook
            wqCastle = false;
        }
        if(squares[0] != 13){ //King side white rook
            wkCastle = false;
        }
        if(squares[4] != 9){ //White king
            wqCastle = false;
            wkCastle = false;
        }

        if(squares[56] != 21){ //Queen side black rook
            bqCastle = false;
        }
        if(squares[63] != 21){ //King side black rook
            bkCastle = false;
        }
        if(squares[60] != 17){ //black king
            bqCastle = false;
            bkCastle = false;
        }



    }


} 