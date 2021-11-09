import java.util.Arrays;

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
        int arr[] = new int[9];
        int sum = 0;
        while(sum!=6){
            sum=0;
            for(int i=0;i<9;i++){
                arr[i] = (int)(3*Math.random());
                sum+=arr[i];
            }
        }
        for(int col=0;col<9;col++){
            
            int no1; int no2; int no3;
            do{
                no1= (int)(11*Math.random());
            }while(no1==0);
            do{
                no2= (int)(11*Math.random());
            }while(no2==0||no2==no1);
        
            do{
                no3= (int)(11*Math.random());
            }while(no3==0||no3==no1||no3==no2);
            
            int[] arr2= {no1,no2,no3}; 
            Arrays.sort(arr2);
            // System.out.println(Arrays.toString(arr2));

                card[0][col] = arr2[0]+(10*col);
                card[1][col] = arr[col]<=1? 0:arr2[1]+(10*col);
                card[2][col] = arr[col]==0? 0:arr2[2]+(10*col);
        }
        
        // for(int i:arr){
        //     System.out.print(i);
        // }
        // System.out.println();
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
