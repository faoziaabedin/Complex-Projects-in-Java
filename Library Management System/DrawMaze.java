// ******************************************************
// Do not change this code. We will make changes to this
// file for testing your submissions. Hence, if you rely
// on changes that you have made yourself, your  program
// might fail some of our tests.
// ******************************************************

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.*;
import java.lang.Thread;

public class DrawMaze extends JFrame{
  private Graphics display;    // Graphics object needed to draw
  private Board panel;         // Panel containing map
  private int numNodes;       // Number of nodes in the labyrinth
  private int labWidth;      // Width of the labyrinth (in rooms)
  private int labLength;     // Length of the labyrinth (in rooms)
  private int roomSize;              //Length of the room in pixels

  private char[][] lab;

  private Color rockColor = new Color(80,70,50);
  private Color wallColor = new Color(255,100,100);
  private Color pathColor = new Color(255,0,0);
  private Color entranceColor = new Color(100,100,100);
  private Color exitColor = new Color(100,255,100);
  private Color backColor = new Color(195,195,215);
  private Color white = new Color(255,255,255);
  private Color door = new Color(128,60,60);
  private Color openDoor = new Color(255,255,255);
  private Color black = new Color(0,0,0);
  private Color[] doorColors = new Color[10];
  
  private BufferedImage img, imgb, rock;
  private boolean imageFile = true;
  private int startx, starty, endx, endy;
  private Font font;
 
  /* ============================= */
  public DrawMaze(String labFile) {
  /* ============================= */
    BufferedReader input;
    String name = "";
    
    doorColors[0] = Color.MAGENTA;
    doorColors[1] = Color.BLUE;
    doorColors[2] = Color.PINK;
    doorColors[3] = new Color(80,225,80);
    doorColors[4] = Color.GRAY;
    doorColors[5] = new Color(50,200,200);
    doorColors[6] = new Color(220,160,220);
    doorColors[7] = new Color(200,100,100);
    doorColors[8] = new Color(50,100,50);
    doorColors[9] = new Color(100,100,255);  
	
    try {
      numNodes = 0;
      panel = new Board();
      getContentPane().add(panel);
      String line;
 
      input = new BufferedReader(new FileReader(labFile));

      // Process first four lines of the file
      roomSize = Integer.parseInt(input.readLine());
      labWidth = Integer.parseInt(input.readLine());
      labLength = Integer.parseInt(input.readLine());
      font = new Font("Serif", Font.PLAIN, roomSize/3);
      
      line = input.readLine();  /* Ignore line with number of available keys */
      int coins = Integer.parseInt(line);

      numNodes = labWidth * labLength;

      int off = 10;
      if (roomSize < 40) off = 20;
      setSize((2*labWidth)*roomSize+10,(2*labLength)*roomSize+roomSize+off);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setVisible(true);
      display = panel.getGraphics();

      Thread clock = new Thread();
      clock.sleep(2000);
      display.setColor(backColor);
      display.fillRect(0,0,(2*labWidth-1)*roomSize,(2*labLength-1)*roomSize);
      display.setColor(rockColor);
      display.drawRect(0,0,(2*labWidth-1)*roomSize,(2*labLength-1)*roomSize);
      display.drawRect(1,1,(2*labWidth-1)*roomSize-2,(2*labLength-1)*roomSize-2);
      display.drawRect(2,2,(2*labWidth-1)*roomSize-4,(2*labLength-1)*roomSize-4);
      display.drawRect(3,3,(2*labWidth-1)*roomSize-6,(2*labLength-1)*roomSize-6);
      
      display.setFont(font);
      name = "person.gif";
      img = ImageIO.read(new File("person.gif"));
      name = "personb.gif";
      imgb = ImageIO.read(new File("personb.gif"));
      name = "rock3.jpg";
      rock = ImageIO.read(new File("rock3.jpg"));
      drawMaze(input);
      
      Font largeFont = new Font("Serif",Font.PLAIN,roomSize/2);
      display.setFont(largeFont);
      display.setColor(black);
      int offset = 10;
      int offsety = 2;
      if (roomSize < 30) offsety = 0;
      if (coins == 0) display.drawString("No coins available",offset,2*labLength*roomSize+2);
      else
      	 display.drawString("Available coins : "+Integer.toString(coins),offset,2*labLength*roomSize+offsety);
    }
    catch (InterruptedException e) {
      System.out.println("Error starting program");
    }
    catch (IOException e) {
	imageFile = false;
	System.out.println("Cannot read image file: "+name);
    }
    catch (Exception e) {
      System.out.println("Error opening file "+labFile);
      labWidth = 1;
      labLength = 1;
      roomSize = 1;
    }
  }

 /* ================================================================= */
  public void drawMaze(BufferedReader input) {
  /* ================================================================= */
     int  i, row = 0, col;
     String line ="";

     // Open the file
     try {

       lab = new char[2*labLength-1][2*labWidth-1];
       // Read the labyrinth from the file
       for (;;) {
           line = input.readLine();
           if (line == null) {             // End of file
               input.close();
               return;
           }

           /* Draw a row of the labyrinth */
           for (i = 0, col = 0; i < line.length(); ++i) {
	       lab[row][i] = line.charAt(i);
	         switch (line.charAt(i)) {
                  case 's': drawStart(i,row); 
		                    ++col; break;
                  case 'x': drawExit(i,row); 
		                      ++col; break;
                  case 'w': drawRock(i,row); break;
                  case 'c': if ((row % 2) == 0) drawHorHall(i,row);
                            else drawVerHall(i,row);
                            break;
		   case 'o': ++col; break;
		   default: if (line.charAt(i) >= '0' && line.charAt(i) <= '9')
						if ((row % 2) ==0) drawHorDoor(i,row,String.valueOf(line.charAt(i)));
						else drawVertDoor(i,row,String.valueOf(line.charAt(i)));
					else System.out.println("Invalid input file. Invalid character: "+line.charAt(i));
                 }
	   }
           ++row; 

       }
     }
     catch (Exception e) {
        System.out.println("Error while processing this line of the input file:");
		System.out.println(line);
     }
   }


  /* Draw a vertical door */
  /* ================================================================= */
  private void drawVertDoor(int x, int y, String label) {
  /* ================================================================= */
      display.setColor(backColor);
      display.fillRect(x*roomSize,y*roomSize,roomSize,roomSize);
      display.setColor(doorColors[Integer.parseInt(label)]);
      display.fillRect(x*roomSize,y*roomSize+roomSize/4,roomSize,roomSize/2);
      display.setColor(white);
      display.drawString(label,x*roomSize+(int)(0.4*roomSize),(int)(y*roomSize+0.6*roomSize));

      display.setColor(rockColor);
      //display.fillRect(x*roomSize,y*roomSize,roomSize/5,roomSize);
      //display.fillRect(x*roomSize+4*roomSize/5,y*roomSize,roomSize/5,roomSize);

      display.drawImage(rock,x*roomSize,y*roomSize,x*roomSize+roomSize/5,
			(y+1)*roomSize,1,1,img.getWidth(),img.getHeight(),null);     
      display.drawImage(rock,x*roomSize+4*roomSize/5,y*roomSize,(x+1)*roomSize,
			(y+1)*roomSize,1,1,img.getWidth(),img.getHeight(),null);
  }

  /* Draw a harizontal door */
  /* ================================================================= */
  private void drawHorDoor(int x, int y, String label) {
  /* ================================================================= */
      display.setColor(backColor);
      display.fillRect(x*roomSize,y*roomSize,roomSize,roomSize);
      display.setColor(doorColors[Integer.parseInt(label)]);
      display.fillRect(x*roomSize+roomSize/4,y*roomSize,roomSize/2,roomSize);
      display.setColor(white);
      
      display.drawString(label,x*roomSize+(int)(0.4*roomSize),(int)(y*roomSize+0.6*roomSize));      

      display.setColor(rockColor);
      //display.fillRect(x*roomSize,y*roomSize,roomSize,roomSize/5);
      //display.fillRect(x*roomSize,y*roomSize+4*roomSize/5,roomSize,roomSize/5);

      display.drawImage(rock,x*roomSize,y*roomSize,(x+1)*roomSize,y*roomSize
			+roomSize/5,1,1,img.getWidth(),img.getHeight(),null);  
      display.drawImage(rock,x*roomSize,y*roomSize+4*roomSize/5,(x+1)*roomSize,
			(y+1)*roomSize,1,1,img.getWidth(),img.getHeight(),null);
  }

 /* Draw a horizontal passage */
 /* ================================================================= */
  private void drawHorHall(int x, int y) {
  /* ================================================================= */
     //display.drawImage(wallImage,x*roomSize,y*roomSize,roomSize,roomSize,this);
      display.setColor(backColor);
      display.fillRect(x*roomSize,y*roomSize,roomSize,roomSize);
      display.setColor(rockColor);

      //display.fillRect(x*roomSize,y*roomSize,roomSize,roomSize/5);
      //display.fillRect(x*roomSize,y*roomSize+4*roomSize/5,roomSize,roomSize/5);

      display.drawImage(rock,x*roomSize,y*roomSize,(x+1)*roomSize,y*roomSize
			+roomSize/5,1,1,img.getWidth(),img.getHeight(),null);  
      display.drawImage(rock,x*roomSize,y*roomSize+4*roomSize/5,(x+1)*roomSize,
			(y+1)*roomSize,1,1,img.getWidth(),img.getHeight(),null);
  }

 /* Draw a vertical passage */
 /* ================================================================= */
  private void drawVerHall(int x, int y) {
  /* ================================================================= */
     //display.drawImage(wallImage,x*roomSize,y*roomSize,roomSize,roomSize,this);
      display.setColor(backColor);
      display.fillRect(x*roomSize,y*roomSize,roomSize,roomSize);
      display.setColor(rockColor);
      //display.fillRect(x*roomSize,y*roomSize,roomSize/5,roomSize);
      //display.fillRect(x*roomSize+4*roomSize/5,y*roomSize,roomSize/5,roomSize);

      display.drawImage(rock,x*roomSize,y*roomSize,x*roomSize+roomSize/5,
			(y+1)*roomSize,1,1,img.getWidth(),img.getHeight(),null);         
      display.drawImage(rock,x*roomSize+4*roomSize/5,y*roomSize,(x+1)*roomSize,
			(y+1)*roomSize,1,1,img.getWidth(),img.getHeight(),null);
  }


 /* ================================================================= */
  private void drawRock(int x, int y) {
  /* ================================================================= */

      display.drawImage(rock,x*roomSize,y*roomSize,(x+1)*roomSize,
			(y+1)*roomSize,1,1,img.getWidth(),img.getHeight(),null);
      //display.setColor(rockColor);
      //display.fillRect(x*roomSize,y*roomSize,roomSize,roomSize);
  }


  /* Draw the entrance room */
  /* ================================================================= */
  private void drawStart(int x, int y) {
  /* ================================================================= */
    startx = x;
    starty = y;

    display.setColor(entranceColor);
    display.fillRect(x*roomSize+2*roomSize/5,y*roomSize+roomSize/5,
		     2*roomSize/5,3*roomSize/5);
    int[] xcoor = {x*roomSize+2*roomSize/5, x*roomSize+roomSize/5,
		   x*roomSize+roomSize/5, x*roomSize+2*roomSize/5};
    int[] ycoor = {y*roomSize+roomSize/5, y*roomSize+roomSize/10,
		   y*roomSize+7*roomSize/10,y*roomSize+4*roomSize/5};
    display.setColor(door);
    display.fillPolygon(xcoor,ycoor,4);
    display.setColor(wallColor);
    display.fillOval(x*roomSize+2*roomSize/10,y*roomSize+4*roomSize/10,
             roomSize/10,roomSize/10);
  }

  /* Draw the exit */
  /* ================================================================= */
  private void drawExit(int x, int y) {
  /* ================================================================= */
    endx = x;
    endy = y;

    display.setColor(exitColor);
    display.fillRect(x*roomSize+2*roomSize/5,y*roomSize+roomSize/5,
		     2*roomSize/5,3*roomSize/5);
    int[] xcoor = {x*roomSize+2*roomSize/5, x*roomSize+roomSize/5,
		   x*roomSize+roomSize/5, x*roomSize+2*roomSize/5};
    int[] ycoor = {y*roomSize+roomSize/5, y*roomSize+roomSize/10,
		   y*roomSize+7*roomSize/10,y*roomSize+4*roomSize/5};
    display.setColor(door);
    display.fillPolygon(xcoor,ycoor,4);
    display.setColor(wallColor);
    display.fillOval(x*roomSize+2*roomSize/10,y*roomSize+4*roomSize/10,
             roomSize/10,roomSize/10);  
  }

  /* ========================================================== */
  private void erasePerson(int x, int y) {
  /* ========================================================== */
      if ((x == (startx * roomSize)) && (y == (starty * roomSize))) drawStart(startx,starty);
      else {
        display.setColor(backColor);
        display.fillRect(x,y,roomSize,roomSize);
      }
  }

  /* ========================================================== */
    private void drawPerson(int x, int y,boolean right) {
  /* ========================================================== */
    	int offset = roomSize/5;

    	if ((x == (endx * roomSize)) && (y == (endy * roomSize))) {
    		if (right)
    			display.drawImage(img,x+2*roomSize/5,y+roomSize/5,x+4*roomSize/5,
    					y+4*roomSize/5,1,1,img.getWidth(),img.getHeight(),null);
    		else 
    			display.drawImage(imgb,x+2*roomSize/5,y+roomSize/5,x+4*roomSize/5,
    					y+4*roomSize/5,1,1,img.getWidth(),img.getHeight(),null);
    	}
    	else 
    		if (right)
    			display.drawImage(img,x+offset,y+offset,x+roomSize-offset,
    					y+roomSize-offset,1,1,img.getWidth(),img.getHeight(),backColor,null);
    		else 
    			display.drawImage(imgb,x+offset,y+offset,x+roomSize-offset,
    					y+roomSize-offset,1,1,img.getWidth(),img.getHeight(),backColor,null);
    }


  /* Draws an edge of the solution */
  /* ========================================================== */
  private void drawEdge(int u, int v) {
  /* ========================================================== */
  /* Input: edge (uv) */
   
      int x,  y, us, vs, width, height, offset, labx, laby;

     if (u > numNodes || v > numNodes) {
        System.out.println("Invalid edge ("+u+","+v+")");
        return;
     }
     offset = roomSize / 2 - 1;
     us = u;
     vs = v;
     if (u > v) {x = u; u = v; v = x;}

     x = (u % labWidth)*2*roomSize + offset;
     y = (u / labWidth)*2*roomSize + offset;

     if (imageFile) erasePerson((us % labWidth)*2*roomSize,(us / labWidth)*2*roomSize);

     if (v == (u+1)) { 
       width = roomSize*2;
       height = 3;
       labx = 2*(u % labWidth)+1;
       laby = 2*(u / labWidth);
       if (Character.isDigit(lab[laby][labx])) 
	       drawOpenHorizDoor(labx,laby,Character.toString(lab[laby][labx]));
     }
     else if (v == (u+labWidth)) {
       width = 3;
       height = roomSize*2;
       labx = 2*(u % labWidth);
       laby = 2*(u / labWidth)+1;
       if (Character.isDigit(lab[laby][labx]) )
	        drawOpenVertDoor(labx,laby,Character.toString(lab[laby][labx]));
     }
     else {
        System.out.println("Invalid edge ("+u+","+v+")");
        return;
     }

     display.setColor(pathColor);
     display.fillRect(x,y,width,height);

     if (imageFile) 
	 if ((us % labWidth) <= (vs % labWidth))
	     drawPerson((vs % labWidth)*2*roomSize,(vs / labWidth)*2*roomSize,true);
	 else drawPerson((vs % labWidth)*2*roomSize,(vs / labWidth)*2*roomSize,false);

  }

   /* Draws an open horizontal door */
   /* ========================================================== */
   private void drawOpenHorizDoor(int x, int y,String label) {
   /* ========================================================== */   
      display.setColor(openDoor);
      display.fillRect(x*roomSize+roomSize/4,y*roomSize+roomSize/5,roomSize/2,3*roomSize/5);
      display.setColor(black);
      display.drawString(label,x*roomSize+(int)(0.4*roomSize),(int)(y*roomSize+0.6*roomSize));         
   }
   
   /* Draws an open vertical door */
   /* ========================================================== */
   private void drawOpenVertDoor(int x, int y,String label) {
   /* ========================================================== */  
      display.setColor(openDoor);      
      display.fillRect(x*roomSize+roomSize/5,y*roomSize+roomSize/4,3*roomSize/5,roomSize/2);
      display.setColor(black);
      display.drawString(label,x*roomSize+(int)(0.6*roomSize),(int)(y*roomSize+0.6*roomSize));
}
   
  /* ==================================== */
  public void drawEdge (GraphNode u, GraphNode v) {
  /* ==================================== */
      if (u != v) drawEdge(u.getName(),v.getName());
  }
}
