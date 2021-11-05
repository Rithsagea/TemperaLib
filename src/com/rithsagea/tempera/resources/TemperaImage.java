package com.rithsagea.tempera.resources;

import java.awt.Graphics2D;
import java.awt.Image;
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
	
	public TemperaImage resize(int w, int h) {
		BufferedImage img = new BufferedImage(w, h, image.getType());
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(image, 0, 0, w, h, null);
		g2d.dispose();
		
		return new TemperaImage(img);
	}
	
	public TemperaImage subimage(int x, int y, int w, int h) {
		return new TemperaImage(image.getSubimage(x, y, w, h));
	}
	
	public TemperaImage crop(int x, int y, int w, int h) {
		return subimage(x, y, w, h).clone();
	}
	
	public TemperaImage clone() {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
		
		return new TemperaImage(new BufferedImage(cm, raster, isAlphaPremultiplied, null));
	}
}
