package at.stefanbauer.picturepuzzlequiz;

import javafx.scene.image.Image;

import java.io.File;

public class ImageFinder {
	public static File findImage(int index) {
		return new File("./images/"+index+".png");
	}
}
