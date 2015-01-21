package co.foxdigitalst.redeactiva;

public class VideoYoutube {
	private int id;
	private String url, img, nombre;
	
	public VideoYoutube(int id, String url, String img, String nombre) {
		super();
		this.id = id;
		this.url = url;
		this.img = img;
		this.nombre = nombre;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
}
