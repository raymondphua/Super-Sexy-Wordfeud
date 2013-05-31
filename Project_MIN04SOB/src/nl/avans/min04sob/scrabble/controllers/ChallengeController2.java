package nl.avans.min04sob.scrabble.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;

import nl.avans.min04sob.scrabble.core.CoreController;
import nl.avans.min04sob.scrabble.models.AccountModel;
import nl.avans.min04sob.scrabble.models.ChallengeModel;
import nl.avans.min04sob.scrabble.models.ChallengeModel2;
import nl.avans.min04sob.scrabble.views.ChallengeView2;


public class ChallengeController2 extends CoreController {

	private ChallengeView2 challengeView2;
	private ChallengeModel challengeModel;
	private JFrame frame;
	private AccountModel account;
	
	public ChallengeController2(AccountModel user) {
		account = user;
		initialize();
		addListeners();
		//add array
		frame.setAlwaysOnTop(true);
		frame.add(challengeView2);
		
		addView(challengeView2);
		addModel(challengeModel);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		challengeView2.showChallenge();
	}
	
	
	@Override
	public void initialize() {
		frame = new JFrame();
		challengeView2 = new ChallengeView2();
		challengeModel = new ChallengeModel(account);
	}

	@Override
	public void addListeners() {
		challengeView2.addActionListenerAccept(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				acceptChallenge();
				frame.dispose();
			}
		});
		challengeView2.addActionListenerDecline(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				declineChallenge();
				frame.dispose();
			}
		});
		challengeView2.addActionListenerBack(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});
		
	}
	
	public void receiveChallenges(){
		int index=0;
		while(index<challengeModel.challengeArray().size())
		{
			challengeView2.receiveChallenge(challengeModel.challengeArray().get(index));
			index++;
		} 
		challengeView2.showChallenge();
	}
	
	private void acceptChallenge() {
		try {
			challengeModel.respondChallenge(challengeView2.getSelectedChallenge(),true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void declineChallenge() {
		try {
			challengeModel.respondChallenge(challengeView2.getSelectedChallenge(),false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void goBack() {
		frame.dispose();
		frame = null;
	}

}