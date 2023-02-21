public class PrecomputedMoveData {

    //Store the distance from edge
    public  int[][] numSquaresToEdge = new int[64][8];
    
    public PrecomputedMoveData () {
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
}
