package org.opencvapp.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class OpenCVSeqImageViewController implements Initializable {

	private static final Logger log = LoggerFactory.getLogger(OpenCVSeqImageViewController.class);
	static {
		nu.pattern.OpenCV.loadShared();
		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
	}

	@FXML
	ImageView ImageViewSrc;	
	@FXML
	ChoiceBox<String> CBoxFilter;
	@FXML
	Button btnAnzahl;

	private Stage stage;
	private List<Mat> srcMat, dstMat;
	List<File> files;
	private int INTcurrentImage;

	private enum Modi {
		Origin, Modify
	};

	private Modi mode;

	public OpenCVSeqImageViewController() {

	}

	public OpenCVSeqImageViewController(Stage stage) {
		this.stage = stage;
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		log.debug("Initialize OpenCVSeqImageViewController");		
		ObservableList<String> items = FXCollections.observableArrayList("Graubild", "Canny", "Praktikum");
		this.CBoxFilter.setItems(items);
		CBoxFilter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				String strValue = CBoxFilter.getItems().get(newValue.intValue());
				if (strValue.equals("Graubild"))
					loadGrayImage();
				if (strValue.equals("Canny"))
					loadCannyImage();
				if (strValue.equals("Praktikum"))
					loadPrak();
			}
		});
	}

	@FXML
	public void socbtnRight() {

		this.INTcurrentImage = (this.INTcurrentImage >= this.files.size() - 1) ? 0 : this.INTcurrentImage + 1;
		refresh();
	}

	@FXML
	public void socbtnLeft() {
		this.INTcurrentImage = (this.INTcurrentImage <= 0) ? this.files.size() - 1 : this.INTcurrentImage - 1;
		refresh();
	}

	@FXML
	public void socbtnAnzahl() {
		if (this.mode.equals(Modi.Origin)) {
			mode = Modi.Modify;
		} else {
			mode = Modi.Origin;
		}

	}

	@FXML
	public void socbtnLoad() {
		this.srcMat = new ArrayList<Mat>();
		this.dstMat = new ArrayList<Mat>();
		this.files = new ImageFileChooser().getImageFileChooser().showOpenMultipleDialog(stage);
		if (this.files != null) {
			mode = Modi.Origin;
			this.INTcurrentImage = 0;
			refresh();

			// this.srcMat = Imgcodecs.imread(f.getPath());
		} else {
			log.debug("No file was choosen");
		}
	}

	public void refresh() {
		if (this.files == null) {
			this.btnAnzahl.setDisable(true);
			this.btnAnzahl.setText("0/0");
		} else
			this.btnAnzahl.setText("[" + INTcurrentImage + "/" + (files.size() - 1) + "] " + mode);

		if (this.mode.equals(Modi.Origin)) {
			this.ImageViewSrc.setImage(new Image(files.get(this.INTcurrentImage).toURI().toString()));
		} else {
			Imgcodecs.imwrite(PathsUtils.ImgPathTempImg, this.dstMat.get(INTcurrentImage));
			this.ImageViewSrc.setImage(new Image(PathsUtils.ImgFileTempImg.getPath()));
		}
	}

	public void loadGrayImage() {
		for (File f : this.files) {
			this.srcMat.add(Imgcodecs.imread(f.getPath()));
			this.dstMat.add(Imgcodecs.imread(f.getPath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE));
		}
		this.mode = Modi.Modify;
		refresh();

	}

	public void loadCannyImage() {
		int i = 0;
		loadGrayImage();

		for (Mat src : this.srcMat) {
			Imgproc.cvtColor(src, this.dstMat.get(i), Imgproc.COLOR_RGB2GRAY);
			Imgproc.blur(this.dstMat.get(i), this.dstMat.get(i), new Size(3, 3));
			Imgproc.Canny(this.dstMat.get(i), this.dstMat.get(i), 100, 180, 3, false);
			i++;
		}
		refresh();
	}
	
	public void loadPrak() {
		/*TO-DO
		 * 
		 */
		
		refresh();
	}

}
