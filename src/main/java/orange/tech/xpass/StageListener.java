package orange.tech.xpass;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import orange.tech.xpass.navigation.CallBackController;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

	private Resource resource;
	private ApplicationContext ctx;
	
	@Autowired
	public StageListener(@Value("classpath:/fxml/login.fxml") Resource resource, ApplicationContext ctx) {
		this.resource = resource;
		this.ctx = ctx;
	}

	@Override
	public void onApplicationEvent(StageReadyEvent event) {

		try {

			Stage stage = event.getStage();
			URL url = resource.getURL();

			FXMLLoader fxmlLoader = new FXMLLoader(url);						
			fxmlLoader.setControllerFactory(obj -> {						
				 Object bean = ctx.getBean(obj);
				 return bean;
			});
			Pane root = fxmlLoader.load();	
			CallBackController<Stage> callback = fxmlLoader.getController();
			callback.content(() -> stage);
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
