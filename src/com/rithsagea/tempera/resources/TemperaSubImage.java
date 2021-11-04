package com.rithsagea.tempera.resources;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class TemperaSubImage extends TemperaImage {
	
	protected TemperaSubImage(BufferedImage image) {
		super(image);
	}
	
	public TemperaImage toImage() {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
		
		return new TemperaImage(new BufferedImage(cm, raster, isAlphaPremultiplied, null));
	}
}
