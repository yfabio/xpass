package orange.tech.xpass.property;

import jakarta.persistence.Column;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import orange.tech.xpass.util.ImageUtil;


public class Person {

	private LongProperty id = new SimpleLongProperty();

	private StringProperty username = new SimpleStringProperty();

	private StringProperty password = new SimpleStringProperty();

	private StringProperty email = new SimpleStringProperty();

	private ObjectProperty<Image> image = new SimpleObjectProperty<>();

	private byte[] photo;
	
	public LongProperty idProperty() {
		return this.id;
	}


	public long getId() {
		return this.idProperty().get();
	}

	
	public void setId(final long id) {
		this.idProperty().set(id);
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

	public StringProperty emailProperty() {
		return this.email;
	}

	@Column(length = 150)
	public String getEmail() {
		return this.emailProperty().get();
	}

	public void setEmail(final String email) {
		this.emailProperty().set(email);
	}

	public ObjectProperty<Image> imageProperty() {
		return this.image;
	}
	
	public Image getImage() {
		return this.imageProperty().get();
	}

	public void setImage(final Image photo) {
		this.imageProperty().set(photo);
	}

	public byte[] getPhoto() {
		return photo;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
		ImageUtil.byteArrayToImage(photo,this::setImage);
	}


	@Override
	public String toString() {
		return "Person [id=" + id.get() + ", username=" + username.get() + ", password=" + password.get() + ", email=" + email.get()
				+ "]";
	}

	
	
}
