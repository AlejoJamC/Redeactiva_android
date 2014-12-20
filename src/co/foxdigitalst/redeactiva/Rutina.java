package co.foxdigitalst.redeactiva;

public class Rutina {
	private String objectId, titulo, dias, hora;	

	public Rutina(String objectId, String titulo, String dias, String hora) {
		super();
		this.objectId = objectId;
		this.titulo = titulo;
		this.dias = dias;
		this.hora = hora;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}	
	
	

}
