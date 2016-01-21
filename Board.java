/*
Brandon Wright
April 29, 2014
This code creates a JPanel and adds images to create a maze
Files accessed: Map.java, Player.java, Phidget files.
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
import java.text.DecimalFormat;
import com.phidgets.*;
import com.phidgets.event.*;



public class Board extends JPanel implements ActionListener
{
   private Timer timer;
   private Map m;
   private Player p;
   private String message ="";
   private String startMessage ="MAZE GAME";
   private String startMessage2 ="(press s to start)";
   private boolean win = false;
   private boolean start = false;
   private TextLCDPhidget lcd;
   private int numSeconds = 0;
   private Timer clock;
   private String clock_display="00:00";
   private InterfaceKitPhidget ik;

   
   /**
   The constructor creates a new Map() and Player() object, 
   adds a KeyListener for up, down, left, and right arrow keys,
   sets a timer for the movement of the character and a timer for
   the clock, and initializes the LCD and Interface Kit phidgets.
   */
   public Board() throws Exception
   {
      m = new Map();
      p = new Player();
      addKeyListener(new Al());
      setFocusable(true);
      
      timer = new Timer(100,this);
      timer.start();
      
      clock = new Timer(1000, new MyTimerListener());
   
      //Create a new LCD object
      lcd = new TextLCDPhidget();
   
      lcd.addAttachListener(
            new AttachListener() {
               public void attached(AttachEvent ae) {
                  System.out.println("attachment of " + ae);
               }
            });
   
      lcd.addDetachListener(
            new DetachListener() {
            
               public void detached(DetachEvent ae) {
                  System.out.println("detachment of " + ae);
               }
            });
   
      lcd.addErrorListener(
            new ErrorListener() {
            
               public void error(ErrorEvent ee) {
                  System.out.println("error event for " + ee);
               }
            });
      //open LCD phidget
      lcd.openAny();
      System.out.println("Waiting for the TextLCD to be attached...");
      lcd.waitForAttachment();
   
      System.out.println("Phidget Information");
      System.out.println("====================================");
      System.out.println("Version: " + lcd.getDeviceVersion());
      System.out.println("Name: " + lcd.getDeviceName());
      System.out.println("Serial #: " + lcd.getSerialNumber());
      System.out.println("# Rows: " + lcd.getRowCount());
      System.out.println("# Columns: " + lcd.getColumnCount());
   
      if (lcd.getDeviceID() == TextLCDPhidget.PHIDID_TEXTLCD_ADAPTER) 
      {
         System.out.println("# Screens: " + lcd.getScreenCount());
      
         //set screen 0 as active
         lcd.setScreen(0);
         lcd.setScreenSize(lcd.PHIDGET_TEXTLCD_SCREEN_2x20);
         lcd.initialize();
      
      }
   
      lcd.setDisplayString(0, "MAZE GAME");
      lcd.setDisplayString(1, "Press s to start");
   
      lcd.setBacklight(true);
      
      ik = new InterfaceKitPhidget();  // Allocate space 
      
      ik.openAny(); //Open interface attached
      
      //Wait for the device to be attached
      System.out.println("waiting for InterfaceKit attachment...");
      ik.waitForAttachment();
      System.out.println(ik.getDeviceName());
      
      //Display all values of port of interface kit 
      for (int port = 0; port<8; port++)
      {
         int value = ik.getSensorValue(port);
        
        //You must know where each sensor has been attached to the interface kit
         System.out.println("Sensor in port: " + port + " has the value: " + value); 
      }
      
   }

   /**
   This method displays a message if the character reaches the finish line, 
   and moves the character if the joystick phidget is pushed up, down, left,
   or right.
   @param e ActionEvent object
   */
   public void actionPerformed(ActionEvent e)
   {
      if(m.getMap(p.getTx(),p.getTy()).equals("f"))
      {
         message = "WINNER!!!";
         win = true;
      }
      repaint();
      try
      {
         if(ik.getSensorValue(0) < 200)//down
         {
            if(!m.getMap(p.getTx(),p.getTy()+1).equals("b"))
            {
            
               p.move(0,1);
               
            }
         }
      
         if(ik.getSensorValue(0) > 600)//up
         {
            if(!m.getMap(p.getTx(),p.getTy()-1).equals("b"))
            {
               
               p.move(0,-1);
               
            }
         }
         if(ik.getSensorValue(1) > 600)//left
         {
            if(!m.getMap(p.getTx()-1,p.getTy()).equals("b"))
            {
            
               p.move(-1,0);
               
            }
         }
         if(ik.getSensorValue(1) < 200)//right
         {
            if(!m.getMap(p.getTx()+1,p.getTy()).equals("b"))
            {
               p.move(1,0);
            }
         }
      }
      catch(Exception ex)
      {
      }
   }
   /**
   This method paints the map and the character on the JFrame as well as painting String
   instance fields on the screen.
   @param g Graphics object
   */
   public void paint(Graphics g)
   {
      super.paint(g);
      
      if(!start)
      {
         for(int y=0; y<20; y++)
         {
            for(int x=0; x<20; x++)
            {
               if(m.getMap(x,y).equals("g"))             
               {
                  g.drawImage(m.getGrass(),x*50,y*50,null);   //gets the substring at a certain position
                                                              //in the Map array. Ff the subtring equals
                                                              //"g" it paints a grass image, "b" it paints
               }                                              //a brick image, and "f" paints a finishline image
               if(m.getMap(x,y).equals("b"))
               {
                  g.drawImage(m.getBrick(),x*50,y*50,null);
               }
               if(m.getMap(x,y).equals("f"))
               {
                  g.drawImage(m.getFinishLine(),x*50,y*50,null);
               }
            }
         }
         
         //sets font and draws message on the JPanel
         g.setFont(new Font("Times", Font.BOLD, 70));
         g.drawString(startMessage,250,300);
         g.setFont(new Font("Times", Font.BOLD, 50));
         g.drawString(startMessage2,300,500);
      }
      //if start is true paint the maze and character on the JPanel
      if(start)
      {
         if(!win)
         {
            for(int y=0; y<20; y++)
            {
               for(int x=0; x<20; x++)
               {
                  if(m.getMap(x,y).equals("g"))
                  {
                     g.drawImage(m.getGrass(),x*50,y*50,null);
                  }
                  if(m.getMap(x,y).equals("b"))
                  {
                     g.drawImage(m.getBrick(),x*50,y*50,null);
                  }
                  if(m.getMap(x,y).equals("f"))
                  {
                     g.drawImage(m.getFinishLine(),x*50,y*50,null);
                  }
               }
            }
            g.drawImage(p.getCharacter(), p.getTx() *50, p.getTy()*50, null);
         
         }
      
         //if the character reaches the finishline, a message pops up saying the player has won.
         if(win)
         {
            g.setFont(new Font("Times",Font.BOLD,70));
            g.setColor(Color.blue);
            g.drawString(message, 300,300);
            clock.stop();
            try
            {
               lcd.setDisplayString(0, "WINNER!!!");
               lcd.setDisplayString(1, "Your time: " +clock_display);
            }
            catch(Exception ex)
            {
               System.out.println("ERROR DISPLAYING TEXT");
            }
            
         }
         
         
      }
      
   }
   /**
   This method formats the clock for the game.
   @param numSeconds int value for the number of seconds the clock is running.
   @return String for the newly formatted time.
   */
   public String formatTime(int numSeconds )
   {
      int minutes = numSeconds/60;
      int seconds = numSeconds % 60;
      DecimalFormat formatter = new DecimalFormat("00");
   		
      String format = formatter.format(minutes)+":"+formatter.format(seconds);
      return format;
   	
   }

   
   public class Al extends KeyAdapter 
   {
           
      /**
      This method listens for either up, down, left, or right arrow keys being pressed on the keyboard.
      @param e KeyEvent object
      */
      public void keyPressed(KeyEvent e)
      {
         int keycode = e.getKeyCode();
         //if keycode returned is eqaul to the keycode that is returned from hitting the up arrow
         if(keycode == KeyEvent.VK_UP)
         {
            if(!m.getMap(p.getTx(),p.getTy()-1).equals("b"))
            {
               
               p.move(0,-1);
               
            }
         }
         if(keycode == KeyEvent.VK_DOWN)
         {
            if(!m.getMap(p.getTx(),p.getTy()+1).equals("b"))
            {
            
               p.move(0,1);
               
            }
         }
         if(keycode == KeyEvent.VK_LEFT)
         {
            if(!m.getMap(p.getTx()-1,p.getTy()).equals("b"))
            {
            
               p.move(-1,0);
               
            }
         }
         if(keycode == KeyEvent.VK_RIGHT)
         {
            if(!m.getMap(p.getTx()+1,p.getTy()).equals("b"))
            {
               p.move(1,0);
            }
         }
         if(keycode == KeyEvent.VK_S)
         {
            start = true;
            
            //set the display text for the LCD
            try
            {
               lcd.setDisplayString(0,"Time starts now: ");
               lcd.setDisplayString(1,clock_display);
               clock.start();
            
            }
            catch(Exception ex)
            {
            }
            
            
         }
      }
      public void keyReleased(KeyEvent e)
      {
      
      }
      public void keyTyped(KeyEvent e)
      {
      
      }
   }
   
   private class MyTimerListener implements ActionListener
   {
      /**
      This method increments numSeconds every 1000 miliseconds and displays the
      formatted time on the LCD screen
      @param e ActionEvent object
      */
      public void actionPerformed(ActionEvent e)
      {
         numSeconds++;
         clock_display = formatTime(numSeconds);
         try
         {
            lcd.setDisplayString(1, formatTime(numSeconds));
         }
         catch(Exception ex)
         {
            System.out.println("ERROR DISPLAYING TIME");
         }
         
      
      }
   	
   		
   }
}
