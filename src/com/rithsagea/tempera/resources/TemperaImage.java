package com.rithsagea.tempera.resources;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TemperaImage {
	
	protected BufferedImage image;
	
	public TemperaImage(String path) {
		this(new File(path));
	}
	
	public TemperaImage(File file) {
		try {
			image = ImageIO.read(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public TemperaImage(InputStream is) {
		try {
			image = ImageIO.read(is);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public TemperaImage(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public TemperaSubImage subimage(int x, int y, int w, int h) {
		return new TemperaSubImage(image.getSubimage(x, y, w, h));
	}
	
	public TemperaImage clone() {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		
		return new TemperaImage(new BufferedImage(cm, raster, isAlphaPremultiplied, null));
	}
}
