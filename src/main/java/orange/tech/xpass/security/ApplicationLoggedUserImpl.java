package orange.tech.xpass.security;

import org.springframework.stereotype.Component;

import orange.tech.xpass.entity.Person;
import orange.tech.xpass.exception.ApplicationException;
import orange.tech.xpass.repository.PersonRepository;

@Component
public class ApplicationLoggedUserImpl implements ApplicationLoggedUser {

	private PersonRepository personRepository;
	
	private Person loggedUser;
	
	public ApplicationLoggedUserImpl(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	@Override
	public Person loggedUser() {		
		return loggedUser;
	}

	@Override
	public void tryFindUsername(String username) throws ApplicationException {
		personRepository.findByUsername(username).orElseThrow(() -> new ApplicationException("username was invalid."));
	}

	@Override
	public void tryFindPassword(String password) throws ApplicationException {
		loggedUser = personRepository.findByPassword(password).orElseThrow(() -> new ApplicationException("password was invalid"));		
	}

	@Override
	public void replace(Person person) {
		loggedUser = person;		
	}

	
	
		
}
