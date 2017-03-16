package com.wzq.client;

import com.wzq.client.view.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private RootLayoutController rootlayoutcontroller;
    private BoardController boardcontroller;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Îå×ÓÆå");
        this.primaryStage.setResizable(false);

        initRootLayout();
        showPersonOverview();
        Game.get().init(boardcontroller);
        try {
			Thread.sleep(1*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        rootlayoutcontroller.setlocalIP(Game.get().localIP);
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            System.out.println(MainApp.class.getResource("view/RootLayout.fxml").toString());
            rootLayout = (BorderPane) loader.load();

            rootlayoutcontroller = loader.getController();
            rootlayoutcontroller.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Board.fxml"));

            System.out.println(MainApp.class.getResource("view/Board.fxml"));

            AnchorPane board = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setLeft(board);
            
            boardcontroller = loader.getController();
            boardcontroller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}