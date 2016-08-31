package com.pjf.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ScreenUtil {
	public static void main(String[] args)throws Exception
	{
		Robot robot = new Robot();	
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		dimension.setSize(dimension.getWidth(), dimension.getHeight()-18);
		Rectangle rect = new Rectangle(dimension);
		BufferedImage image=robot.createScreenCapture(rect);
		JFrame jframe = new JFrame();
		jframe.setVisible(true);
		jframe.setSize(400, 400);
		jframe.setLocationRelativeTo(null);
		JLabel backImg = new JLabel();		
		jframe.add(backImg);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while(true)
		{
			image=robot.createScreenCapture(rect);
			backImg.setIcon(new ImageIcon(image));
			Thread.sleep(100);
		}
	}
}
