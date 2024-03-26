package orange.tech.xpass;


import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import javafx.application.Application;

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
	public Validator validatorFactory() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}  
	
	
	
}
