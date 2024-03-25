package orange.tech.xpass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import orange.tech.xpass.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {	

	Optional<Person> findByUsername(String username);
	
}
