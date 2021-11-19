import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;



import java.util.*;
public class Moderator extends Board implements Runnable{
    private int noOfPlayers;
    private List<WinningConditions> rules;
    private static List<Player> allPlayers; 
    private Board board;
    private int numberAnnounced = 91;
    private Thread[] playerThreads;
    public final JLabel lblGameStatus = new JLabel();   
    private JButton[] btnDealerBoardNumbers;	
    private static GameGUI gui;
    private boolean userSelectedNumber;


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
        lblGameStatus.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        userSelectedNumber = false;	
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
            Player pi = new Player(rules,i,names[i],board,this);

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
        if(noOfPlayers>=rules.size())
        for(int i=0;i<rules.size();i++){
            if(rules.get(i).getIsAvailable()){
                return true;       
            }
        }
        else
        for(int i=0;i<board.playerSuccessFlag.length;i++){
            if(!board.playerSuccessFlag[i])
                return true;
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
        this.userSelectedNumber = true;
	}

    public void run(){
        synchronized(board.lock1){    
            while(!board.isBoardFull()&&this.anyPrizesLeft()){
                
                board.noAnnouncedFlag = false;

                for(int j=0;j<noOfPlayers;j++){
                    board.playerChanceFlag[j] =false;
                }
                if(board.isBoardFull()||!this.anyPrizesLeft())
                    board.gameCompleteFlag = true;
                    int x;
                    if(userSelectedNumber){
                        x = numberAnnounced;
                        board.generateRandomNumber(x);
                        userSelectedNumber=!userSelectedNumber;
                    }
                    else
                        x = board.generateRandomNumber();
                gui.changeButtonColor(x-1);
                System.out.println("Moderator called "+x);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                board.announcedNumber = x;
                
                board.noAnnouncedFlag = true;

                board.lock1.notifyAll();

                while(!allPlayersChanceFinished()){
                    try {
						board.lock1.wait(); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
                // for(int j=0;j<noOfPlayers;j++){
                //     this.allPlayers.get(j).containsNumber(x);
                // }
            }
            System.out.println("All prizes are now finished");
            this.declareWinners();
            System.out.println("Game Over");
            // System.exit(1);
        }
    }

    
    private boolean allPlayersChanceFinished() {
        for(int j=0;j<noOfPlayers;j++){
            if(!board.playerChanceFlag[j])
                return false;
        }
        return true;
    }
    public GameGUI getGUI(){
        return gui;
    }
    // public void actionPerformed(ActionEvent e) {
	// 	// we will have to run a for loop 30 times for each time a button is pressed 
	// 	// we have to identify which button has raised an event
	// 	for(int i = 0; i < 30; i++) {			
	// 		if(e.getSource() == btnDealerBoardNumbers[i]) {				
	// 			// this thread will take a lock on the game object  
	// 			synchronized(board.lock2) {									
	// 				this.setAnnouncedNumber(i+1);
	// 				btnDealerBoardNumbers[i].setForeground(Color.gray);
	// 				btnDealerBoardNumbers[i].setEnabled(false);
	// 				board.lock2.notify();
	// 			}				
	// 			break;
	// 		}
	// 	}
	// }

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
        
        SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				gui = new GameGUI(board,admin,allPlayers);
			}
		});		
        sc.next();
        
		Thread moderatorThread  = new Thread(admin,"Main Thread");
        moderatorThread.start();
        sc.close();

        
    }
    
}
