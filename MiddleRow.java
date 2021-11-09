
import java.util.*;


public class MiddleRow implements WinningConditions {
    private boolean isAvailable;
    private List<Player> wonByPlayers;
    MiddleRow(){
        this.isAvailable =true;
        wonByPlayers = new ArrayList<>();
    }
    public void setWonByPlayer(Player p){
        this.wonByPlayers.add(p);
    }
    public List<Player> getWonByPlayer(){
        return wonByPlayers;
    }
    public void winCondition(int[][] card,Player p){
        boolean flag=true;
        if(isAvailable){
                for(int col=0;col<9;col++){
                    if(card[1][col]!=-1&&card[1][col]!=0){
                        flag=false;
                        break;
                    }
                }
            if(flag){
                setWonByPlayer(p);
                this.isAvailable =false;
            }
        }
    }
    public boolean getIsAvailable(){
        return isAvailable;
    }
    public void setIsAvailable(boolean value){
        this.isAvailable = value;
    }
    @Override
    public String toString(){
        return "Middle Row: Complete all numbers in the second line";
    }
}
