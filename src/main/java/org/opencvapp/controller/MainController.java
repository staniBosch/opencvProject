package org.opencvapp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController implements Initializable {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@FXML
	BorderPane mainPane;

	private BorderPane ImageOpenCVView;
	private BorderPane ImageSeqOpenCVView;
	private Stage stage;

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
	public void openSeqImageView() throws IOException {
		if (this.ImageSeqOpenCVView == null) {
			log.info("Open ImageView");
			String fxmlFile = "/views/SeqImageOpenCVView.fxml";
			log.debug("Loading FXML for main view from: {}", fxmlFile);
			FXMLLoader loader = new FXMLLoader();
			this.ImageSeqOpenCVView = (BorderPane) loader.load(getClass().getResourceAsStream(fxmlFile));

			OpenCVSeqImageViewController opc = loader.<OpenCVSeqImageViewController>getController();
			opc.setStage(stage);
		}
		this.mainPane.setCenter(this.ImageSeqOpenCVView);
	}

	/**
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	public void openVideoView() {

	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			openSeqImageView();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
