package orange.tech.xpass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import orange.tech.xpass.entity.Key;

public interface KeyRepository extends JpaRepository<Key, Long> {
	
	List<Key> findByNoteContainsOrUsernameContains(String note,String username);
}
