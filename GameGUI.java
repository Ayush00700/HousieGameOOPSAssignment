
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GameGUI implements ActionListener{
	
	/************ DONOT MODIFY THIS CODE BY INTRODUCING NEW VARIABLES *************/
	private Moderator moderator;
	private Board board;	
	private JButton[] btnDealerBoardNumbers;
	private JFrame mainGameFrame;
	/********************** DONOT MODIFY THE CONSTRUCTOR CODE *********************/	
	GameGUI(Board board, Moderator moderator, List<Player> players) {
		
		this.moderator = moderator;
		this.board = board;
				
		mainGameFrame = new JFrame("HOUSIE");
		mainGameFrame.setSize(1600,1600);
		mainGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblDealer = new JLabel("Moderator",JLabel.CENTER);
		mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.Y_AXIS));		
		mainGameFrame.add(lblDealer);
		
		// Panel for moderator buttons
		JPanel dealerPanel = new JPanel();
        // mainGameFrame.getContentPane().add(scrPane);

		dealerPanel.setLayout(new GridLayout(10,9));
		
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
        mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.Y_AXIS));		
		mainGameFrame.add(lblTickets);

		JPanel playerss = new JPanel();
		// playerss.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.Y_AXIS));
		for(int i=0;i<players.size();i++){
            JLabel lblPlayer1 = new JLabel("Player "+players.get(i).getName(),JLabel.CENTER);
            playerss.add(lblPlayer1);
            playerss.add(players.get(i).getPlayerTicketPanel());// Add player1 ticket           
        }
        JScrollPane scrPane = new JScrollPane(playerss);

        // scrPane.setViewportView(playerss);
        // scrPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // scrPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainGameFrame.add(scrPane);
        // mainGameFrame.add(playerss);
		mainGameFrame.add(moderator.lblGameStatus);
		
		mainGameFrame.setVisible(true);

		JLabel lblPrizes = new JLabel("Prizes",JLabel.CENTER);
        mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.Y_AXIS));		
		mainGameFrame.add(lblPrizes);

	}
	public void changeButtonColor(int i){
		btnDealerBoardNumbers[i].setForeground(Color.gray);
		btnDealerBoardNumbers[i].setEnabled(false);
	}
	public void logs(String log){
		JLabel logs = new JLabel(log,JLabel.CENTER);
		mainGameFrame.setLayout(new BoxLayout(mainGameFrame.getContentPane(),BoxLayout.LINE_AXIS));		
		mainGameFrame.add(logs);
	}
	/**************************** DONOT MODIFY THE CODE *************************/
	/* Action taken when the user presses a button on the moderator board */
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < 90; i++) {			
			if(e.getSource() == btnDealerBoardNumbers[i]) {				
				// this thread will take a lock on the game object  
				synchronized(board.lock2) {									
					moderator.setAnnouncedNumber(i+1);
					btnDealerBoardNumbers[i].setForeground(Color.gray);
					btnDealerBoardNumbers[i].setEnabled(false);
					board.lock2.notify();
				}				
				break;
			}
		}
	}		
}
