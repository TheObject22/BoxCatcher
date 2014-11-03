import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * This program is a simple game of catch the box!  The user moves a rectangular
 * box around the screen in attempt to place it inside a larger box which is
 * moving independently.
 *
 * @author Andrew Nuxol
 * @author Garrett Becker
 *
 * @version February 2014
 * ------------------------------
 * last updated:  Spring 2014
 * ------------------------------
 */
public class BoxCatcher extends Panel implements MouseMotionListener, Runnable
{
/**
     * Variables!
     *
     * This program uses several variables to track the state of the game.
     *
     */

    //This constant defines how big the window is.
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;

    //These variables define the initial position and size of the big box
    private int bigBoxX = WINDOW_WIDTH / 2;
    private int bigBoxY = WINDOW_HEIGHT / 2;
    private int bigBoxSize = 30;

    
    //These variables define the initial position and size of the little box
    private int lilBoxX = 10;
    private int lilBoxY = 10;
    private int lilBoxSize = 20;

    //If the big box is moving (part B of the assignment) these variables
    //specify its trajectory.
    private int bigBoxDeltaX = 0;
    private int bigBoxDeltaY = 0;

    
    
    //This variable is used to track the user's current score.  
    private int score = 0;

    //This variable should be set to true when the little square first enters
    //the big square.  It should be set to false as soon as it leaves.
    private boolean inside = false;
    private boolean inside1=true;

    //This variable stores how many seconds have elapsed since the program
    //started.  Prof Nux's code will update this value for you.  Your only
    //responsibility is display it for the user with the paint method.
    private int seconds = 0;

    
    private int response=0;
    private boolean response1=false;
    
    private boolean xB=false;
    private boolean yB=false;
    
    
    
    
    /*======================================================================
     * Methods
     *----------------------------------------------------------------------
     */

    /**
     * This method is called exactly once when your program is first started.
     * You'll eventually use it to allow the user to set the game difficulty.
     */
    public void init() 
    {

        do
        {
            String difficulty1=JOptionPane.showInputDialog("Enter a level of difficulty(1-5)").trim();//asks the user for difficulty, trims unneeded space
            response= Integer.parseInt(difficulty1);//converts string to integer
            if(response<1||response>5)//checks to see if response is valid
            {
                
                response1=false;

            }
            else
            {

                response1=true;

            }
        }
        while(response1==false);//if response isn't valid, the user will be asked to enter a difficulty again
        //checks to see if what level of difficulty the user will be assigned
        if (response==1)
        {
            bigBoxSize=30;
        }
        else if(response==2)
        {
            bigBoxSize=27;
        }
        else if(response==3)
        {
            bigBoxSize=25;
        }
        else if(response==4)
        {
            bigBoxSize=23;
        }
        else if(response==5)
        {
            bigBoxSize=22;
        }

    }

    /**
     * @return true if the little box is currently entirely within the big box
     * and false otherwise
     */
    public boolean isInside()
    {
        //checks to see if the small box is inside the big box
        if(lilBoxY>bigBoxY&&lilBoxX>bigBoxX&&lilBoxX+lilBoxSize<bigBoxX+bigBoxSize&&lilBoxY+lilBoxSize<bigBoxX+bigBoxSize)
        {
            inside=true;
            return true;
        }
        else//if it's not, this statement will be false and will not execute in the paint method
        {
            inside=false;
            return false;
        }
    }

    /**
     * @return true if the little box is overlapping with the big box and false
     * otherwise
     */
    public boolean isOverlapping()
    {
        //checks to see if the box is overlapping
        
            if(lilBoxX<=bigBoxX+bigBoxSize&&lilBoxY+lilBoxSize>=bigBoxY&&lilBoxY<=bigBoxY+bigBoxSize&&lilBoxX>=bigBoxX-lilBoxSize)
            {
                return true;
            }   
         
        else{//if it is not overlapping the code in the paint method will not execute

            return false;
        }
    }//isOverlapping
    /**
     * When called, this method will draw the big and little boxes at their
     * current positions based on the class variables above.  The color of the
     * boxes vary depending upon whether their relative positions:
     *   
     *   small box is fully - the big box is white with a black
     *   inside the big box   border.  The small box is green.
     *
     *   small box overlaps - the big box is gray with a black
     *   the big box          border.  The small box is yellow.
     *
     *   otherwise          - the big box is black and the
     *                        small box is red
     *
     *  This method also updates and displays the user's score and the number of
     *  elapsed seconds since the program started.  The user gets +1 to score
     *  each time the small box is moved inside the big box.
     *  
     */
    public void paint(Graphics canvas)
    {
        
        //draws the big and small box
        canvas.drawRect(bigBoxX, bigBoxY, bigBoxSize, bigBoxSize);
        canvas.drawRect(lilBoxX, lilBoxY, lilBoxSize, lilBoxSize);
        
        //displays time and score
        canvas.drawString("Time: " + seconds,WINDOW_WIDTH-100,WINDOW_HEIGHT-100);
        canvas.drawString("Score: "+score,WINDOW_WIDTH-100,WINDOW_HEIGHT-150);
        if(isInside()==true)//checks to see if the small box is inside the big box
        {
            //if true, then draws the big and small box to these set colors
            canvas.setColor(Color.green);
            canvas.fillRect(lilBoxX, lilBoxY, lilBoxSize, lilBoxSize);
            canvas.setColor(Color.black);
            canvas.drawRect(bigBoxX, bigBoxY, bigBoxSize, bigBoxSize);


        }
        else if(isOverlapping()==true)//checks to see if the small box is overlapping the big box
        {
            //if true, then draws the big and small box to these set colors
            canvas.setColor(Color.gray);
            canvas.fillRect(bigBoxX, bigBoxY, bigBoxSize, bigBoxSize);
            canvas.setColor(Color.black);
            canvas.drawRect(bigBoxX, bigBoxY, bigBoxSize, bigBoxSize);
            canvas.setColor(Color.yellow);
            canvas.fillRect(lilBoxX, lilBoxY, lilBoxSize, lilBoxSize); 
        }
        else//if it's neither draws the small box to these colors
        {
            canvas.setColor(Color.black);
            canvas.fillRect(bigBoxX, bigBoxY, bigBoxSize, bigBoxSize);
            canvas.setColor(Color.red);
            canvas.fillRect(lilBoxX, lilBoxY, lilBoxSize, lilBoxSize);
        }
        //checks to see if the small box has left the big box
        if(inside!=inside1)//checks to see if the small box is inside by comparing boolean operators
        {
            if(inside!=true)//if the small box isn't inside then it sets the inside1 value to be false so it doesn't check these if statements
            {
                inside1=inside;   

            }
            else{//if the small box is in the big box it will increment the score by 1 and then set the inside1 variable to false so it exits these if statements
                ++score;
                inside1=inside;
            }
        }

    }//paint
    

//======================================================================
//                        Homework 4b
//
// The code below this point is for homework 4b.  You can safely
// ignore it while you work on homework 4a.
// ======================================================================

    //This variable specifies whether the big box should move or not
    //Set this to true when you start on HW#4B.)
    private boolean movement = true;
    

    /**
     * This method repeatedly changes the x,y position of the box using its
     * trajectory as specified by bigBoxDeltaX and bigBoxDeltaY.  If the big box
     * would hit a wall it "bounces" by multiplying one or both of its
     * trajectory values by -1.  Each time box is moved, the repaint method
     * is called to update its position and then the pause() method is called
     * so the user has time to react.
     *
     * This method returns when the user catches the box (i.e., the user's
     * score changes).
     */
    void moveTheBox()
    {
        
        
        while(!isInside())//checks to see if the small box is inside
        {
            if(!xB)//moves the big cube in the x direction
            {
            bigBoxX=bigBoxDeltaX+bigBoxX; 
            }
            if(!yB)//moves the big cube in the y direction
            {
            bigBoxY=bigBoxDeltaY+bigBoxY;
            }

            if(Math.abs(bigBoxX)<15||Math.abs(bigBoxX)+bigBoxSize>WINDOW_WIDTH-15)//checks to see if the box is in bounds in x direction
            
            {
                bigBoxDeltaX=bigBoxDeltaX*-1;//multiplies by -1 to move it out if it's within those boundaries
                
            }
            //same thing as before, but for y direction
            if(Math.abs(bigBoxY)<15||Math.abs(bigBoxY)+bigBoxSize>WINDOW_HEIGHT-15)
           
                
            {
                bigBoxDeltaY=bigBoxDeltaY*-1;
                
            }
            repaint();
            pause();
            
        }   
        
        
    }//moveTheBox

   

//======================================================================
//                        ATTENTION STUDENTS!
//
// Do not modify the code below this point.  However you are welcome
// to review it.  Most of this code is pretty straightforward but some
// concepts (especially threads) will be new to you.
// ======================================================================

    //This variable stores the time when the program started
    private long startTime = 0;
    //A random number generator is used to select starting position and velocity
    //for the big box
    private Random randGen = new Random();

    
    /**
     * Whenever an instance of BoxCatcher is created, it registers
     * itself to be notified whenever the mouse cursor moves and also
     * saves the canvas in an instance variable
     */
    public BoxCatcher(Canvas initCanvas)
    {
        //save a pointer to the graphics canvas
        myCanvas = initCanvas;

        //listen for mouse movement
        myCanvas.addMouseMotionListener(this);

    }//BoxCatcher ctor

    /*
     * These variables are used for double buffering and are initialized in
     * the main() method at the bottom of this file.
     */
    Canvas myCanvas = null;
    BufferStrategy strategy = null;

    
    /**
     * this method gets called whenever the user moves the mouse
     */
    public void mouseMoved(MouseEvent event)
    {
        lilBoxX = event.getX();
        lilBoxY = event.getY();
        repaint();
    }

    /**
     * this method is called whenever the user drags the mouse cursor.
     * This event is currently ignored
     */
    public void mouseDragged(MouseEvent event)
    {
        //Do nothing when the mouse is dragged
    }

    /**
     * this method causes the computer to wait a split second before
     * continuing.  The more you play, the faster it goes!
     */
    public void pause()
    {
        try
        {
            //gradually decrease the delay time from 100ms to 20ms
            int duration = 100 - score*2;
            if (duration < 20)
            {
                duration = 20;
            }

            Thread.sleep(duration);
        }
        catch(InterruptedException ie)
        {
            //it's safe to ignore this
        }
    }//pause

    /**
     * this method manages the creation and display of the next frame in the
     * game animation
     */
    public void repaint()
    {
        //Retrieve the canvas used for double buffering 
        Graphics hiddenCanvas = strategy.getDrawGraphics();

        //Start with a black pen on a white background
        hiddenCanvas.setColor(Color.white);
        hiddenCanvas.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        hiddenCanvas.setColor(Color.black);
        
        //Update the elapsed time
        seconds = (int)((System.currentTimeMillis() - startTime) / 1000);
        
        //Draw the next frame in the game animation
        paint(hiddenCanvas);

        //Display the new canvas to the user
        strategy.show();
        try
        {
            Thread.sleep(1);
        }
        catch(InterruptedException ie)
        {
            //it's safe to ignore this
        }

    }//repaint
    
    
    /**
     * this method contains the main run loop for the game
     *
     */
    public void run()
    {
        //Init the starting time
        startTime = System.currentTimeMillis();
       
        //Setup double-buffering
        myCanvas.createBufferStrategy(2);
        strategy = myCanvas.getBufferStrategy();
        
        //Keep running until the user presses the "Close" button
        while(true)
        {
            //Update the elapsed time
            seconds = (int)((System.currentTimeMillis() - startTime) / 1000);

            //draw the next frame
            repaint();

            //If the big box isn't supposed to move then do nothing else
            if (!movement)
            {
                continue;
            }

            //Select a random starting location for the box at least border from the edge
            int border = bigBoxSize + 20;
            bigBoxX = randGen.nextInt(WINDOW_WIDTH - 2 * border) + border;
            bigBoxY = randGen.nextInt(WINDOW_HEIGHT - 2 * border) + border;

            //Select a random trajectory for the box that has a magnitude of at
            //least 5 in each direction
            double magnitude = 0.0;
            do
            {
                bigBoxDeltaX = randGen.nextInt(40) - 20;
                bigBoxDeltaY = randGen.nextInt(40) - 20;

                magnitude = Math.sqrt(bigBoxDeltaX*bigBoxDeltaX + bigBoxDeltaY*bigBoxDeltaY);
            }
            while(magnitude < 8.0);
           
            //Move the box until the user catches it
            moveTheBox();

            
        }//while

    }//run
      
    
    /**
     * This method creates a window frame and displays the BoxCatcher
     * game inside of it.  
     */
    public static void main(String[] args)
    {
        //Create a properly sized window for this program
        final JFrame myFrame = new JFrame();
        myFrame.setSize(WINDOW_WIDTH+10, WINDOW_HEIGHT+30);
        
        //Tell this window to close when someone presses the close button
        myFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                };
            });

        //Display a canvas in the window
        Canvas myCanvas = new Canvas();
        myFrame.getContentPane().add(myCanvas);

        //Hide the regular mouse cursor
        BufferedImage emptyImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor noCursor = Toolkit.getDefaultToolkit().createCustomCursor(emptyImage, new Point(0, 0), "no cursor");
        myFrame.getContentPane().setCursor(noCursor);

        //Run the game!
        BoxCatcher myBoxCatcherGame = new BoxCatcher(myCanvas);
        myBoxCatcherGame.init();
        myFrame.setVisible(true);
        myBoxCatcherGame.run();
        
    }//main
    
}//class BoxCatcher
