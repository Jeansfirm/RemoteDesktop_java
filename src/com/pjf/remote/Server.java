package com.pjf.remote;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.image.codec.jpeg.*;

public class Server {
	public static void main(String[] args)throws Exception
	{
		ServerSocket serverSocket=new ServerSocket(1314);
		while (true) {
			System.out.println("Waitting for client connection...");
			Socket client = serverSocket.accept(); //blocked function
			System.out.println("New Client connected!");
			ImgThread imgThread=new ImgThread(new DataOutputStream(client.getOutputStream()));
			imgThread.start();
			EventThread eventThread=new EventThread(new ObjectInputStream(client.getInputStream()));
			eventThread.start();
		}
	}
}

class ImgThread extends Thread{
	private DataOutputStream dataOut;
	
	public ImgThread(DataOutputStream dataOut) {
		// TODO Auto-generated constructor stub
		this.dataOut=dataOut;		
	}
	@Override
	public void run(){
		try{
			Robot robot = new Robot();
			Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
			dimension.setSize(dimension.getWidth(), dimension.getHeight()-0);
			Rectangle rect = new Rectangle(dimension);
			BufferedImage bufImg;
			byte[] imgBytes;
			while(true)
			{
				bufImg = robot.createScreenCapture(rect);
				imgBytes=getImgBytes(bufImg);
				dataOut.writeInt(imgBytes.length);
				//dataOut.writeDouble(dimension.getHeight());
				//dataOut.writeDouble(dimension.getWidth());
				dataOut.write(imgBytes);
				dataOut.flush();
				Thread.sleep(100);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public byte[] getImgBytes(BufferedImage bufImg) throws ImageFormatException, IOException
	{
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(baos);
		encoder.encode(bufImg);
		return baos.toByteArray();
	}	
}

class EventThread extends Thread{
	private ObjectInputStream objIn;
	private Robot robot;
	
	public EventThread(ObjectInputStream objIn) {
		// TODO Auto-generated constructor stub
		this.objIn=objIn;
	}
	public void run()
	{
		try {
			robot=new Robot();
			InputEvent event;
			while(true)
			{
				event=(InputEvent)objIn.readObject();
				doEvent(event);
			}
		} catch (AWTException | ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doEvent(InputEvent event){
		if(event instanceof MouseEvent)
		{
			MouseEvent me=(MouseEvent)event;
			int type=me.getID();
			if(type==Event.MOUSE_UP)
			{
				robot.mouseRelease(getButton(me.getButton()));
			}else if(type==Event.MOUSE_DOWN)
			{
				robot.mousePress(getButton(me.getButton()));
			}else if(type==Event.MOUSE_MOVE)
			{
				robot.mouseMove(me.getX(), me.getY());
			}else if(type==Event.MOUSE_DRAG)
			{
				robot.mouseMove(me.getX(), me.getY());
			}
		}else if(event instanceof KeyEvent)
		{
			KeyEvent ke=(KeyEvent)event;
			int type=ke.getID();
			if(type==Event.KEY_PRESS)
			{
				robot.keyPress(ke.getKeyCode());
			}else if(type==Event.KEY_RELEASE)
			{
				robot.keyRelease(ke.getKeyCode());
			}
		}
	}
	public int getButton(int button)
	{
		if(button==MouseEvent.BUTTON1){
			return InputEvent.BUTTON1_MASK;
		}else if(button==MouseEvent.BUTTON2){
			return InputEvent.BUTTON2_MASK;
		}else if(button==MouseEvent.BUTTON3){
			return InputEvent.BUTTON3_MASK;
		};
		return -1;
	}
}