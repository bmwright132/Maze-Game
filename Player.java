/*
Brandon Wright
April 29, 2014
This code creates a character Image object and returns the x and y position of the character
Files accessed: character.jpg
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

public class Player
{
   private int tx,ty;
   private Image character;
   
   /**
   The constructor gets the character jpg, assigns it to a Image object,
   as well as sets the characters position on the screen.
   */
   public Player()
   {
      ImageIcon img = new ImageIcon("character.jpg");
      character = img.getImage();
   
      
      tx = 0;
      ty = 16;
   }
   /**
   This method returns a Image object
   @return character Image object
   */
   public Image getCharacter()
   {
      return character;
   }
   /**
   This method returns the characters x position
   @return tx int value for characters x position
   */
   public int getTx()
   {
      return tx;
   }
   /**
   This method returns the characters y position
   @return ty int value for characters y position
   */
   public int getTy()
   {
      return ty;
   }
   /**
   This method accepts an x and y parameter and adds it to the tx and ty instance fields
   @param tx, ty int values that tell the character to move to a certain position on the JFrame 
   */
   public void move(int tx, int ty)
   {
      
      this.tx += tx;
      this.ty += ty;
   }
   
}  