package ssh;


import java.util.Comparator;
import java.util.Map;


/**
 * 
 * Esta clase, va a servir para ordenar nuestro HashMap por Valor
 * Al crear el TreeMap <Coche, Persona>, puedo pasarle un Comparador - constructor sobrecargado TreeMap (comparador)
 * Eso s�, el comparador debe ser de Coche. Y el m�todo compare, recibe dos coches compare (Coche o1, Coche o2)
 * 
 * Ahora bien, el truco consiste en lo siguiente:
 * En el constructor, le paso el hashmap, y as�, desde los coches que recibe el comparador, puedo acceder a las personas.
 * Una vez obtenidas las personas, es decir, los valores a trav�s de la clave, realizo la comparaci�n de personas
 * y ah� est� el truco, que el me pregunta qu� coche es mayor, y yo le contesto, diciendole qu� persona es la mayor.
 * De este modo, estoy ordenando por Valor y no Por Clave.
 * 
 * @author Val
 *
 */
public class ComparadorPersonas implements Comparator<Coche>{
	
	Map<Coche, Persona> map;
	
	public ComparadorPersonas(Map<Coche, Persona> mapa) {
		// TODO Auto-generated constructor stub
		this.map = mapa;
	}
	

	public int compare(Coche o1, Coche o2) {
		
		
		Persona paux_1 = null;
		Persona paux_2 = null;
		
		paux_1 = this.map.get(o1);
		paux_2 = this.map.get(o2);
		
		return (paux_1.getEdad()-paux_2.getEdad());
	}

}
