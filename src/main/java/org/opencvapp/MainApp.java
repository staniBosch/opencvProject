package org.opencvapp;

import org.apache.log4j.BasicConfigurator;
import org.opencvapp.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static final Logger log = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BasicConfigurator.configure();
		// PathsUtils.configure();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		log.info("App is starting");
		String fxmlFile = "/views/MainView.fxml";
		log.debug("Loading FXML for main view from: {}", fxmlFile);
		FXMLLoader loader = new FXMLLoader();
		BorderPane parent = (BorderPane) loader.load(getClass().getResourceAsStream(fxmlFile));

		MainController opc = loader.<MainController>getController();
		opc.setStage(stage);

		log.debug("Showing JFX scene");
		Scene scene = new Scene(parent, 1280, 720);
		scene.getStylesheets().add("/styles/styles.css");

		stage.setTitle("OpenCV Application");
		stage.setScene(scene);
		stage.show();
	}

}
