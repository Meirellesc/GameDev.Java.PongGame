package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Player 
{
	//Actions
	public boolean right;
	public boolean left;
	
	//Positions
	private double xPos;
	private double yPos;
	private double speed = 1.2;
	
	//Size
	private int width;
	private int height;
	
	public Player(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = 30;
		this.height = 5;
	}
	
	public double GetXPos()
	{
		return this.xPos;
	}
	public double GetYPos()
	{
		return this.yPos;
	}
	public int GetHeight()
	{
		return this.height;
	}
	public int GetWidth()
	{
		return this.width;
	}
	
	public void Tick()
	{
		if(right)
		{
			xPos += speed;
		}
		else if(left)
		{
			xPos -= speed;
		}
		
		if(xPos + width >= Game.WIDTH) 
		{
			xPos = Game.WIDTH - width;
		}
		else if(xPos <= 0) 
		{
			xPos = 0;
		}
	}
	
	public void Render(Graphics graph)
	{
		graph.setColor(Color.ORANGE);
		graph.fillRect((int)xPos, (int)yPos, width, height);
	}
}
