package co.foxdigitalst.redeactiva;

public class Proyecto {
	private String nombre, descripcion, tipo, logo, partitionKey, rowkey, sitio;	
	private String galeria1 = "", galeria2 = "", galeria3 = "", galeria4 = "", galeria5 = "";	
	
	
	public Proyecto(String nombre, String descripcion, String tipo,
			String logo, String partitionKey, String rowkey, String sitio) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.logo = logo;
		this.partitionKey = partitionKey;
		this.rowkey = rowkey;
		this.sitio = sitio;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPartitionKey() {
		return partitionKey;
	}

	public void setPartitionKey(String partitionKey) {
		this.partitionKey = partitionKey;
	}

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}
	public String getGaleria1() {
		return galeria1;
	}
	public void setGaleria1(String galeria1) {
		this.galeria1 = galeria1;
	}
	public String getGaleria2() {
		return galeria2;
	}
	public void setGaleria2(String galeria2) {
		this.galeria2 = galeria2;
	}
	public String getGaleria3() {
		return galeria3;
	}
	public void setGaleria3(String galeria3) {
		this.galeria3 = galeria3;
	}
	public String getGaleria4() {
		return galeria4;
	}
	public void setGaleria4(String galeria4) {
		this.galeria4 = galeria4;
	}
	public String getGaleria5() {
		return galeria5;
	}
	public void setGaleria5(String galeria5) {
		this.galeria5 = galeria5;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSitio() {
		return sitio;
	}
	public void setSitio(String sitio) {
		this.sitio = sitio;
	}
}
