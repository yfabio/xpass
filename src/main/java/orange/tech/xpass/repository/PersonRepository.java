package orange.tech.xpass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import orange.tech.xpass.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	
	@Query("SELECT p FROM Person p WHERE p.password = ?1 AND p.username = ?2")				
	Optional<Person> findPasswordByPerson(String password,String username);
	
	Optional<Person> findByUsername(String username);
}
