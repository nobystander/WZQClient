package com.wzq.client.view;

import com.wzq.client.*;

import java.util.Vector;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.event.EventHandler;

public class BoardController {

	@FXML
	private AnchorPane back;
	@FXML
	private GridPane board;
	@FXML
	private ImageView imageview ;

	private MainApp mainApp;

	public final int N = 15;
	public final double cRad = 16;
	public final double sLens = 550./14;

	public Vector<Circle> chesses = new Vector<Circle>();
	public boolean moveable,clickable;
	public int count = 0;
	
	@FXML
	private void initialize() {
		clickable = moveable = false;

		//board.setGridLinesVisible(true);
		board.setPrefWidth(sLens*N);
		board.setPrefHeight(sLens*N);
		board.setLayoutX(25 - sLens/2);
		board.setLayoutY(29 - sLens/2);
		
		for(int i=0;i<N;++i){
			ColumnConstraints col = new ColumnConstraints();
			col.setPrefWidth(sLens);
			col.setHalignment(HPos.CENTER);
			if(i == 0)
				board.getColumnConstraints().set(0, col);
			else
				board.getColumnConstraints().add(col);
		}
		for(int i=0;i<N;++i){
			RowConstraints row = new RowConstraints();
			row.setPrefHeight(sLens);
			row.setValignment(VPos.CENTER);
			if(i == 0)
				board.getRowConstraints().set(0, row);
			else
				board.getRowConstraints().add(row);
		}
		
		board.setOnMouseMoved(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {

				if(!moveable) {
					imageview.setVisible(false);
					return ;
				}

				double x = e.getX(),y = e.getY();
				if(x > board.getLayoutX() && x < board.getLayoutX()+board.getWidth() &&
				   y > board.getLayoutY() && y < board.getLayoutY()+board.getHeight()){
					x = (x - board.getLayoutX())/sLens;
					y = (y - board.getLayoutY())/sLens;
					imageview.setVisible(true);
					
					imageview.setX(board.getLayoutX() + sLens*(int)x);
					imageview.setY(board.getLayoutY() + sLens*(int)y);
				}
			}

		});
		
		imageview.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent e) {
				
				if(!clickable){
					return ;
				}

				double x = e.getX(),y = e.getY();
				if(x > board.getLayoutX() && x < board.getLayoutX()+board.getWidth() &&
				   y > board.getLayoutY() && y < board.getLayoutY()+board.getHeight()){
					x = (x - board.getLayoutX())/sLens;
					y = (y - board.getLayoutY())/sLens;
					System.out.println((int)x +" "+ (int)y + " " + Game.get().color);
					
					if( !Game.get().put((int)x,(int)y,Game.get().color) ) return ;

					Circle chess = new Circle(cRad,Game.get().color==1?Color.WHITE:Color.BLACK);
					board.add(chess, (int)x, (int)y);
					chesses.add(chess);

					moveable = false;
					clickable = false;
				}
			}
		});

	}

	public void putChess(int x,int y,int c){
		System.out.println(x + " "+ y + " " + c);
		Circle chess = new Circle(cRad,c==1?Color.WHITE:Color.BLACK);
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				board.add(chess,(int)x,(int)y);
			}
		});
		chesses.add(chess);
		moveable = true;
		clickable = true;
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
}
