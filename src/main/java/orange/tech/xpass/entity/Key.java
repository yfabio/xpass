package orange.tech.xpass.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "keys")
public class Key implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@NotNull(message = "date is required")
	private LocalDate date;
	
	@NotBlank(message = "title is required")
	@Column(length = 100)
	private String title;
	
	@NotBlank(message = "note is required")
	@Column(length = 1000)	
	private String note;
	
	@NotBlank(message = "username is required")
	@Column(length = 100)	
	private String username; 
	
	@Size(min = 4, message = "minimum length 4")
	@NotBlank(message = "password is required")
	@Column(length = 68)	
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	@Override
	public String toString() {
		return "Key [id=" + id + ", date=" + date + ", note=" + note + ", username=" + username + ", password="
				+ password + "]";
	}
	
	
}
