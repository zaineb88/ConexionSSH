
package Patologia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Consultas {
	
	
	public static final String CONSULTA_SINTOMAS_POR_PATOLOGIA = "SELECT * FROM Sintomas WHERE id_sint IN (SELECT id_sint FROM Causas WHERE id_patol IN (SELECT id_patol FROM Patologias WHERE nom_patol = '";
	public static final String CONSULTA_LISTAR_PATOLOGIAS = "SELECT * FROM Patologias";
	public static final String CONSULTA_LISTAR_SINTOMAS = "SELECT * FROM Sintomas";
	public static final String CONSULTA_PATOLOGIAS_POR_ID = "SELECT * FROM Patologias WHERE id_patol = ";

	
	public static Patologia buscarPatologiasPorId (Statement stmt, Connection conn, int id) throws SQLException
	{
		Patologia patologia = null;
		
			ResultSet rset = null;
			rset = stmt.executeQuery(Consultas.CONSULTA_PATOLOGIAS_POR_ID+id);
			while (rset.next())
		    {
				patologia = new Patologia(rset.getInt(1), rset.getNString(2), rset.getString(3), rset.getString(4), rset.getNString(5), buscarSintomasPorPatologia(conn, rset.getNString(2)));
			}
			
		return patologia;
	}
	
	public static List<Sintomas> buscarSintomasPorPatologia (Connection conn, String nombre_patologia) throws SQLException
	{
		List<Sintomas> lista_sintomas = new ArrayList<Sintomas>();
			
			String descripcion_sintoma = null;
			int id_sintoma = 0;
			Sintomas sintoma = null;
			ResultSet rset2 = null;
			Statement stmt2 = null;
			stmt2 = conn.createStatement();
			rset2 = stmt2.executeQuery(Consultas.CONSULTA_SINTOMAS_POR_PATOLOGIA+nombre_patologia+"'))");
			while (rset2.next())
		    {
				id_sintoma = rset2.getInt(1);
				descripcion_sintoma = rset2.getString(2);
				sintoma = new Sintomas(id_sintoma, descripcion_sintoma);
				lista_sintomas.add(sintoma);
			}
			
		return lista_sintomas;
	}
	
	public static List<Patologia> listarPatologias (Statement stmt, Connection conn) throws SQLException
	{
		List<Patologia> lista_patologias = new ArrayList<Patologia>();
			
			Patologia patologia = null;
			
			ResultSet rset = null;
			rset = stmt.executeQuery(Consultas.CONSULTA_LISTAR_PATOLOGIAS);
			while (rset.next())
		    {
				patologia = new Patologia(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5), null);
				lista_patologias.add(patologia);
		    }
			
		return lista_patologias;
	}
	
	public static List<Sintomas> listarSintomas (Statement stmt, Connection conn) throws SQLException
	{
		List<Sintomas> lista_sintomas = new ArrayList<Sintomas>();
			
			Sintomas sintoma = null;
			
			ResultSet rset = null;
			rset = stmt.executeQuery(Consultas.CONSULTA_LISTAR_SINTOMAS);
			while (rset.next())
		    {
				sintoma = new Sintomas(rset.getInt(1), rset.getString(2));
				lista_sintomas.add(sintoma);
		    }
			
		return lista_sintomas;
	}
	
}