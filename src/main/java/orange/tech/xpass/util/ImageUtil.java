package orange.tech.xpass.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import orange.tech.xpass.exception.ApplicationException;

public class ImageUtil {

	public static Optional<Image> openFileDialog(Window window,Consumer<byte[]> con) {

		Optional<Image> op = Optional.empty();

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));

		File file = fileChooser.showOpenDialog(window);
		try {
			if (file != null) {
				op = Optional.of(new Image(new FileInputStream(file)));				
				byte[] data = Files.readAllBytes(file.toPath());
				con.accept(data);
			}
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
		
		return op;

	}

	public static Optional<Image> byteArrayToImage(byte[] photo) {

		Optional<Image> op = Optional.empty();

		try {
			if (photo != null) {
				BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(photo));
				if (bimg != null) {
					Image image = SwingFXUtils.toFXImage(bimg, null);
					op = Optional.of(image);
				}
			}
		} catch (IOException e) {
			throw new ApplicationException(e);
		}

		return op;

	}

}
