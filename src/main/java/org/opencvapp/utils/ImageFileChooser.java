package org.opencvapp.utils;

import java.io.File;

import javafx.stage.FileChooser;

public class ImageFileChooser {

	private FileChooser imageFileChooser;
	
	public ImageFileChooser() {
		init();
	}
	
	private void init(){
		imageFileChooser = new FileChooser();
		imageFileChooser.setTitle("View Pictures");
        imageFileChooser.setInitialDirectory(
            new File(getClass().getResource("/images/").getFile())
        ); 
        imageFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
            );
	}

	public FileChooser getImageFileChooser() {
		return imageFileChooser;
	}

	public void setImageFileChooser(FileChooser imageFileChooser) {
		this.imageFileChooser = imageFileChooser;
	}
	
	
	
}
