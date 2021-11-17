import java.util.*;
public class Player implements Runnable{
    private int id;
    private String playerName;
    private Ticket card;
    private boolean hasAlreadyWon;
    private final List<WinningConditions> rules;
    private WinningConditions prizeWon;
    private Board board;

    Player(List<WinningConditions> rules,int id, String playerName, Board b){
        this.playerName=playerName;
        card = new Ticket(playerName);
        hasAlreadyWon =false;
        this.rules = rules; 
        this.board = b;
        this.id =id;
    }
    public boolean containsNumber(int calledNumber){
        for(int row=0;row<3;row++){
            for(int col=0;col<9;col++){
                if(card.getCard()[row][col]==calledNumber){
                    card.crossOut(calledNumber);
                    if(!hasAlreadyWon && checkIfWon()){
                        hasAlreadyWon = true;
                        board.playerSuccessFlag[id] = true;
                        System.out.printf("Player %s has completed %s \n",this.playerName,this.prizeWon.getClass().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }                    }
                    return true; 
                }
            }
        }
        return false;
    } 
    
    public void run(){
        synchronized(board.lock1) {
            while(!board.gameCompleteFlag) {
                while(!board.noAnnouncedFlag || board.playerChanceFlag[id]) {
					try {
						board.lock1.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
                if(!board.gameCompleteFlag) {
                    this.containsNumber(board.announcedNumber);
                    
                    board.playerChanceFlag[id] = true;

                    board.lock1.notifyAll();
   
                }
            }
        }	

    }

    public String getName(){
        return playerName;
    }
    public void setName(String name){
        this.playerName = name; 
    }
    private boolean checkIfWon(){
        for(int i=0;i<rules.size();i++){
            if(rules.get(i).winCondition(card.getCard(), this)){
                this.prizeWon = rules.get(i); 
                return true;
            }
        }
         return false;
    }
}
