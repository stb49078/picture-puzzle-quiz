package at.stefanbauer.picturepuzzlequiz;

import java.io.File;
import java.util.Optional;

public class ImageFinder {

	public static Optional<File> findImage(int index) {
		File pngFile = new File("./images/" + index + ".png");
		if (pngFile.exists())
			return Optional.of(pngFile);

		File jpgFile = new File("./images/" + index + ".jpg");
		if (jpgFile.exists())
			return Optional.of(jpgFile);

		return Optional.empty();
	}
}
