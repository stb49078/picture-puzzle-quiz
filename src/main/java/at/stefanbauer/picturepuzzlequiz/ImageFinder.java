package at.stefanbauer.picturepuzzlequiz;

import java.io.File;
import java.util.Optional;

public class ImageFinder {
	public static Optional<File> findImage(final int index) {
		final File pngFile = new File("./images/" + index + ".png");
		if (pngFile.exists())
			return Optional.of(pngFile);

		final File jpgFile = new File("./images/" + index + ".jpg");
		if (jpgFile.exists())
			return Optional.of(jpgFile);

		return Optional.empty();
	}
}
