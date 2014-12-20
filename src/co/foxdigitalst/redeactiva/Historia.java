package co.foxdigitalst.redeactiva;

public class Historia {
	private String objectId, descripcion, adjunto, idUsuario;

	public Historia(String objectId, String descripcion, String adjunto,
			String idUsuario) {
		super();
		this.objectId = objectId;
		this.descripcion = descripcion;
		this.adjunto = adjunto;
		this.idUsuario = idUsuario;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
