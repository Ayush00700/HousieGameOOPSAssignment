import java.util.*;
public class Moderator extends Board implements Runnable{
    private int noOfPlayers;
    private List<WinningConditions> rules;
    private List<Player> allPlayers; 
    private Board board;
    private int numberAnnounced = 0;
    private Thread[] playerThreads;
    
    Moderator(int noOfPlayers, Board board){
        super(noOfPlayers);
        this.noOfPlayers = noOfPlayers;
        rules = new ArrayList<>();
        allPlayers = new ArrayList<>();
        rules.add(new Early5());
        rules.add(new TopRow());
        rules.add(new MiddleRow());
        rules.add(new BottomRow());
        rules.add(new FullHouse());
        this.board = board;
    }
    public void generateAllTicketsAndPlayers(int noOfPlayers,Scanner sc){
        String[] names = new String[noOfPlayers];
        Thread[] playerThreads = new Thread[noOfPlayers];
        while(noOfPlayers>0){
            System.out.printf("Enter Player %d 's Name: ",(this.noOfPlayers-noOfPlayers+1));
            names[this.noOfPlayers-noOfPlayers] = sc.next();
            System.out.println();
            noOfPlayers--;
        }
        for(int i=0;i<names.length;i++){
            Player pi = new Player(rules,names[i],board);

            playerThreads[i] = new Thread(pi);
            playerThreads[i].start();

            System.out.println();
            this.allPlayers.add(pi);
        }
    }
    public void setRules(Scanner sc){
        int noOfRules = 3 + (int)(3*Math.random());
        for(int i=5;i>noOfRules;i--){
            int removedRule = (int)(i*Math.random());
            rules.remove(removedRule);
        }
        System.out.println("Winning conditions selected by the moderator: ");
        for(int i=0;i<rules.size();i++){
            System.out.println(rules.get(i));
        }
        System.out.println();
    }

    public boolean anyPrizesLeft(){
        for(int i=0;i<rules.size();i++){
            if(rules.get(i).getIsAvailable()){
                return true;       
            }
        }
        return false;
    }

    public void declareWinners(){
        System.out.println("\n\nWinners of this Game");
        for(int i=0;i<rules.size();i++){
            System.out.print(rules.get(i).toString()+" : " );
            for(int j=0;j<rules.get(i).getWonByPlayer().size();j++){
                if(j>0)
                    System.out.print(",");
                System.out.print(rules.get(i).getWonByPlayer().get(j).getName());
            }
            System.out.println();
        }
    }
	public void setAnnouncedNumber(int i) {
		this.numberAnnounced = i;	
	}

    public void run(){
        synchronized(board.lock1){    
            while(!board.isBoardFull()&&this.anyPrizesLeft()){
                
                int x = board.generateRandomNumber();
                System.out.println("Moderator called "+x);
                for(int j=0;j<noOfPlayers;j++){
                    this.allPlayers.get(j).containsNumber(x);
                }
            }
            System.out.println("All prizes are now finished");
            this.declareWinners();
            System.out.println("Game Over");
        }
    }

    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number of Players:");
        int noOfPlayers = sc.nextInt();
        Board board = new Board(noOfPlayers);
        Moderator admin = new Moderator(noOfPlayers, board);
        admin.setRules(sc);
        admin.generateAllTicketsAndPlayers(noOfPlayers,sc);
        System.out.println("Game Starting ");
        System.out.println("Press 0 to Start ");
        sc.next();
        
		Thread moderatorThread  = Thread.currentThread();  

        sc.close();
    }
    
}
