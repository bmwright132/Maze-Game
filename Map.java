/*
Brandon Wright
April 29, 2014
This code reads in the Map.txt file and returns the subtring from the Strings being read from the file.
Files accessed: Map.txt, grass.jpg, brick.jpg, finishline.jpg
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
import java.util.*;
import java.io.*;

public class Map
{
   private Scanner s;
   private String Map [] = new String[20];
   private Image grass, brick,finishLine;
   
   /**
   The constructor initializes a new ImageIcon object to jpg files saved
   in the same folder as the code and opens, reads, and closes the Map.txt file.
   */
   public Map()
   {
      ImageIcon img = new ImageIcon("grass.jpg");
      grass = img.getImage();
      
      
      img = new ImageIcon("brick.jpg");
      brick = img.getImage();
      
      img = new ImageIcon("finishline.jpg");
      finishLine = img.getImage();
      
      
      openFile();
      readFile();
      closeFile();
   }
   /**
   This method returns an Image object
   @return grass Image object
   */
   public Image getGrass()
   {
      return grass;
   }
   /**
   This method returns an Image object
  @return brick Image object
   */
   public Image getBrick()
   {
      return brick;
   }
   /**
   This method returns an Image object
   @return finishline Image object
   */
   public Image getFinishLine()
   {
      return finishLine;
   }
   /**
   This method reads an array of Strings at the position specified
   through int x and int y.
   @param int x, int y sends a position for the method to read through the Map array
   @return index String returned from the array at a specified position.
   */
   public String getMap(int x,int y)
   {   
      String index="b";
      try
      { 
         index = Map[y].substring(x,x+1);//y is the position in the array and x determines what part of the string
                                         //to cut out and save to the instance field index.
      }
      catch(Exception e)
      {
         
      }
      return index;
      
   }
   /**
   This method opens a Map.txt file
   */
   public void openFile()
   {
      try
      {
         s = new Scanner(new File("Map.txt"));
      }
      catch(Exception e)
      {
         System.out.println("ERROR OPENING FILE");
      }
   }
   /**
   This method reads the Map.txt file line by line
   */
   public void readFile()
   {
      
      while(s.hasNext())
      {
         for(int i=0; i<20; i++)
         {
            Map[i] = s.next();
         }
      }
   }
   /**
   This method closes the Map.txt file
   */
   public void closeFile()
   {
   
   }
}