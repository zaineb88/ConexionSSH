package ssh;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainHashMap2 {
	
	private static HashMap<Coche, Persona> mapa_personas_coche;
	
	private static Persona damePersonaCoche (Coche c)
	{
		return mapa_personas_coche.get(c);
	}
	
public static void main(String[] args) {
		
		Persona p1 = new Persona("Javi", 23);
		Persona p2 = new Persona("Isa", 24);
		Persona p3 = new Persona("Zizi", 20);
		Persona p4 = new Persona("Aitor", 17);
		Persona p5 = new Persona("Carlota", 23);
		
		Coche c1 = new Coche("3365MLZ", Coche.TipoMarca.FORD);
		Coche c2 = new Coche("2598PQS", Coche.TipoMarca.MERCEDES);
		Coche c3 = new Coche("4546VZR", Coche.TipoMarca.FIAT);
		Coche c4 = new Coche("9879QAS", Coche.TipoMarca.KIA);
		Coche c5 = new Coche("6016KLO", Coche.TipoMarca.NISSAN);
		
		mapa_personas_coche = new HashMap<Coche, Persona>();
		
		mapa_personas_coche.put(c1, p1);
		mapa_personas_coche.put(c2, p2);
		mapa_personas_coche.put(c3, p3);
		mapa_personas_coche.put(c4, p4);
		mapa_personas_coche.put(c5, p5);
		
		Persona p =  mapa_personas_coche.get(c5); //damePersonaCoche(c1);
		
		System.out.println(p.getNombre());
	}

}