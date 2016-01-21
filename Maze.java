/*
Brandon Wright
April 29, 2014
This code creates a new Board object and adds it to the JFrame
Files accessed: Board.java
I am the sole author of this assignment. I visited www.youtube.com/watch?v=6DDjE-r3olk, 
http://www.dreamincode.net/forums/topic/280188-need-some-help-with-a-basic-java-maze-game/,
http://forum.codecall.net/topic/63862-maze-tutorial/,
http://rs.cs.iastate.edu/smarthome/documents/Manuals%20and%20Tutorials/Phidgets/PhidgetsManual.pdf
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import com.phidgets.TextLCDPhidget;
import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;
import com.phidgets.event.DetachEvent;
import com.phidgets.event.DetachListener;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;

public class Maze extends JFrame
{
   /**
   The constructor sets the title, size, location, and the default close operation
   as well as adding a new Board() object to the JFrame.
   */
   public Maze() throws Exception
   {
      setTitle("Maze Game");
      add(new Board());
      setSize(1000,1200);//(x,y)
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
      setVisible(true);
   }
  

   
   public static void main(String []args)throws Exception
   {
      
      new Maze();
      
   }
}