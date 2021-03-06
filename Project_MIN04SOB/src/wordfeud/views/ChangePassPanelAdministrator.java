package wordfeud.views;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import wordfeud.core.mvc.CorePanel;

import net.miginfocom.swing.MigLayout;

public class ChangePassPanelAdministrator extends CorePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7891036503091223344L;
	private JLabel NamePass;
	private JTextField Newpass;
	private JLabel errorLabel;
	private JButton back;
	private JButton change;
	private JList<String> accountList;
	private JScrollPane scrollPane;

	public ChangePassPanelAdministrator() {
		setPreferredSize(new Dimension(925, 498));
		setLayout(new MigLayout(
				"",
				"[120px][::2000px,growprio 50,grow][::2000px,grow][::250px,growprio 70,grow]",
				"[30px][::2000px,grow][30px][30px]"));
		scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1 4 4,grow");
		accountList = new JList<String>();
		accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(accountList);
		errorLabel = new JLabel("");
		add(errorLabel, "cell 1 5 2 1,grow");
		NamePass = new JLabel("nieuw wachtwoord");
		add(NamePass, "cell 0 11,alignx right,aligny center");
		Newpass = new JTextField(11);
		add(Newpass, "cell 1 11 3 1,grow");
		back = new JButton("Back");
		add(back, "cell 1 13,grow");
		change = new JButton("Change");
		add(change, "flowy,cell 3 13,grow");
		change.setEnabled(false);

		accountList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				change.setEnabled(true);
			}

		});
	}

	public void BackaddActionListener(ActionListener listener) {
		back.addActionListener(listener);
	}

	public void ChangeaddActionListener(ActionListener listener) {
		change.addActionListener(listener);
	}

	public void fillPlayerList(String[] players) {
		accountList.setListData(players);

	}

	public String getSelectedPlayer() {
		return accountList.getSelectedValue();
	}

	public String getSelectedNewPass() {
		return Newpass.getText();
	}

	public void setText(String msg) {
		change.setText(msg);
	}

	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
	}

}

/*
 * /
 * 
 * 
 * /
 */