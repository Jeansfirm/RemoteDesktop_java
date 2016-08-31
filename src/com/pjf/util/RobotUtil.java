package com.pjf.util;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * Robot¿‡—ßœ∞
 * @author p
 *
 */
public class RobotUtil {
	public static void main(String[] args) throws Exception 
	{
		Robot robot=new Robot();
		robot.mouseMove(60, 60);
		//robot.delay(2000);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
