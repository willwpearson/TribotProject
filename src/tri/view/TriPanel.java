package tri.view;

import javax.swing.JPanel;
import tri.controller.TriController;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriPanel extends JPanel
{
	private TriController appController;
	private SpringLayout appLayout;
	
	private JButton inputButton;
	private JTextField outputField;
	private JTextArea inputArea;
	
	public TriPanel(TriController appController)
	{
		super();
		this.appController = appController;
		appLayout = new SpringLayout();
		
		inputButton = new JButton("Input Website URL");
		outputField = new JTextField();
		inputArea = new JTextArea();
		
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	public void setupPanel()
	{
		this.setBackground(Color.CYAN);
		this.setLayout(appLayout);
		
		this.add(outputField);
		this.add(inputButton);
		this.add(inputArea);
	}
	
	public void setupLayout()
	{
		appLayout.putConstraint(SpringLayout.NORTH, outputField, 0, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.WEST, outputField, 0, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.EAST, outputField, 0, SpringLayout.EAST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, outputField, 0, SpringLayout.NORTH, inputArea);
		appLayout.putConstraint(SpringLayout.NORTH, inputButton, 0, SpringLayout.NORTH, inputArea);
		appLayout.putConstraint(SpringLayout.SOUTH, inputButton, 0, SpringLayout.SOUTH, inputArea);
		appLayout.putConstraint(SpringLayout.EAST, inputButton, 0, SpringLayout.EAST, this);
		appLayout.putConstraint(SpringLayout.WEST, inputArea, 10, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, inputArea, -25, SpringLayout.SOUTH, this);
		appLayout.putConstraint(SpringLayout.EAST, inputArea, 0, SpringLayout.WEST, inputButton);
	}
	
	public void setupListeners()
	{
		
	}
}
