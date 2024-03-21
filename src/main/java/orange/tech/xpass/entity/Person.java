package orange.tech.xpass.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "persons")
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, unique = true)	
	private String username;
	
	@Column(length = 68)
	private String password;
	
	@Column(length = 150)	
	private String email;
	
	@Lob
	@Column(name = "photo",columnDefinition = "BLOB")
	private byte[] photo;
	
	@OneToMany(mappedBy = "person", fetch = FetchType.EAGER)	
	private Set<Key> keys = new LinkedHashSet<>();
			
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public Set<Key> getKeys() {
		return keys;
	}
	
	public void setKeys(Set<Key> keys) {
		this.keys = keys;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}
	
	
	
}
