package orange.tech.xpass.navigation;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;


@Component
public class FxLoader {
	
	public enum Url {

		LOGIN("/fxml/login.fxml"), 
		REGISTER("/fxml/register.fxml"), 
		MAIN("/fxml/main.fxml"), 
		HOME("/fxml/home.fxml"), 
		KEY("/fxml/key.fxml"), 
		CONFIG("/fxml/config.fxml"), 
		RM("/fxml/deleteModal.fxml"),
		PM("/fxml/proceedModal.fxml"); 
		
		
		private String path;

		private Url(String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}

	} 
	
	private ApplicationContext ctx;
	
	@Autowired
	public FxLoader(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> Navigator load(Url url,Supplier<T> obj)  {
		
		FXMLLoader fxmlLoader = new FXMLLoader(FxLoader.class.getResource(url.getPath()));
				
		Navigator navigator = new Navigator() {			
			@Override
			public Pane navigate() {				
				Pane pane = new Pane();
				
				try {	
					fxmlLoader.setControllerFactory(obj -> {						
						return ctx.getBean(obj);
					});
					pane = fxmlLoader.load();					
					Object controller = fxmlLoader.getController();
					if(controller instanceof CallBackController && obj != null) {						
						CallBackController<T> callback = (CallBackController<T>) controller;
						callback.content(obj);
					}
					
				} catch (IOException e) {					
				}
				return pane;
			}

			
		};
				
		return navigator;
	}
	
	
	
	
}
