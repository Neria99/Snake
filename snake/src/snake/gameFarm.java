package snake;

import javax.swing.JFrame;

public class gameFarm extends JFrame{
   gameFarm(){
	   //Adds a window
	   this.add(new GamePanel());
	   //Gives a title
	   this.setTitle("Snake");
	   //Option to close the program
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   //Does not let the user expand the window
	   this.setResizable(false);
	   //Gives you automtic size according to the data you need
	   this.pack();
	   //Presents you with a graphical interface
	   this.setVisible(true);
	   // Show you the window in the center
	   this.setLocationRelativeTo(null);
   }
}
