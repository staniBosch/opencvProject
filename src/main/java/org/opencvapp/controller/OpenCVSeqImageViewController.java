package org.opencvapp.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencvapp.model.MatObjs;
import org.opencvapp.utils.ImageFileChooser;
import org.opencvapp.utils.PathsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	@FXML
	private Slider slider1, slider2, sliderHL1, sliderHL2, sliderHL3, sliderHC1, sliderHC2, sliderHC3, sliderHC4;
	@FXML
	private Label labelSHL1, labelSHL2, labelSHL3;
	@FXML
	private CheckBox chBHoughP, cannyAllOther;
	@FXML
	private Accordion accord;
	@FXML
	private TitledPane cannyPane, houghLPane, houghCPane;

	private PathsUtils putils = new PathsUtils();
	private Stage stage;

	private MatObjs matObj;

	public boolean isPlay;

	public static enum Modi {
		Origin, Gray, Canny, Line, Circle;
		private static Modi[] vals = values();

		public Modi next() {
			return vals[(this.ordinal() + 1) % vals.length];
		}
	};

	private Modi mode;

	public OpenCVSeqImageViewController() {

	}

	public void changeMode(Modi m) {
		mode = m;
		refresh();
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		log.debug("Initialize OpenCVSeqImageViewController");
		ObservableList<String> items = FXCollections.observableArrayList("Graubild", "Canny", "HoughLinien",
				"HoughCircle");
		this.CBoxFilter.setItems(items);
		CBoxFilter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub

				String strValue = CBoxFilter.getItems().get(newValue.intValue());
				if (strValue.equals("Graubild")) {
					loadGrayImage();
					changeMode(Modi.Gray);
				}
				if (strValue.equals("Canny")) {
					if (matObj.getCannyMat().isEmpty())
						loadCannyImage((int) slider1.getValue(), (int) slider2.getValue());
					accord.setExpandedPane(cannyPane);
					changeMode(Modi.Canny);
				}
				if (strValue.equals("HoughLinien")) {
					loadHoughLines(100, 0, 0);
					accord.setExpandedPane(houghLPane);
					changeMode(Modi.Line);
				}
				if (strValue.equals("HoughCircle")) {
					loadPrak(10, 20);
					accord.setExpandedPane(houghCPane);
					changeMode(Modi.Circle);
				}

				refresh();
			}
		});

		this.slider1.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub

				if (!matObj.getCannyMat().isEmpty() && !cannyAllOther.isSelected()) {
					loadCannyImage((int) newValue.doubleValue(), (int) slider2.getValue());
				} else {
					loadHoughLines((int) sliderHL1.getValue(), (int) sliderHL2.getValue(), (int) sliderHL3.getValue());
					loadPrak(0, 0);
				}
				refresh();
			}
		});
		this.slider2.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if (!matObj.getCannyMat().isEmpty() && !cannyAllOther.isSelected()) {
					loadCannyImage((int) slider1.getValue(), (int) newValue.doubleValue());
				} else {
					loadHoughLines((int) sliderHL1.getValue(), (int) sliderHL2.getValue(), (int) sliderHL3.getValue());
					loadPrak(0, 0);
				}
				refresh();
			}
		});
		this.sliderHL1.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				String strValue = CBoxFilter.getSelectionModel().getSelectedItem();
				if (strValue.equals("HoughLinien")) {
					loadHoughLines((int) newValue.doubleValue(), (int) sliderHL2.getValue(),
							(int) sliderHL3.getValue());
					changeMode(Modi.Line);
				}
				refresh();
			}
		});
		this.sliderHL2.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				String strValue = CBoxFilter.getSelectionModel().getSelectedItem();
				if (strValue.equals("HoughLinien")) {
					loadHoughLines((int) sliderHL1.getValue(), (int) newValue.doubleValue(),
							(int) sliderHL3.getValue());
					changeMode(Modi.Line);
				}
				refresh();
			}
		});
		this.sliderHL3.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				String strValue = CBoxFilter.getSelectionModel().getSelectedItem();
				if (strValue.equals("HoughLinien")) {
					loadHoughLines((int) sliderHL1.getValue(), (int) sliderHL2.getValue(),
							(int) newValue.doubleValue());
					changeMode(Modi.Line);
				}
				refresh();
			}
		});
	}

	@FXML
	public void keyEvent(KeyEvent event) {
		if (event.getCode().equals(KeyCode.LEFT))
			socbtnLeft();
		if (event.getCode().equals(KeyCode.RIGHT))
			socbtnRight();
	}

	@FXML
	public void socbtnRight() {

		matObj.INTcurrentImage = ((matObj.INTcurrentImage >= matObj.getFiles().size() - 1) ? 0
				: matObj.INTcurrentImage + 1);
		refresh();
	}

	@FXML
	public void socbtnLeft() {
		matObj.INTcurrentImage = ((matObj.INTcurrentImage <= 0) ? matObj.getFiles().size() - 1
				: matObj.INTcurrentImage - 1);
		refresh();
	}

	@FXML
	public void socbtnAnzahl() {
		if (mode.next() != null)
			mode = mode.next();
		refresh();

	}

	@FXML
	public void socbtnLoad() {

		List<File> files = new ImageFileChooser().getImageFileChooser().showOpenMultipleDialog(stage);
		if (files != null) {
			matObj = new MatObjs();
			matObj.setFiles(files);
			mode = Modi.Origin;
			matObj.INTcurrentImage = 0;
			refresh();

			// matObj.getSrcMat() = Imgcodecs.imread(f.getPath());
		} else {
			log.debug("No file was choosen");
		}
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
	public void socbtnPlay() {
		this.isPlay = true;
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (isPlay) {
					Platform.runLater(new Runnable() {
						public void run() {
							matObj.INTcurrentImage = ((matObj.INTcurrentImage >= matObj.getFiles().size() - 1) ? 0
									: matObj.INTcurrentImage + 1);
							refresh();
						}
					});
					Thread.sleep(100);
				}
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	@FXML
	public void socbtnStop() {
		this.isPlay = false;
	}

	/**
	 * refresh the ImageView and all buttons
	 */
	public void refresh() {
		if (matObj.getFiles() == null) {
			this.btnAnzahl.setDisable(true);
			this.btnAnzahl.setText("0/0");
		} else {

			switch (mode) {
			case Origin:
				this.btnAnzahl
						.setText("[" + matObj.INTcurrentImage + "/" + (matObj.getFiles().size() - 1) + "] " + mode);
				this.ImageViewSrc.setImage(new Image(matObj.getFiles().get(matObj.INTcurrentImage).toURI().toString()));
				break;
			case Gray:
				if (matObj.getGrayMat().isEmpty()) {
					socbtnAnzahl();
					break;
				}
				this.btnAnzahl
						.setText("[" + matObj.INTcurrentImage + "/" + (matObj.getFiles().size() - 1) + "] " + mode);
				Imgcodecs.imwrite(putils.ImgPathTempImg, matObj.getGrayMat().get(matObj.INTcurrentImage));
				this.ImageViewSrc.setImage(new Image(putils.ImgFileTempImg.toURI().toString()));
				break;
			case Canny:
				if (matObj.getCannyMat().isEmpty()) {
					socbtnAnzahl();
					break;
				}
				this.btnAnzahl
						.setText("[" + matObj.INTcurrentImage + "/" + (matObj.getFiles().size() - 1) + "] " + mode);
				Imgcodecs.imwrite(putils.ImgPathTempImg, matObj.getCannyMat().get(matObj.INTcurrentImage));
				this.ImageViewSrc.setImage(new Image(putils.ImgFileTempImg.toURI().toString()));
				break;
			case Line:
				if (matObj.getLineMat().isEmpty()) {
					socbtnAnzahl();
					break;
				}
				this.btnAnzahl
						.setText("[" + matObj.INTcurrentImage + "/" + (matObj.getFiles().size() - 1) + "] " + mode);
				Imgcodecs.imwrite(putils.ImgPathTempImg, matObj.getLineMat().get(matObj.INTcurrentImage));
				this.ImageViewSrc.setImage(new Image(putils.ImgFileTempImg.toURI().toString()));
				break;
			case Circle:
				if (matObj.getCircleMat().isEmpty()) {
					socbtnAnzahl();
					break;
				}
				this.btnAnzahl
						.setText("[" + matObj.INTcurrentImage + "/" + (matObj.getFiles().size() - 1) + "] " + mode);
				Imgcodecs.imwrite(putils.ImgPathTempImg, matObj.getCircleMat().get(matObj.INTcurrentImage));
				this.ImageViewSrc.setImage(new Image(putils.ImgFileTempImg.toURI().toString()));
				break;
			default:
				break;
			}
		}
	}

	public void loadGrayImage() {
		matObj.setGrayMat(new ArrayList<Mat>());
		for (File f : matObj.getFiles()) {
			matObj.getSrcMat().add(Imgcodecs.imread(f.getPath()));
			matObj.getGrayMat().add(Imgcodecs.imread(f.getPath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE));
		}
	}

	public void loadCannyImage(double th1, double th2) {
		matObj.setCannyMat(new ArrayList<Mat>());
		int i = 0;
		if (matObj.getGrayMat().isEmpty())
			loadGrayImage();
		for (Mat gray : matObj.getGrayMat()) {
			matObj.getCannyMat().add(gray.clone());
			Imgproc.medianBlur(gray, matObj.getCannyMat().get(i), 5);
			Imgproc.Canny(matObj.getCannyMat().get(i), matObj.getCannyMat().get(i), th1, th2, 3, false);
			i++;
		}
	}

	public void loadHoughLines(int th1, int srn, int stn) {
		matObj.setLineMat(new ArrayList<Mat>());
		if (matObj.getCannyMat().isEmpty())
			loadCannyImage((int) slider1.getValue(), (int) slider2.getValue());
		for (Mat src : matObj.getSrcMat()) {
			matObj.getLineMat().add(src.clone());
		}
		int j = 0;

		if (chBHoughP.isSelected()) {
			for (Mat image : matObj.getCannyMat()) {
				Mat lines = new Mat();

				Imgproc.HoughLinesP(image, lines, 1, (Math.PI / 180), th1, srn, stn);
				for (int i = 0; i < lines.rows(); i++) {
					double[] val = lines.get(i, 0);
					Imgproc.line(matObj.getLineMat().get(j), new Point(val[0], val[1]), new Point(val[2], val[3]),
							new Scalar(0, 0, 255), 2);
				}
				j++;
			}
		} else {
			for (Mat image : matObj.getCannyMat()) {
				Mat lines = new Mat();

				Imgproc.HoughLinesP(image, lines, 1, (Math.PI / 180), th1, srn, stn);
				for (int i = 0; i < lines.rows(); i++) {
					double[] val = lines.get(i, 0);
					Imgproc.line(matObj.getLineMat().get(j), new Point(val[0], val[1]), new Point(val[2], val[3]),
							new Scalar(0, 0, 255), 2);
				}
				j++;
			}
		}
	}

	public void loadPrak(int th1, int th2) {
		// loadCannyImage();
		matObj.setCircleMat(new ArrayList<Mat>());
		if (matObj.getCannyMat().isEmpty())
			loadCannyImage((int) slider1.getValue(), (int) slider2.getValue());
		for (Mat src : matObj.getSrcMat()) {
			matObj.getCircleMat().add(src.clone());
		}
		int j = 0;
		for (Mat image : matObj.getCannyMat()) {

			Mat circles = new Mat();

			Imgproc.HoughCircles(image, circles, Imgproc.CV_HOUGH_GRADIENT, 1, 23, 300, 26, 3, 20);

			for (int x = 0; x < circles.cols(); x++) {

				double[] c = circles.get(0, x);

				Point center = new Point(Math.round(c[0]), Math.round(c[1]));
				// circle center
				Imgproc.circle(matObj.getCircleMat().get(j), center, 1, new Scalar(0, 100, 100), 3, 8, 0);
				// circle outline
				int radius = (int) Math.round(c[2]);
				Imgproc.circle(matObj.getCircleMat().get(j), center, radius, new Scalar(0, 0, 255), 2, 7, 0);

			}
			j++;
		}
	}

}
