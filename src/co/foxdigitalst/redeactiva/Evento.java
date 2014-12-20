package co.foxdigitalst.redeactiva;

public class Evento {
	private int id;
	private String nombre, tipo, fechaInicial, fechaFinal, entidad, descripcion, 
		clase, web, email;
			
	public Evento(int id, String nombre, String tipo, String fechaInicial,
			String fechaFinal, String entidad, String descripcion,
			String clase, String web, String email) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
		this.entidad = entidad;
		this.descripcion = descripcion;
		this.clase = clase;
		this.web = web;
		this.email = email;
	}
	
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
		
}
