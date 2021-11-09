import java.util.ArrayList;
import java.util.List;

public class Board {
    private boolean[] board;
    private List<Integer> availableNumbers;
    Board(){
        board = new boolean[91];
        availableNumbers = new ArrayList<>();
        board[0] = true;
        for(int i=1;i<91;i++){
            board[i] = false;
            availableNumbers.add(i);
        }
    }
    public boolean[] resetBoard(){
        for(int i=1;i<91;i++)
            board[i] = false;
        return board;
    }
    public int generateRandomNumber(){
        int index = (int)(availableNumbers.size()*Math.random());
        int x = availableNumbers.remove(index);
        board[x] =true;
        return x;
    }
    public boolean isBoardFull(){
        return availableNumbers.size()==0;
    }
    
    // public static void main(String args[]) {
    //     int i=1;
    //     Board b = new Board();
    //     while(i++<=90){
    //         System.out.println(
    //             b.generateRandomNumber());
    //     }
    // }
}
