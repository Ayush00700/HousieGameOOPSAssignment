import java.util.ArrayList;
import java.util.List;

public class Board {
    private boolean[] board;
    private List<Integer> availableNumbers;
    public int announcedNumber = 0;	 
	public boolean gameCompleteFlag = false;	
	public boolean noAnnouncedFlag = false;
	public boolean[] playerSuccessFlag;
	public boolean[] playerChanceFlag;
	public Object lock1 = new Object();
	public Object lock2 = new Object();

    Board(int noOfPlayers){
        playerSuccessFlag = new boolean[noOfPlayers];
        playerChanceFlag = new boolean[noOfPlayers];
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
    
    public int generateRandomNumber(int a){
        int x = availableNumbers.remove(availableNumbers.indexOf(a));
        board[x] =true;
        return x;   //DEBUG
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

