package orange.tech.xpass.property;

import java.time.LocalDate;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Key {

	private LongProperty id = new SimpleLongProperty();

	private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

	private StringProperty note = new SimpleStringProperty();

	private StringProperty username = new SimpleStringProperty();

	private StringProperty password = new SimpleStringProperty();

	public Key() {
	}

	public Key(int id, LocalDate date, String note, String username, String password) {
		this.id.set(id);
		this.date.set(date);
		this.note.set(note);
		this.username.set(username);
		this.password.set(password);
	}

	public LongProperty idProperty() {
		return this.id;
	}

	public long getId() {
		return this.idProperty().get();
	}

	public void setId(final long id) {
		this.idProperty().set(id);
	}

	public ObjectProperty<LocalDate> dateProperty() {
		return this.date;
	}

	public LocalDate getDate() {
		return this.dateProperty().get();
	}

	public void setDate(final LocalDate date) {
		this.dateProperty().set(date);
	}

	public StringProperty noteProperty() {
		return this.note;
	}

	public String getNote() {
		return this.noteProperty().get();
	}

	public void setNote(final String note) {
		this.noteProperty().set(note);
	}

	public StringProperty usernameProperty() {
		return this.username;
	}

	public String getUsername() {
		return this.usernameProperty().get();
	}

	public void setUsername(final String username) {
		this.usernameProperty().set(username);
	}

	public StringProperty passwordProperty() {
		return this.password;
	}

	public String getPassword() {
		return this.passwordProperty().get();
	}

	public void setPassword(final String password) {
		this.passwordProperty().set(password);
	}

	public void setData(Key key) {
		setId(key.getId());
		setDate(key.getDate());
		setNote(key.getNote());
		setUsername(key.getUsername());
		setPassword(key.getPassword());
	}

	@Override
	public String toString() {
		return "Key [id=" + id.get() + ", date=" + date.get() + ", note=" + note.get() + ", username=" + username.get()
				+ ", password=" + password.get() + "]";
	}

}
