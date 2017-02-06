package Patologia;

public class Sintomas {
	
	private int id_sintoma;
	private String  des_sintoma;
	
	
	public Sintomas(int id_sintoma, String des_sintoma) {
		super();
		this.id_sintoma = id_sintoma;
		this.des_sintoma = des_sintoma;
	}
	
	
	public int getId_sintoma() {
		return id_sintoma;
	}
	public void setId_sintoma(int id_sintoma) {
		this.id_sintoma = id_sintoma;
	}
	public String getDes_sintoma() {
		return des_sintoma;
	}
	public void setDes_sintoma(String des_sintoma) {
		this.des_sintoma = des_sintoma;
	}
	
	
	
	

}
