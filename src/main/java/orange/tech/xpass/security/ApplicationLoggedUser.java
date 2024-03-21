package orange.tech.xpass.security;

import orange.tech.xpass.entity.Person;
import orange.tech.xpass.exception.ApplicationException;

public interface ApplicationLoggedUser {
	Person loggedUser();
	void replace(Person person);	
	void tryLogin(String username, String password)throws ApplicationException;;
}
