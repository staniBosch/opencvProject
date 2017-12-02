package org.opencvapp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.stage.FileChooser;

public class ImageFileChooser {

	private static final Logger log = LoggerFactory.getLogger(ImageFileChooser.class);

	private FileChooser imageFileChooser;
	private PathsUtils putils = new PathsUtils();

	public ImageFileChooser() {
		init();
		log.debug("FileChooser successful initialized");
	}

	private void init() {

		imageFileChooser = new FileChooser();
		imageFileChooser.setTitle("View Pictures");
		imageFileChooser.setInitialDirectory(putils.FileImg);

		imageFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("BMP", "*.bmp"));
	}

	public FileChooser getImageFileChooser() {
		return imageFileChooser;
	}

	public void setImageFileChooser(FileChooser imageFileChooser) {
		this.imageFileChooser = imageFileChooser;
	}

}
