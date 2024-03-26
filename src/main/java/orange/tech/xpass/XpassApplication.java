package orange.tech.xpass;


import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javafx.application.Application;
import net.synedra.validatorfx.Validator;

@SpringBootApplication
@ComponentScan("orange.tech.xpass")
public class XpassApplication {

	public static void main(String[] args) {
		Application.launch(JavafxApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}	
	
	@Bean
	public Validator validator() {
		return new Validator();
	} 
	
	@Bean
	public org.springframework.validation.Validator validatorFactory() {
		return new LocalValidatorFactoryBean();
	}  
	
	
	
}
