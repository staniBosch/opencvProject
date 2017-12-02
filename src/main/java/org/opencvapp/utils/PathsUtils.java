package org.opencvapp.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author SB
 *
 */
public class PathsUtils {

	private static final Logger log = LoggerFactory.getLogger(PathsUtils.class);

	public PathsUtils() {
		boolean isJar;
		String className = this.getClass().getName().replace('.', '/');
		String classJar = this.getClass().getResource("/" + className + ".class").toString();
		if (classJar.startsWith("jar:"))
			isJar = true;
		else
			isJar = false;

		/************************************************/

		if (!isJar) {
			log.debug("You're running inside Eclipse");
			FileImg = new File(getClass().getResource("/images").getPath());
			PathImg = new File(getClass().getResource("/images").toString()).getPath().substring(6);
			ImgFileTempImg = new File(getClass().getResource("/images/tmp/tmp.bmp").getPath());
			ImgPathTempImg = new File(getClass().getResource("/images/tmp/tmp.bmp").toString()).getPath().substring(6);
		} else {
			log.debug("You're running as jar");
			String jarPath = new File("").getAbsolutePath();
			this.FileImg = new File(jarPath + "/images");
			this.ImgFileTempImg = new File(jarPath + "/images/tmp/tmp.bmp");
			this.PathImg = new File(jarPath + "/images").getPath();
			this.ImgPathTempImg = new File(jarPath + "/images/tmp/tmp.bmp").getPath();
			if (!new File(jarPath + "/images/tmp").exists()) {
				new File(jarPath + "/images/tmp").mkdirs();
				try {
					log.debug("file was created:" + this.ImgFileTempImg.createNewFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else
				log.debug("folders exist already");
		}
	}

	/**
	 * The File for the Imagefolder.
	 */
	public File FileImg;
	/**
	 * The Path for the Imagefolder.
	 */
	public String PathImg;
	/**
	 * The File for the temp Image where results can be saved.
	 */
	public File ImgFileTempImg;
	/**
	 * The File for the temp Image where results can be saved.
	 */
	public String ImgPathTempImg;

}
