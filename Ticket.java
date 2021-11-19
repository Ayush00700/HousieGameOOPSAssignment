import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ticket {
    private int card[][];
    private final String playerName;
    Ticket(String playerName){
        card = new int[3][9];
        this.playerName = playerName;
        generateTicket();
        System.out.printf("Ticket for %s : \n",playerName);
        printCard();
    }
    public int[][] getCard(){
        return card;
    }
    public String getPlayerName(){
        return playerName;
    }
    public int[][] generateTicket(){
        List<Integer> arr = new ArrayList<>();
        int[][] cardPositions = new int[3][9];
        for(int row=0;row<3;row++){
            for(int i=0;i<9;i++){
                arr.add(i);
            }
            for(int j=0;j<5;j++){
                int colPosition=arr.remove((int)((9-j)*Math.random()));
                int randomNumber;
                do{
                    randomNumber = (int)(10*Math.random())+1;
                }while(cardPositions[0][colPosition]==colPosition*10+randomNumber||cardPositions[1][colPosition]==colPosition*10+randomNumber||cardPositions[2][colPosition]==colPosition*10+randomNumber);

                cardPositions[row][colPosition] = colPosition*10+randomNumber;
            }
            arr.clear();
        }
        for(int col=0;col<9;col++){
            if(cardPositions[0][col]!=0&&cardPositions[1][col]!=0&&cardPositions[2][col]!=0){
                int n1=cardPositions[0][col];
                int n2=cardPositions[1][col];
                int n3=cardPositions[2][col];
                int[] t = {n1,n2,n3};
                Arrays.sort(t);
                for(int q=0;q<3;q++)
                cardPositions[q][col] = t[q];
            }
            else if(cardPositions[0][col]!=0&&cardPositions[1][col]!=0){
                int temp =cardPositions[0][col];
                cardPositions[0][col] = Math.min(cardPositions[0][col],cardPositions[1][col]);
                cardPositions[1][col] = Math.max(temp,cardPositions[1][col]);
            }
            else if(cardPositions[0][col]!=0&&cardPositions[2][col]!=0){
                int temp =cardPositions[0][col];
                cardPositions[0][col] = Math.min(cardPositions[0][col],cardPositions[2][col]);
                cardPositions[2][col] = Math.max(temp,cardPositions[2][col]);
            }
            else if(cardPositions[1][col]!=0&&cardPositions[2][col]!=0){
                int temp =cardPositions[1][col];
                cardPositions[1][col] = Math.min(cardPositions[1][col],cardPositions[2][col]);
                cardPositions[2][col] = Math.max(temp,cardPositions[2][col]);
            }
        }

        card = cardPositions;
        return card;
    }
    private void printCard(){
    
        for(int row=0;row<3;row++){
            for(int col=0;col<9;col++){
                if(col==0&&card[row][col]<10&&card[row][col]>0)
                    System.out.print(" ");    
                System.out.print(card[row][col]!=0?Integer.toString(card[row][col])+" ":"   ");
            }
            System.out.println();
        }
    }
    public void crossOut(int calledNumber){
        for(int row=0;row<3;row++){
            for(int col=0;col<9;col++){
                if(this.card[row][col] == calledNumber){
                    System.out.printf("Number Found in %s 's Ticket\n",this.playerName);
                    this.card[row][col] = -1;
                }
            }
        }
    }
    //  public static void main(String args[]) {
    //     Ticket t = new Ticket("Ayush");
    //     t.generateTicket();
    //     t.printCard();
    // }
}
