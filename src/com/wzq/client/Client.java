package com.wzq.client;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Client implements Runnable{
	
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private String ip;
	private int port;
	private int status = -1;
	
	public Client(String ip,int port){
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			socket = new Socket(ip,port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			Game.get().localIP = socket.getLocalAddress().toString();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			while(true){
				Thread.sleep(20);
				if(status == -1){ 	//准备联机
					if(Game.get().status == -1) continue;
					if(Game.get().status == 0){
						String s = null;
						s = br.readLine();
						System.out.println("OK "+s);
						if(s != null){
							String[] ss = s.split(" "); 
							if(ss[0].equals("L")){
								Game.get().remoteIP = ss[1];
								Game.get().color = status = 0;
								Game.get().boardcontroller.clickable = true;
								Game.get().boardcontroller.moveable = true;
								out.write("S\n".getBytes());
								System.out.println("link S");
							}
							else{
								out.write("F\n".getBytes());
								System.out.println("link F");
							}
							Game.get().status = -1;
						}
						continue;
					}
					if(Game.get().status == 1){
						if(Game.get().remoteIP == "") continue;
						out.write(("L "+Game.get().remoteIP+"\n").getBytes());
						String s = null;
						s = br.readLine();
						if(s != null){
							String[] ss = s.split(" ");
							if(ss[0].equals("L")){
								Game.get().color = status = 1;
								out.write("S\n".getBytes());
								System.out.println("link S");
							}
							else{
								out.write("F\n".getBytes());
								System.out.println("link F");
							}
							Game.get().status = -1;
						}
						continue;
					}
				}else if(status == 0){
					while(Game.get().x == -1) Thread.sleep(20);
					String message = "P " + Game.get().x + " " +Game.get().y + " " 
										  + Game.get().color + "\n";
					out.write(message.getBytes());
					System.out.println("put S");
					status = 1;
					if(Game.get().JudgeWin(Game.get().x, Game.get().y, Game.get().color)){
								Platform.runLater(new Runnable(){
									@Override
									public void run() {
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Result");
										alert.setHeaderText(null);
										alert.setContentText("You Are Win!");
										alert.showAndWait();
									}
								});
						break;
					}
					Game.get().x = Game.get().y = -1;
				}else if(status == 1){
					String s = null;
					s = br.readLine();
					System.out.println("OK "+s);
					if(s != null){
						String[] ss = s.split(" ");
						if(ss[0].equals("P")){
							Integer x = new Integer(ss[1]),y = new Integer(ss[2]),
									color = new Integer(ss[3]);
							Game.get().put(x,y,color);
							System.out.println("put S");
							status = 0;
							if(Game.get().JudgeWin(x, y, color)){
								Platform.runLater(new Runnable(){

									@Override
									public void run() {
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Result");
										alert.setHeaderText(null);
										alert.setContentText("You Are Lose!");
										alert.showAndWait();
									}
								});

								System.out.println("You Are Lose!");
								break;
							}
						}
					}
					continue;
				}
			}
			System.out.println("Game Over");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}