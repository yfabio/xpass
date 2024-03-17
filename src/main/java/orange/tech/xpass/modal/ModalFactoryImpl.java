package orange.tech.xpass.modal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ModalFactoryImpl implements ModalFactory {

	private Modal delete;
	
	private Modal proceed;
		
	public ModalFactoryImpl(@Qualifier("deleteModal") Modal delete,
			@Qualifier("proceedModal") Modal proceed) {
		this.delete = delete;
		this.proceed = proceed;
	}

	@Override
	public Modal get(ModalOption opt) {
		return switch (opt) {
			case DELETE -> delete;
			case PROCEED -> proceed;
		};			
	}

}
