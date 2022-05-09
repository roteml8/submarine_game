package submarinegame.game;

import java.io.Serializable;

public class Player implements Serializable{
	
	protected String name;
	protected String email;
	protected String phone;
	
	public Player(String name, String email, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}
	
	

}
