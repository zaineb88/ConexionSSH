package Patologia;

public class Patologia {

	
	private int id_patol;
	private String nom_patol;
	private String des_patol;
	private String tratamiento;
	private String causa;
	
	
	public Patologia(int id_patol, String nom_patol, String des_patol, String tratamiento, String causa) {
		super();
		this.id_patol = id_patol;
		this.nom_patol = nom_patol;
		this.des_patol = des_patol;
		this.tratamiento = tratamiento;
		this.causa = causa;
		
	}
	public int getId_patol() {
		return id_patol;
	}
	public void setId_patol(int id_patol) {
		this.id_patol = id_patol;
	}
	public String getNom_patol() {
		return nom_patol;
	}
	public void setNom_patol(String nom_patol) {
		this.nom_patol = nom_patol;
	}
	public String getDes_patol() {
		return des_patol;
	}
	public void setDes_patol(String des_patol) {
		this.des_patol = des_patol;
	}
	public String getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
	
	
}
