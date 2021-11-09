
import java.util.*;


public class FullHouse implements WinningConditions {
    private boolean isAvailable;
    private List<Player> wonByPlayers;
    FullHouse(){
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
            for(int row=0;row<2;row++){
                for(int col=0;col<9;col++){
                    if(card[row][col]!=-1&&card[row][col]!=0){
                        flag=false;
                        break;
                    }
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
        return "Full House: First one to cross all numbers on the ticket";
    }
}
