/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tknob;

/**
 *
 * @author Brooks
 */
/*********************************************************************
  
								 
					 Usage of Simple Knob Component by Chris Dewbery

					 This Program is a Demostration of how to use
					 My The TKnob Component I Made.
	
					 Your also welcome to use this code but only if
					 credit is given where due.
				
					 This is very messy code which i wrote at 
					 midnight can't be bothered commenting
					 but you should be able to figure it out

***********************************************************************/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
	
public class Chris extends JFrame implements ActionListener

{


	
	TKnob Knob2 = new TKnob();
	

	JLabel Label1 = new JLabel("000"); //and some text labels for output
	JLabel Label2 = new JLabel("000");
	JLabel Label3 = new JLabel("000");
	JLabel Label4 = new JLabel("000");
	JLabel Label5 = new JLabel("000");

	public Chris()
	{
		  Container Form; //Our Frame Container
			
         //create our window and our container
	     	JFrame MainWindow = new JFrame("Don't be a Knob - Knob Component Test - Chris Dewbery 2002");
	 	 	MainWindow.setSize(450,115); //set the main window width and height	
	 	
		 	Form = MainWindow.getContentPane(); //set our main frame as the container
			Form.setLayout(new FlowLayout()); //choose flow layout.. this is important!!
														//grid layout makes TKnob too Small

			Form.setBackground(Color.lightGray); //choose lightGray as our Background Color
		
			
			
			Form.add(Knob2);
			Form.add(Label2);
			Knob2.Scale(65); //scale this button down to 35 pixels
			
			//Knob2.SetKnobColor(new Color(205,208,214)); //show use of some of the functions
			Knob2.SetHandleLine(true); //use line instead of handle
			//Knob2.SetHandleColor(Color.darkGray); //dark gray line
			Knob2.addChangeListener(Cl); 
			
		
			
		//	pack();
	      MainWindow.setVisible(true); //set frame to visible
		
	}

public String LZeros(String In) //simple function to add on leading 0's
{
	String Out = new String(In); //didn't know if u had to do this is java so i 
										  //played it safe

	while(Out.length() < 3)	Out = "0" + Out; //3 is the number of chars u want in
														 //your new String. 3 = 001 etc 4 = 0001	
	return Out;
}

ChangeListener Cl = new ChangeListener() //capture change events and deal to them
{
	public void stateChanged(ChangeEvent e) //yes yes i know there is better ways of
	{													 //doing this but i was in a hurry


		if(e.getSource() == Knob2)
		   Label2.setText(LZeros(String.valueOf((int)Knob2.GetAngle())));


   }

	
};

public void actionPerformed(ActionEvent Ae)
{
}

public static void main(String[] args) 
{
		new Chris(); //on load create new Chris Object.
}


}

