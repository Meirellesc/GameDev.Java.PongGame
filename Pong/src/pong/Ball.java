package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball 
{
	//Positions
	private double xPos;
	private double  yPos;
	
	//Movements
	private double dx;
	private double dy;
	private double speed = 1.3;
	
	//Size
	private int width;
	private int height;
	
	/*
	 * Constructor
	 */
	public Ball(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = 4;
		this.height = 4;
		
		this.dx = new Random().nextGaussian();
		this.dy = new Random().nextGaussian();
	}
	
	public double GetXPos()
	{
		return this.xPos;
	}
	
	/*
	 * UPDATE
	 */
	public void Tick()
	{
		//Update positions
		xPos += dx * speed;
		yPos += dy * speed;
		
		//Detect collisions
		if(xPos+(dx * speed) + width >= Game.WIDTH || xPos <= 0)
		{
			dx *= -1;
		}
		
		if(yPos + height > Game.HEIGHT)
		{
			//Enemy's Score
			System.out.println("Enemy's Score");
			return;
		}
		else if(yPos < 0)
		{
			//Player's Score
			System.out.println("Player's Score");
			return;
		}
		
		//Ball's bounds
		Rectangle ballBounds = new Rectangle((int)(xPos+(dx*speed)),(int)(yPos+(dy*speed)),width,height);
		
		//Player's bounds
		Rectangle playerBounds = new Rectangle((int)(Game.player.GetXPos()),(int)(Game.player.GetYPos()),Game.player.GetWidth(),Game.player.GetHeight());
		
		//Enemys's bounds
		Rectangle enemyBounds = new Rectangle((int)(Game.enemy.GetXPos()),(int)(Game.enemy.GetYPos()),Game.enemy.GetWidth(),Game.enemy.GetHeight());
		
		//Detect collisions with player or enemy
		if(ballBounds.intersects(playerBounds) || ballBounds.intersects(enemyBounds))
		{
			dy *= -1;
		}
	}
	
	/*
	 * DRAW
	 */
	public void Render(Graphics graph)
	{
		graph.setColor(Color.YELLOW);
		graph.fillRect((int)xPos, (int)yPos, width, height);
	}
}
