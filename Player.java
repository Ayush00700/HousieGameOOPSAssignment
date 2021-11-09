import java.util.*;
public class Player {
    private String playerName;
    private Ticket card;
    private boolean hasAlreadyWon;
    private final List<WinningConditions> rules;

    Player(List<WinningConditions> rules,String playerName){
        this.playerName=playerName;
        card = new Ticket(playerName);
        hasAlreadyWon =false;
        this.rules = rules; 
    }
    public boolean containsNumber(int calledNumber){
        for(int row=0;row<3;row++){
            for(int col=0;col<9;col++){
                if(card.getCard()[row][col]==calledNumber){
                    card.crossOut(calledNumber);
                    if(!hasAlreadyWon && checkIfWon()){
                        hasAlreadyWon = true;
                        //TODO Notify Admin and Print
                    }
                    return true; 
                }
            }
        }
        return false;
    } 
    public String getName(){
        return playerName;
    }
    public void setName(String name){
        this.playerName = name; 
    }
    private boolean checkIfWon(){
         //TODO Call Win Classes to Check
         return false;
    }
}
