//Tests for Board Representation
public class TestBoardRepresentation {

    public static void testFenStringLoading(){
        
        for(int piece : BoardRepresentation.squares){
            System.out.println(piece);
        }

    }
    public static void main(String[] args){
        //Test if Board properly reads fen Strings
        testFenStringLoading();
    }
}
