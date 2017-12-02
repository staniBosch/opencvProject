package org.opencvapp.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencvapp.utils.ImageFileChooser;
import org.opencvapp.utils.PathsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class OpenCVImageViewController implements Initializable {

	@FXML
	ImageView ImageViewSrc;
	@FXML
	ImageView ImageViewDst;
	@FXML
	ChoiceBox<String> CBoxFilter;

	private Mat srcMat, dstMat;
	private static final Logger log = LoggerFactory.getLogger(OpenCVImageViewController.class);
	static {
		nu.pattern.OpenCV.loadShared();
		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
	}

	private Stage stage;
	private PathsUtils putils = new PathsUtils();

	public OpenCVImageViewController() {

	}

	public OpenCVImageViewController(Stage stage) {
		this.stage = stage;
	}

	@FXML
	public void loadImage() {
		File file = new ImageFileChooser().getImageFileChooser().showOpenDialog(stage);
		if (file != null) {
			this.ImageViewSrc.setImage(new Image(file.toURI().toString()));
			this.srcMat = Imgcodecs.imread(file.getPath());
		} else {
			log.debug("No file was choosen");
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> items = FXCollections.observableArrayList("Graubild", "Canny", "...");
		this.CBoxFilter.setItems(items);
		CBoxFilter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				String strValue = CBoxFilter.getItems().get(newValue.intValue());
				if (strValue.equals("Graubild"))
					loadGrayImage();
				if (strValue.equals("Canny"))
					loadCannyImage();
				if (strValue.equals("..."))
					loadOtherImage();
			}
		});
	}

	public void loadGrayImage() {
		this.dstMat = this.srcMat.clone();

		Imgproc.cvtColor(this.srcMat, this.dstMat, Imgproc.COLOR_RGB2GRAY);
		Imgcodecs.imwrite(putils.ImgPathTempImg, this.dstMat);

		this.ImageViewDst.setImage(new Image(putils.ImgFileTempImg.toURI().toString()));
	}

	public void loadCannyImage() {
		this.dstMat = this.srcMat.clone();

		Imgproc.cvtColor(this.srcMat, this.dstMat, Imgproc.COLOR_RGB2GRAY);
		Imgproc.blur(this.dstMat, this.dstMat, new Size(3, 3));
		Imgproc.Canny(this.dstMat, this.dstMat, 100, 180, 3, false);

		Imgcodecs.imwrite(putils.ImgPathTempImg, this.dstMat);

		this.ImageViewDst.setImage(new Image(putils.ImgFileTempImg.toURI().toString()));
	}

	public void loadOtherImage() {

	}

}
