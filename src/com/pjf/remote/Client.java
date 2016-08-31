package com.pjf.remote;


import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Client {
	public static void main(String [] args) throws Exception{
		Socket client=new Socket("localhost",1314);
		DataInputStream dataIn=new DataInputStream(client.getInputStream());
		ClientUI clientUI=new ClientUI(new ObjectOutputStream(client.getOutputStream()));
		//clientUI.setSize((int)dataIn.readDouble(), (int)dataIn.readDouble());
		byte[] buf;
		while (true){
			buf=new byte[dataIn.readInt()];
			dataIn.readFully(buf);
			clientUI.setImg(buf);
		}
	}
}

class ClientUI extends JFrame{
	public JLabel backgroundImg;
	private ObjectOutputStream objOut;
	
	public ClientUI(ObjectOutputStream objOut){
		this.objOut=objOut;
		this.setTitle("Remote Controller");
		this.setSize(1027, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		backgroundImg = new JLabel();
		JPanel panel=new JPanel();
		JScrollPane scrollPane=new JScrollPane(panel);
		panel.add(backgroundImg);
		this.add(scrollPane);
		
		backgroundImg.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
		});
		backgroundImg.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
		});
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				sendEventObject(e);
			}
		});
		
	}
	
	public void setImg(byte[] img)
	{
		backgroundImg.setIcon(new ImageIcon(img));
	}
	public void sendEventObject(InputEvent event)
	{
		try {
			objOut.writeObject(event);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}