package orange.tech.xpass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import orange.tech.xpass.entity.Key;



public interface KeyRepository extends JpaRepository<Key, Long> {
}
