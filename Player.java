import java.util.*;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Player implements Runnable{
    private int id;
    private String playerName;
    private Ticket card;
    private boolean hasAlreadyWon;
    private final List<WinningConditions> rules;
    private WinningConditions prizeWon;
    private Board board;
	private final static int MAXNO = 27;		// maximum numbers on player ticket
    private Moderator moderator;
    private JPanel playerTicketPanel;		// GUI component
	private JButton[] btnOnTicket;

    Player(List<WinningConditions> rules,int id, String playerName, Board b,Moderator moderator){
        this.playerName=playerName;
        card = new Ticket(playerName);
        hasAlreadyWon =false;
        this.rules = rules; 
        this.board = b;
        this.id =id;
        this.moderator = moderator;
		// initialize player panel
		playerTicketPanel = new JPanel();
		// set playerPanel layout
		playerTicketPanel.setLayout(new GridLayout(3,9));
		// create an array of six buttons 
		btnOnTicket = new JButton[MAXNO];
		
        int cardOfPlayer[][] = this.card.getCard();     
        List<String> tempCard = new ArrayList<>();
        for(int row=0;row<3;row++){
            for(int col=0;col<9;col++){
                tempCard.add(cardOfPlayer[row][col] != 0 ? Integer.toString(cardOfPlayer[row][col]):"  ");
            }
        }
		// initialize the buttons on ticket and add them to playerPanel
		for(int i = 0; i < MAXNO; i++) {
                btnOnTicket[i] = new JButton(String.valueOf(tempCard.get(i)));
                btnOnTicket[i].setEnabled(false);
                playerTicketPanel.add(btnOnTicket[i]);
            }
		
    }
    public boolean containsNumber(int calledNumber){
        for(int row=0;row<3;row++){
            for(int col=0;col<9;col++){
                if(card.getCard()[row][col]==calledNumber){
                    this.btnOnTicket[row*9+col].setBackground(Color.GREEN);
                    card.crossOut(calledNumber);
                    if(!hasAlreadyWon && checkIfWon()){
                        hasAlreadyWon = true;
                        board.playerSuccessFlag[id] = true;
                        System.out.printf("Player %s has completed %s \n",this.playerName,this.prizeWon.getClass().getName());
                        moderator.getGUI().logs("Player "+this.playerName+" has completed "+this.prizeWon.getClass().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }                    
                    }
                    return true; 
                }
            }
        }
        return false;
    } 
    
    public void run(){
        synchronized(board.lock1) {
            while(!board.gameCompleteFlag) {
                while(!board.noAnnouncedFlag || board.playerChanceFlag[id]) {
					try {
						board.lock1.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
                if(!board.gameCompleteFlag) {
                    this.containsNumber(board.announcedNumber);
                    
                    board.playerChanceFlag[id] = true;

                    board.lock1.notifyAll();
   
                }
            }
        }	

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
    
	public JPanel getPlayerTicketPanel() {		
		return playerTicketPanel;
	}
}
