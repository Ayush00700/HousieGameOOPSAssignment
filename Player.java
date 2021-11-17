import java.util.*;
public class Player implements Runnable{
    private String playerName;
    private Ticket card;
    private boolean hasAlreadyWon;
    private final List<WinningConditions> rules;
    private WinningConditions prizeWon;
    private Board board;

    Player(List<WinningConditions> rules,String playerName, Board b){
        this.playerName=playerName;
        card = new Ticket(playerName);
        hasAlreadyWon =false;
        this.rules = rules; 
        this.board = b;
    }
    public boolean containsNumber(int calledNumber){
        for(int row=0;row<3;row++){
            for(int col=0;col<9;col++){
                if(card.getCard()[row][col]==calledNumber){
                    card.crossOut(calledNumber);
                    if(!hasAlreadyWon && checkIfWon()){
                        hasAlreadyWon = true;
                        System.out.printf("Player %s has completed %s \n",this.playerName,this.prizeWon.getClass().getName());
                    }
                    return true; 
                }
            }
        }
        return false;
    } 
    
    public void run(){

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
