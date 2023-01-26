//Tests for Board Representation
public class TestBoardRepresentation {

    public static void testFenStringLoading(){
        BoardRepresentation board = new BoardRepresentation();
        for(int piece : board.squares){
            System.out.println(piece);
        }

    }
    public static void main(String[] args){
        //Test if Board properly reads fen Strings
        testFenStringLoading();
    }
}
