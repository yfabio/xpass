package orange.tech.xpass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import orange.tech.xpass.entity.Key;
import orange.tech.xpass.entity.Person;

public interface KeyRepository extends JpaRepository<Key, Long> {
	
	List<Key> findByNoteContains(String note);
	
	@Query("SELECT k FROM Key k WHERE LOWER(k.title) LIKE LOWER(CONCAT('%',?1,'%')) AND k.person.id = ?2")	
	List<Key> listPersonKeys(String keyword, Long id);
	
	List<Key> findAllByPerson(Person person);
}
