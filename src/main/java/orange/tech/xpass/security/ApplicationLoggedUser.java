package orange.tech.xpass.security;

import orange.tech.xpass.entity.Person;
import orange.tech.xpass.exception.ApplicationException;

public interface ApplicationLoggedUser {
	Person loggedUser();
	void replace(Person person);
	void tryFindUsername(String username) throws ApplicationException;
	void tryFindPassword(String password) throws ApplicationException;
}
