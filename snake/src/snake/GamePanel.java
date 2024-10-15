package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	// Screen size
	static final int SCREEN_WITH = 600;
	static final int SCREEN_HEIGHT = 600;
	// One part of the screen
	static final int UNIT_SIZE = 25;
	// The number of pieces there are
	static final int GAME_UNITS = (SCREEN_WITH * SCREEN_HEIGHT) / UNIT_SIZE;
	// Speed(the smaller it is,the faster it is)
	static int DELAY = 150;
	// Array(final= the value cannot be changed)
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;

	

	GamePanel() {
		random = new Random();
		// Defines a measure
		this.setPreferredSize(new Dimension(SCREEN_WITH, SCREEN_HEIGHT));
		// Defines a color
		this.setBackground(Color.black);
		// Defines that the program can receive keybord events
		this.setFocusable(true);
		// After the screen is ready i tell the program to start from the "startGame"
		// function
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		// how often is the game update , this = my game
		timer = new Timer(DELAY, this);
		timer.start();
	}

	// the function is designed to handle drawing processes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	// Function of graphics
	public void draw(Graphics g) {
		if (running) {
			/*
			 * Exanple = g.drawLine(x1= starting point , y1= starting point, x2= end point ,
			 * y2 = end point) for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
			 * g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); g.drawLine(0, i *
			 * UNIT_SIZE, SCREEN_WITH, i * UNIT_SIZE); }
			 */
			// Dye the apple red
			g.setColor(Color.red);
			// Choice of location and size and shape of circle
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			// Coloring the snake
			for (int i = 0; i < bodyParts; i++) {
				// If it is the snakes head
				if (i == 0) {
					// Paint it green
					g.setColor(Color.green);
					// Draw the head in green color in the shape of a rectangle
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			// color red
			g.setColor(Color.red);
			// Paint the title in red, "Ink Free" = type of writing , Font.BOLD, 40 =
			// thickness
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			// Gets the font we wrote
			FontMetrics metrics = getFontMetrics(g.getFont());
			// Divides the screen into two that wes in the middle
			g.drawString("Score: " + applesEaten, (SCREEN_WITH - metrics.stringWidth("Score: " + applesEaten)) / 2,
					g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}

	public void newApple() {
		// Generate a random number between 0 and 23 for me multiply the result by 25
		// Example 3 * 25 = 75
		appleX = random.nextInt((int) (SCREEN_WITH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void move() {
		// Moving forward, according to a section will change to the position of the
		// previous section
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
		case 'U': {
			// Since Y is the axis of the height , I subtract one UNIT SIZE from it
			y[0] = y[0] - UNIT_SIZE;
			break;
		}
		case 'D': {
			y[0] = y[0] + UNIT_SIZE;
			break;
		}
		case 'L': {
			x[0] = x[0] - UNIT_SIZE;
			break;
		}
		case 'R': {
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		// This is only in case there was an unexpected value , but here it is not
		// relevant
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}

	}

	public void checkApple() {
		// If the guesser's head meets apple
		if ((x[0] == appleX) && (y[0] == appleY)) {
			// The snake lengthens
			bodyParts++;
			// The amount of apples increased
			applesEaten++;
			newApple();
			DELAY -= 10;
			timer.stop();
	        timer = new Timer(DELAY, this);
	        timer.start();
		}
	}

	// A function that checks for conflicts
	public void checkCollisions() {
		for (int i = bodyParts; i > 0; i--) {
			// Does the head collide with any part the body
			if (x[0] == x[i] && y[0] == y[i]) {
				// Exit
				running = false;
			}
			// check if head touches left border
			if (x[0] < 0) {
				running = false;
			}
			// check if head touches right border
			if (x[0] > SCREEN_WITH) {
				running = false;
			}
			// check if head touches top border
			if (y[0] < 0) {
				running = false;
			}
			// check if head touches bottom border
			if (y[0] > SCREEN_HEIGHT) {
				running = false;
			}
			if (!running)
				timer.start();
		}
	}

	public void gameOver(Graphics g) {
		// Score
		// Stays the same as a game
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WITH - metrics1.stringWidth("Score: " + applesEaten)) / 2,
				g.getFont().getSize());

		// game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		// The middle of the screen both in height and in width
		g.drawString("Game Over", (SCREEN_WITH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();

	}

	// We created a class called MyKeyAdapter and it inherits class KeyAdapter which
	// is a built-in class
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			// To resort should update the direction
			// Example : want to turn left only if it is changed to right, only then it is
			// updated to the left
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;

			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}

		}

	}
}
