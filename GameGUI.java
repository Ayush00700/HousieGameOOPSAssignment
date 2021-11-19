
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GameGUI implements ActionListener{
	
	/************ DONOT MODIFY THIS CODE BY INTRODUCING NEW VARIABLES *************/
	private Moderator moderator;
	private Board board;	
	private JButton[] btnDealerBoardNumbers;
	private JFrame mainGameFrame;
	private JPanel pnlWin;
	private boolean buttonsActivated;

	/********************** DONOT MODIFY THE CONSTRUCTOR CODE *********************/	
	GameGUI(Board board, Moderator moderator, List<Player> players) {
		
		this.moderator = moderator;
		this.board = board;
				
		mainGameFrame = new JFrame("HOUSIE");
		mainGameFrame.setSize(1600,1600);
		mainGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblDealer = new JLabel("Moderator",JLabel.CENTER);
		mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.X_AXIS));		
		mainGameFrame.add(lblDealer);
		
		// Panel for moderator buttons
		JPanel dealerPanel = new JPanel();
		pnlWin = new JPanel();
		dealerPanel.setPreferredSize(new Dimension(500, 480));
        // mainGameFrame.getContentPane().add(scrPane);

		dealerPanel.setLayout(new GridLayout(9,10));
		
		// initialize moderator board number buttons
		btnDealerBoardNumbers = new JButton[90];
		
		for(int i = 0; i < 90; i++) {
			btnDealerBoardNumbers[i] = new JButton(String.valueOf(i+1));
			btnDealerBoardNumbers[i].addActionListener(this);
			// btnDealerBoardNumbers[i].setEnabled(false);
			dealerPanel.add(btnDealerBoardNumbers[i]);

		}
		mainGameFrame.add(dealerPanel);
        JLabel lblTickets = new JLabel("Tickets",JLabel.CENTER);
        mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.X_AXIS));		
		mainGameFrame.add(lblTickets);

		JPanel playerss = new JPanel();
		playerss.setPreferredSize(new Dimension(700, 800));

		// playerss.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.Y_AXIS));
		for(int i=0;i<players.size();i++){
            playerss.add(players.get(i).getPlayerTicketPanel());// Add player1 ticket           
            JLabel lblPlayer1 = new JLabel("Player "+players.get(i).getName(),JLabel.CENTER);
            playerss.add(lblPlayer1);
        }
        JScrollPane scrPane = new JScrollPane(playerss);

        // scrPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // scrPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainGameFrame.add(scrPane);
        // mainGameFrame.add(playerss);
		mainGameFrame.add(moderator.lblGameStatus);
		
		mainGameFrame.setVisible(true);
		JLabel lblPrizes = new JLabel("Prizes set by the Moderator",JLabel.CENTER);
		
        mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.X_AXIS));		
		mainGameFrame.add(lblPrizes);
		JPanel pnlPrizes = new JPanel();
		pnlPrizes.setPreferredSize(new Dimension(100,100));

		for(int i=0;i<moderator.getRules().size();i++){
            pnlPrizes.add(logs(moderator.getRules().get(i).toString()));
        }
		
		mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.Y_AXIS));		
		mainGameFrame.add(pnlPrizes);
		
		JLabel lblWin = new JLabel("Winners of the Game",JLabel.CENTER);
		mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.Y_AXIS));		
		mainGameFrame.add(lblWin);
	}
	public void changeButtonColor(int i){
		btnDealerBoardNumbers[i].setForeground(Color.gray);
		btnDealerBoardNumbers[i].setEnabled(false);
	}
	
	public void activateButtons(){
		this.buttonsActivated= true;
	}
	public JLabel logs(String log){
		return new JLabel(log+"           ",JLabel.CENTER);
	}
	
	public void winners(String[] log){
		
		pnlWin.setPreferredSize(new Dimension(300,100));
		for(String s:log){
			pnlWin.add(new JLabel(s+"           ",JLabel.CENTER));
		}
		mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.Y_AXIS));		
		mainGameFrame.add(pnlWin);
	}
	/**************************** DONOT MODIFY THE CODE *************************/
	/* Action taken when the user presses a button on the moderator board */
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < 90; i++) {			
			if(e.getSource() == btnDealerBoardNumbers[i]) {				
				// this thread will take a lock on the game object  
				synchronized(board.lock2) {
					if(buttonsActivated){
						moderator.setAnnouncedNumber(i+1);
						btnDealerBoardNumbers[i].setForeground(Color.gray);
						btnDealerBoardNumbers[i].setEnabled(false);
						board.lock2.notify();
					}									
				}				
				break;
			}
		}
	}		
}
