package ssh;

public class Patologia {
	
	private int id;
	private String nombre;
	
	/**
	 * BEAN
	 * POJO 
	 */
	
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
	public Patologia(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	

}