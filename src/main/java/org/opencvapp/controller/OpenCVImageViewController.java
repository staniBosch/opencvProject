package org.opencvapp.controller;

import java.io.File;

import org.opencvapp.utils.ImageFileChooser;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class OpenCVImageViewController {

	@FXML 
	ImageView ImageViewSrc;
	@FXML
	ImageView ImageViewDst;
	@FXML
	ChoiceBox<Object> CBoxFilter;
	
	private Stage stage;
	
	public OpenCVImageViewController() {
		init();
	}
	
		public OpenCVImageViewController(Stage stage) {
			this.stage = stage;
		}
		
		@FXML
		public void loadImage() {
			File file = new ImageFileChooser().getImageFileChooser().showOpenDialog(stage);
			this.ImageViewSrc.setImage(new Image(file.toURI().toString()));
			
		}
		
		private void init() {
			if(this.CBoxFilter!=null)
				this.CBoxFilter.setItems(FXCollections.observableArrayList(
				    "New Document", "Open ", 
				    new Separator(), "Save", "Save as"));
		}
}
