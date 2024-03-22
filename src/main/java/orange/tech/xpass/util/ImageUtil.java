package orange.tech.xpass.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import orange.tech.xpass.exception.ApplicationException;

public class ImageUtil {

	public static Optional<Image> openFileDialog(Window window,Consumer<byte[]> con) {

		ExecutorService job = Executors.newSingleThreadExecutor();
		
		Optional<Image> op = Optional.empty();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		ExtensionFilter[] extensionFilter  = {new FileChooser.ExtensionFilter("All Images","*.*"),
											  new FileChooser.ExtensionFilter("JPG", "*.jpg"),
											  new FileChooser.ExtensionFilter("PNG", "*.png")};
		
		fileChooser.getExtensionFilters().addAll(extensionFilter);
		File file = fileChooser.showOpenDialog(window);
		
		try {
			if (file != null) {
				op = Optional.of(new Image(new FileInputStream(file)));				
				job.submit(() -> {
					byte[] data = Files.readAllBytes(file.toPath());
					con.accept(data);
					return null;
				});
			}
		} catch (IOException e) {
			throw new ApplicationException(e);
		}finally {
			job.shutdown();
		}
		
		return op;

	}

	public static void byteArrayToImage(byte[] photo,Consumer<Image> con) {
		
		ExecutorService job = Executors.newSingleThreadExecutor();
		
		job.submit(() -> {
			try {
				if (photo != null) {
					BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(photo));
					if (bimg != null) {
						Image image = SwingFXUtils.toFXImage(bimg, null);
						con.accept(image);
					}
				}
			} catch (IOException e) {
				throw new ApplicationException(e);
			}finally {
				job.shutdown();
			}
		});

		

	}

}
