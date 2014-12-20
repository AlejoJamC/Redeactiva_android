package co.foxdigitalst.redeactiva;

public class Usuario {
	private String email, pass;	
	
	public Usuario(String email, String pass) {
		super();
		this.email = email;
		this.pass = pass;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
