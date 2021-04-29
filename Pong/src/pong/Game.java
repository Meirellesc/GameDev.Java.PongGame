package pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener
{
	//<------------------->
	//<-----VARIABLES----->
	//<------------------->
		
	
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	
	protected static int WIDTH = 160;
	protected static int HEIGHT = 120;
	protected static int SCALE = 3;
	
	/*
	 * Thread - Game Loop
	 */
	private Thread thread;
	private boolean isRunning;
	
	//Frame
	private static JFrame jFrame;
	private BufferedImage layer;
	
	/*
	 * Player
	 */
	static Player player;
	
	/*
	 * Enemy
	 */
	static Enemy enemy;
	
	/*
	 * Ball
	 */
	static Ball ball;
	
	//<----------------->
	//<-----METHODS----->
	//<----------------->
	
	/*
	 * Constructor
	 */
	public Game()
	{
		//Set the resolution
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		//Initialize the frame (window of the game)
		InitFrame(this);
			
		//Initialize the image to be buffered into the game
		layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
				
		//Add the key listener
		this.addKeyListener(this);
		
		//Initialize Player
		player = new Player(WIDTH/2-20, HEIGHT-5);
		
		//Initialize Player
		enemy = new Enemy(WIDTH/2-20, 0);
		
		//Initialize Player
		ball = new Ball(WIDTH/2-20, HEIGHT/2 - 1);
	}
	
	/*
	 * Frame (Game's window)
	 */
	public static void InitFrame(Component game)
	{
		jFrame = new JFrame("Pong");
		jFrame.add(game);
		jFrame.pack();
		
		jFrame.setResizable(false);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}
	
	/*
	 * Thread - Start the game loop 
	 */
	private synchronized void Start()
	{
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	private synchronized void Stop()
	{
		try 
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void Reset()
	{
		new Game();
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.Start();
	}
	
	public void Tick()
	{
		player.Tick();
		enemy.Tick();
		ball.Tick();
	}
	
	public void Render()
	{
		//Sequence of buffers into screen to optimize the rendering 
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics graph = layer.getGraphics();
		
		//Background
		graph.setColor(Color.black);
		graph.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		//Render player
		player.Render(graph);
		
		//Render enemy
		enemy.Render(graph);
		
		//Render ball
		ball.Render(graph);
		
		graph = bs.getDrawGraphics();
		graph.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null); 
		graph.dispose();
		bs.show();
	}

	@Override
	public void run() 
	{
		//Last Time
		long lastTime = System.nanoTime();
		
		//Quantity of Frames
		double amountOfTicks = 60.0;
		
		//1 [nanoSecond] per 60 [ticks]
		double nst = 1000000000 / amountOfTicks;
		
		//Interval
		double delta = 0;
		
		//Game Loop
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nst;
			lastTime = now;
			
			//Update
			if(delta >= 1)
			{
				Tick();
				Render();
				delta--;
			}
		}
		
		Stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			//System.out.println("Right button pressed");
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			//System.out.println("Left button pressed");
			player.left = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			//System.out.println("Right button released");
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			//System.out.println("Left button released");
			player.left = false;
		}
		
	}

}
