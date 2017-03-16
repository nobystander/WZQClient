package com.wzq.client.view;

import com.wzq.client.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class RootLayoutController {
	
	@FXML
	private Button login;
	@FXML
	private Button challange;
	@FXML
	private TextField textIP; 
	@FXML
	private Label remoteIP;
	@FXML
	private Label localIP;

	private MainApp mainApp;

	@FXML
	private void initialize() {
		login.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				Game.get().status = 0;
			}
		});
		
		challange.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				String ip = textIP.getText().toString().trim();
				if(ip != ""){
					Game.get().status = 1;
					Game.remoteIP = ip;
					setremoteIP(ip);
				}
			}
		});
	}
	
	public void setremoteIP(String s){
		this.remoteIP.setText(s);
	}

	public void setlocalIP(String s){
		this.localIP.setText(s);
	}

	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
}
