package org.opencvapp.utils;

import java.io.File;

/**
 * 
 * @author SB
 *
 */
public  interface PathsUtils {
	/**
	 * The File for the Imagefolder.
	 */
	File FileImg = new File((PathsUtils.class).getResource("/images").toString());
	/**
	 * The Path for the Imagefolder.
	 */
	String PathImg = new File((PathsUtils.class).getResource("/images").toString()).getPath().substring(6);
	/**
	 *  The File for the temp Image where results can be saved.
	 */
	File ImgFileTempImg = new File((PathsUtils.class).getResource("/images/temp/temp.bmp").toString()); 
	/**
	 *  The File for the temp Image where results can be saved.
	 */
	String ImgPathTempImg = new File((PathsUtils.class).getResource("/images/temp/temp.bmp").toString()).getPath().substring(6); 
	
}
