package org.opencvapp;


import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application{
	
	private static final Logger log = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		log.info("App is starting");
		String fxmlFile = "/views/MainView.fxml";
		log.debug("Loading FXML for main view from: {}", fxmlFile);
		FXMLLoader loader = new FXMLLoader();
		BorderPane rootNode = (BorderPane) loader.load(getClass().getResourceAsStream(fxmlFile));

		log.debug("Showing JFX scene");
		Scene scene = new Scene(rootNode);
		scene.getStylesheets().add("/styles/styles.css");

		primaryStage.setTitle("OpenCV Application");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
