
import java.util.*;

public class Early5 implements WinningConditions {
    private boolean isAvailable;
    private List<Player> wonByPlayers;
    Early5(){
        this.isAvailable =true;
        wonByPlayers = new ArrayList<>();
    }
    public void setWonByPlayer(Player p){
        this.wonByPlayers.add(p);
    }
    public List<Player> getWonByPlayer(){
        return wonByPlayers;
    }
    public boolean winCondition(int[][] card,Player p){
        if(isAvailable){
            int count=0;
            for(int row=0;row<3;row++){
                for(int col=0;col<9;col++){
                    if(card[row][col]==-1)
                        count++;
                }
            }
            if(count==5){
                setWonByPlayer(p);
                this.isAvailable =false;
                return true;
            }
        }
        return false;   
    }
    public boolean getIsAvailable(){
        return isAvailable;
    }
    public void setIsAvailable(boolean value){
        this.isAvailable = value;
    }
    @Override
    public String toString(){
        return "Early 5: First one to cross 5 numbers on the ticket";
    }
}
