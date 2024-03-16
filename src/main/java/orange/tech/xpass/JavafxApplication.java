package orange.tech.xpass;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavafxApplication extends Application {
	
	private ConfigurableApplicationContext context;
	
	@Override
	public void init() throws Exception {
		
		ApplicationContextInitializer<GenericApplicationContext> initializer = ctx -> {			
			ctx.registerBean(Application.class, () -> JavafxApplication.this);
			ctx.registerBean(Parameters.class, this::getParameters);
			ctx.registerBean(HostServices.class, this::getHostServices);			
		}; 
				
		this.context = new SpringApplicationBuilder()
							.sources(XpassApplication.class)
							.initializers(initializer)
							.run(getParameters().getRaw().toArray(new String[0]));
								
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		context.publishEvent(new StageReadyEvent(stage));
	}

	@Override
	public void stop() throws Exception {
		this.context.close();
		Platform.exit();
	}


	
}

