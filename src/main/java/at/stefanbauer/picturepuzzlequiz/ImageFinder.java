package at.stefanbauer.picturepuzzlequiz;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Optional;

public class ImageFinder {

	public static Optional<File> findImage(int index) {
		File pngFile = new File("./images/" + index + ".png");
		return Optional.of(pngFile);
	}
}
