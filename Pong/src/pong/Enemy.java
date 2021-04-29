package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy 
{
	//Positions
	private double xPos;
	private double  yPos;
	
	//Size
	private int width;
	private int height;
	
	public Enemy(int xPos, int yPos)
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
	
	/*
	 * UPDATE
	 */
	public void Tick()
	{
		xPos += (Game.ball.GetXPos() - xPos - 6) * 0.1;
		
		if(xPos + width >= Game.WIDTH) 
		{
			xPos = Game.WIDTH - width;
		}
		else if(xPos <= 0) 
		{
			xPos = 0;
		}
	}
	
	/*
	 * DRAW
	 */
	public void Render(Graphics graph)
	{
		graph.setColor(Color.RED);
		graph.fillRect((int)xPos, (int)yPos, width, height);
	}
}
