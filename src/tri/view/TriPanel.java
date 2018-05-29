package tri.view;

import javax.swing.JPanel;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import tri.controller.TriController;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriPanel extends JPanel
{
	private TriController appController;
	private SpringLayout appLayout;
	
	private JButton inputButton;
	private JTextArea outputField;
	private JTextField inputArea;
	private JScrollPane triScrollPane;
	
	public TriPanel(TriController appController)
	{
		super();
		this.appController = appController;
		appLayout = new SpringLayout();
		
		inputButton = new JButton("Input Poloniex URL");
		outputField = new JTextArea(10, 50);
		inputArea = new JTextField(20);
		triScrollPane = new JScrollPane();
		
		setupScrollPane();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	private void setupScrollPane()
	{
		triScrollPane.setViewportView(outputField);
		triScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		triScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		triScrollPane.setBackground(Color.LIGHT_GRAY);
		outputField.setBackground(Color.LIGHT_GRAY);
		outputField.setEditable(false);
		outputField.setLineWrap(true);
		outputField.setWrapStyleWord(true);
	}
	
	public void setupPanel()
	{
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(appLayout);
		
		this.add(triScrollPane);
		this.add(inputButton);
		this.add(inputArea);
	}
	
	public void setupLayout()
	{
		appLayout.putConstraint(SpringLayout.NORTH, triScrollPane, 0, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.WEST, triScrollPane, 0, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.EAST, triScrollPane, 0, SpringLayout.EAST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, triScrollPane, 0, SpringLayout.NORTH, inputArea);
		appLayout.putConstraint(SpringLayout.NORTH, inputButton, 0, SpringLayout.NORTH, inputArea);
		appLayout.putConstraint(SpringLayout.SOUTH, inputButton, 0, SpringLayout.SOUTH, inputArea);
		appLayout.putConstraint(SpringLayout.EAST, inputButton, 0, SpringLayout.EAST, this);
		appLayout.putConstraint(SpringLayout.WEST, inputArea, 10, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, inputArea, -25, SpringLayout.SOUTH, this);
		appLayout.putConstraint(SpringLayout.EAST, inputArea, 0, SpringLayout.WEST, inputButton);
	}
	
	public void setupListeners()
	{
		inputButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				appController.passToTribot(inputArea.getText());
				outputField.setText(appController.passArbitrage().toString());
			}
		});
	}
}
