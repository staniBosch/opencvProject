package org.opencvapp.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

public class MatObjs {

	private List<Mat> srcMat, grayMat, blurMat, cannyMat, lineMat, circleMat;
	

	private List<File> files;
	public int INTcurrentImage;

	public MatObjs() {
		srcMat = new ArrayList<Mat>();
		grayMat = new ArrayList<Mat>();
		blurMat = new ArrayList<Mat>();
		cannyMat = new ArrayList<Mat>();
		lineMat = new ArrayList<Mat>();
		circleMat = new ArrayList<Mat>();		
		files = new ArrayList<File>();
	}

	/**
	 * @return the srcMat
	 */
	public List<Mat> getSrcMat() {
		return srcMat;
	}

	/**
	 * @param srcMat
	 *            the srcMat to set
	 */
	public void setSrcMat(List<Mat> srcMat) {
		this.srcMat = srcMat;
	}

	/**
	 * @return the grayMat
	 */
	public List<Mat> getGrayMat() {
		return grayMat;
	}

	/**
	 * @param grayMat
	 *            the grayMat to set
	 */
	public void setGrayMat(List<Mat> grayMat) {
		this.grayMat = grayMat;
	}

	/**
	 * @return the blurMat
	 */
	public List<Mat> getBlurMat() {
		return blurMat;
	}

	/**
	 * @param blurMat
	 *            the blurMat to set
	 */
	public void setBlurMat(List<Mat> blurMat) {
		this.blurMat = blurMat;
	}

	/**
	 * @return the cannyMat
	 */
	public List<Mat> getCannyMat() {
		return cannyMat;
	}

	/**
	 * @param cannyMat
	 *            the cannyMat to set
	 */
	public void setCannyMat(List<Mat> cannyMat) {
		this.cannyMat = cannyMat;
	}

	/**
	 * @return the lineMat
	 */
	public List<Mat> getLineMat() {
		return lineMat;
	}

	/**
	 * @param lineMat
	 *            the lineMat to set
	 */
	public void setLineMat(List<Mat> lineMat) {
		this.lineMat = lineMat;
	}

	/**
	 * @return the circleMat
	 */
	public List<Mat> getCircleMat() {
		return circleMat;
	}

	/**
	 * @param circleMat
	 *            the circleMat to set
	 */
	public void setCircleMat(List<Mat> circleMat) {
		this.circleMat = circleMat;
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}

	/**
	 * @param files
	 *            the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}

}
