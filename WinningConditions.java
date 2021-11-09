
import java.util.*;

public interface WinningConditions {
    public void setWonByPlayer(Player p);
    public List<Player> getWonByPlayer();
    public boolean winCondition(int[][] card,Player p);
    public boolean getIsAvailable();
    public void setIsAvailable(boolean value);
    public String toString();
}
