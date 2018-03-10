/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tknob;

/*********************************************************************
  
								Don't be a Knob!!!! hehe
 
					 Simple Knob Component by Chris Dewbery
    								  Weltec 2002

					 Not 100% Accurate but hey its a working
      				   Knob Component What do u care...
			

				   If you are at weltec pls give credit where
					   it is due. don't just steal this.

					I have added quite a few functions and this
					Module has become quite extensive. Colors of
					Knobs/Handles/Shadows can be changed as well as
					turning the handle into a line etc.
					You Want anything else added.. fixed ask me.
					Oh and if you add something to this tell me and 
					i might even add it to the new version.
					
					Version 0.2
						Fixed Rotation Bug where handle wasn't drawn from its
						Center Position
						Added Scale Function for Easier Use.

					Version 0.1
						First Release.
				
***********************************************************************/

import java.awt.*;			 //don't you always need this? :P
import java.awt.event.*;    //mouse events
import javax.swing.*;       //jcomponent
import javax.swing.event.*; //changelistener

public class TKnob extends JComponent 
{														
	 //handle stuff
	 public int Handle_Size;      //size of handle
	 private Color Handle_Color;   //color of handle
	 private int Handle_Closeness; //how close to edge of knob 
	 private boolean Handle_Show; //is handle visible
	 private boolean Handle_Line; //should we use a line instead of handle
	
	 //knob stuff
	 private  Color Knob_Color; //knob fill color
	 private  int Size;         //size of the knob
    private double Angle;     //to hold the position of the knob
	 private boolean Visible;  //component visible???
	 private int MaxAngle; //max and min turning angles of knob 0-360 = infinate turn
	 private int MinAngle;
		 
	 //shadow stuff
	 private Color Shadow_Color; //for setting shadow color
	 private int Shadow_Size; //shadow size

	 private ChangeEvent MyEvent = null; //init our Change Event Handler
    private EventListenerList Listener = new EventListenerList(); //list to Store
   																					//our Events

    public TKnob () //constructor
    {
      setPreferredSize(new Dimension(70,60)); //if possible set knob to 55,50
	   Handle_Size = 10; //size of handle
	   Handle_Color = new Color(0,0,0); //init default colors RED handle
		Shadow_Color = new Color(0,0,0); //gray shadow
		Knob_Color = new Color(255,208,0); //and a nice navy blue for the knob
		Handle_Closeness = 10; //set the Handle 5 pixels from the Perimitor of our knob
	   Angle = 360; //set the Default angle as 360
		Handle_Show = true; //we want a handle
		Visible = true; //knob visible
		Handle_Line = false; //not line draw handle
		Shadow_Size = 6; //default shadow size
		MaxAngle = 360; //infinite turn 360-0
		MinAngle = 0;
	
  		addMouseMotionListener(MyMouseHandler); //set up a Mouse Handler for this
															 //component
	 }

	 public void addChangeListener(ChangeListener cl) 
	 {
	 	Listener.add(ChangeListener.class, cl); //add a New ChangeListener to the
    }														 //list
    
	 protected void OnChange() 
	 {
     Object[] L = Listener.getListenerList();

 	    for (int i = L.length-2; i>=0; i-=2) //as list is order backwards
		 {
	      if (L[i] == ChangeListener.class)
		   {											
			   if (MyEvent == null) MyEvent = new ChangeEvent(this); //create new 
		      ((ChangeListener)L[i+1]).stateChanged(MyEvent);		//event if null
	      }
		 
		 }
	 }
    
	 MouseMotionAdapter MyMouseHandler = new MouseMotionAdapter() //create us a mouse
	 { //this routine handles the mouse drags							  //handler
		
		public void mouseDragged(MouseEvent m) //if the mouse is dragged
		{
		  	int MouseX = m.getX(); //get the mouse X and Y coordinates
			int MouseY = m.getY();

			if((MouseX<Size)&&(MouseX>0)) //check if we are on the knob
			if((MouseY<Size)&&(MouseY>0))
			{
			   MouseX = (Size/2) - MouseX; //if so get mouse position
				MouseY = (Size/2) - MouseY;//realitive to the center of the 
													//knob
			   if(Visible)
			   {
			      Angle = Math.atan2(MouseX, MouseY)/(Math.PI/180); //update our new angle
	
			      if((MinAngle == 0)&&(MaxAngle == 360))
			      {
			           if(Angle<=0) Angle = 360 + Angle; //clip the angle make sure not to big
				        if(Angle>360) Angle = Angle - 360;
			      } 
			      else													
			      {   
			           if(Angle<MinAngle) Angle = MinAngle; //stop movememnt at max angle
				        if(Angle>MaxAngle) Angle = MaxAngle; //by setting it like this 360-0 is an infinate loop
			      }

		   	}
			}

			repaint(); //update knob position
		 }
		};

	 public void Scale(int size) //retain aspect ratio and scale down our knob
	 {									//saves having to change each field individually
	 	   SetKnobSize(size);   
			SetHandleSize(size/5);
			SetHandleCloseness(size/5);
	 }

	 public void SetKnobColor(Color c)
    {
	   	Knob_Color = c; //change knob fill color
	 }

	 public void SetShadowColor(Color c)
    {
	      Shadow_Color = c; //change knob shadow color
	 }
	 
	 
	 public void SetHandleVisible(boolean state)
	 {
	     Handle_Show = state; // set handle visible or invisible
		  if(Handle_Show) Handle_Line = false; //can't have line if we have handle
	 }
	
    public void SetVisible(boolean state)
	 {
	 	 Visible = state; //set component ..aka knob visible or invisible
	 }

	 public void SetContraints(int maxAngle,int minAngle)
	 {
			MaxAngle = maxAngle;
	 	   MinAngle = minAngle;
	 }

	 public void SetHandleColor(Color c)
    {
	   	Handle_Color = c; //set our handle fill color to c
	 }

	 public void SetHandleLine(boolean state)
	 {
	 	Handle_Line = state;
		if (Handle_Line) Handle_Show = false; //as we can't have a line and handle
	 }

	 public void SetShadowSize(int size)
	 {
	 	 Shadow_Size = size; //set shadow size
	 }

	 public void SetHandleSize(int size)
	 {
	 		Handle_Size = size; //change our handle size
	 }

	 public void SetHandleCloseness(int Closeness)
	 {
			Handle_Closeness = Closeness; //alter our closeness this is how close
	 }												//to the premetier the handle is

	 public void SetKnobSize(int size)
	 {
	    setPreferredSize(new Dimension(size,size)); //adjust our knob size
		 Size = size - Shadow_Size; //-10 so that it actually fits in the size field
	 }

	 public void SetAngle(double angle) 
	 {
	 	Angle = -(angle+MinAngle); //set the angle of the knob realtive to the minangle
		repaint();    //redraw the knob which also calls the ChangeEvent
	 }
	 
	 public double GetAngle()
	 {
	 	return (MaxAngle) - Angle; //correct our angle for output to the screen
	 }
	
 	 private int round(double value) //simple rounding function
    {											//smooths out movement of knobs
      double rounded = 0;				//when turned

		if(value >= 0.0)
		{
			rounded = Math.floor(value + .5);
		}
		else
			rounded = Math.ceil(value - .5);

		return (int)rounded;
    }


    public void paint(Graphics g) //our knobs paint method
    {
      
		 Size = Math.min(getWidth(), getHeight())-(Shadow_Size+10); //return the smallest and get our knob size

		//set antialiasing on.

	    Graphics2D Graphic2d = (Graphics2D) g;   
	    Graphic2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
			 									   RenderingHints.VALUE_ANTIALIAS_ON));
		  //draw Knob and Shadow
       if (Visible)
		 {
		    g.setColor(Shadow_Color);
		    g.fillOval(0,0,Size+Shadow_Size,Size+Shadow_Size); //draw our shadow bigger than our knob
		  
		    g.setColor(Knob_Color);
	       g.fillOval(2,2,Size,Size);    //draw a circle to represent a knob
		
		    g.setColor(Color.black);  //draw a nice outline
		    g.drawOval(2,2,Size,Size);
       
	     //now calculate and draw our handle the only moving part on this component
		  int HandlePos[] = new int[2]; //to store our handle X,Y position
      
	    if (Handle_Show) //if we have choosen to show the handle
		 {
		  		//calculate position on handle.
		     	HandlePos[0]=round(Size / 2+Math.sin(Angle * (Math.PI /180))* //get the X position of
		  					       -((Size-(Handle_Size/2+Handle_Closeness))/2));//our Handle from the Angle

            HandlePos[1]=round(Size / 2+Math.cos(Angle * (Math.PI /180))*//get the Y position of
		  						    -((Size-(Handle_Size/2+Handle_Closeness))/2));//our Handle
				
		      g.setColor(Handle_Color);
		      g.fillOval(2+HandlePos[0]-(Handle_Size/2),2+HandlePos[1]-(Handle_Size/2),Handle_Size,Handle_Size); //draw handle
				
		      g.setColor(Color.black);
		      g.drawOval(2+HandlePos[0]-(Handle_Size/2),2+HandlePos[1]-(Handle_Size/2),Handle_Size,Handle_Size); //handle outline
       }
		 else
		 if (Handle_Line) //if we want a line and no handle can't have both thats being stupid
		 {
		 	    HandlePos[0]=round(Size / 2+Math.sin(Angle * (Math.PI /180))*-(Size/2)); //create line-end X,Y 
             HandlePos[1]=round(Size / 2+Math.cos(Angle * (Math.PI /180))*-(Size/2)); //Points
		
	          g.setColor(Handle_Color);
		 	    g.drawLine(2+HandlePos[0],2+HandlePos[1],2+Size/2,2+Size/2);
		 }


		  OnChange(); //tell everything that the Knob has Changed
		}
    }

}
