package org.opencvapp.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@FXML
	BorderPane mainPane;
	private Stage stage;

	private BorderPane ImageOpenCVView;
	private BorderPane ImageSeqOpenCVView;
	private BorderPane VideoOpenCVView;

	public MainController() {
		super();
	}

	public MainController(Stage stage) throws IOException {
		this.stage = stage;
		String fxmlFile = "/views/MainView.fxml";
		log.debug("Loading FXML for main view from: {}", fxmlFile);
		FXMLLoader loader = new FXMLLoader();
		BorderPane parent = (BorderPane) loader.load(getClass().getResourceAsStream(fxmlFile));

		log.debug("Showing JFX scene");
		Scene scene = new Scene(parent, 600, 400);
		scene.getStylesheets().add("/styles/styles.css");

		stage.setTitle("OpenCV Application");
		stage.setScene(scene);
		stage.show();
	}

	public void init() throws IOException {

	}

	@FXML
	public void openImageView() {
		if (this.ImageOpenCVView == null) {
			try {
				log.info("Open ImageView");
				String fxmlFile = "/views/ImageOpenCVView.fxml";
				log.debug("Loading FXML for main view from: {}", fxmlFile);
				FXMLLoader loader = new FXMLLoader();
				this.ImageOpenCVView = (BorderPane) loader.load(getClass().getResourceAsStream(fxmlFile));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.mainPane.setCenter(this.ImageOpenCVView);
	}

	@FXML
	public void openSeqImageView() {
		if (this.ImageSeqOpenCVView == null) {
			try {
				log.info("Open ImageView");
				String fxmlFile = "/views/SeqImageOpenCVView.fxml";
				log.debug("Loading FXML for main view from: {}", fxmlFile);
				FXMLLoader loader = new FXMLLoader();
				this.ImageSeqOpenCVView = (BorderPane) loader.load(getClass().getResourceAsStream(fxmlFile));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.mainPane.setCenter(this.ImageSeqOpenCVView);
	}

	@FXML
	public void openVideoView() {

	}

}
