package orange.tech.xpass.security;

import org.springframework.stereotype.Component;

import orange.tech.xpass.crypto.Zippo;
import orange.tech.xpass.entity.Person;
import orange.tech.xpass.exception.ApplicationException;
import orange.tech.xpass.repository.PersonRepository;

@Component
public class ApplicationLoggedUserImpl implements ApplicationLoggedUser {

	private PersonRepository personRepository;
	
	private Person loggedUser;
	
	private Zippo zippo;
	
	public ApplicationLoggedUserImpl(PersonRepository personRepository, Zippo zippo) {
		this.personRepository = personRepository;
		this.zippo = zippo;
	}
	
	@Override
	public Person loggedUser() {		
		return loggedUser;
	}


	private Person tryFindUsername(String username) throws ApplicationException {
		return personRepository.findByUsername(username).orElseThrow(() -> new ApplicationException("username was invalid."));
	}

	
	

	@Override
	public void replace(Person person) {
		loggedUser = person;		
	}

	@Override
	public void tryLogin(String username, String password) throws ApplicationException {
		loggedUser = tryFindUsername(username);		
		if(loggedUser!=null) {			
			 String decrypt = zippo.decrypt(loggedUser.getPassword());			 
			 if(!decrypt.equals(password)) {
				 throw new ApplicationException("password was invalid");
			 }			
		}else {
			throw new ApplicationException("user not found!");
		}
		
	}

	
	
		
}
