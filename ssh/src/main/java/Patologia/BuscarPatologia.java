package Patologia;
import java.util.ArrayList;
import java.util.List;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;




public class BuscarPatologia {
	
	private final static String S_PATH_FILE_PRIVATE_KEY = "id_rsa.ppk"; //\\windows absolut path of our ssh private key locally saved
	private final static String S_PATH_FILE_KNOWN_HOSTS = "known_hosts";
	private final static String S_PASS_PHRASE = "mypassphrase";
	private final static int LOCAl_PORT = 3308; 
	private final static int REMOTE_PORT = 3306; 
	private final static int SSH_REMOTE_PORT = 22; 
	private final static String SSH_USER = "587fca0889f5cf057100004b";
	private final static String SSH_REMOTE_SERVER = "femxa-ebtm.rhcloud.com";
	private final static String MYSQL_REMOTE_SERVER = "127.11.220.2";
	private static Session sesion; //represents each ssh session
	private final static String cadena_conexion = "jdbc:mysql://localhost:3308/femxa";
	private final static String user = "adminGXjlxzG";
	private final static String password = "QBShkFDW_69e";
	
	
	private static void conectate_A_SSH () throws Throwable
	{
		JSch jsch = new JSch();
        jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
        jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY, S_PASS_PHRASE.getBytes());

        sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
        sesion.connect();          
        sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT); 


	}
	
	private static void desconectate_D_SSH ()
	{
		sesion.disconnect();
	}

	
	
	public static void main(String[] args) throws Throwable {
	
		///////////no finciona el boucle no ser porque
		
	List<Patologia> le=listarPatologia(1);
	
	
//	for(int i=0;i<le.size();i++)
//	{
//		System.out.println(le);
//	}
//		for(Patologia p: le)
//		{
//			System.out.println(p);
//		}
		
		
	//	listarPatologia(1);
	}
	
	public static  List<Patologia> listarPatologia (int id) throws Throwable
	
	
	{ 
		List<Patologia> lista_p = null;
		
		lista_p = new ArrayList<Patologia>();
	
		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		
		try
		{
			
			
			conectate_A_SSH();
			
			DriverManager.registerDriver (new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection (cadena_conexion, user, password);
  	        stmt = conn.createStatement();
			rset = stmt.executeQuery(" SELECT * From Patologias Where id_patol="+id);
			String nom_patol=null;
			String descripcion = null;
			String tratamiento = null;
			String causas = null;
			
			while (rset.next())
				
			    {
					  nom_patol=rset.getString(2);
					descripcion = rset.getString(3);
					tratamiento = rset.getString(4);
					causas = rset.getString(5);
				
					System.out.println("El nombre del patologia es  : "+nom_patol+"\n");
					System.out.println("Descripcion de la patologia es   : "+descripcion +"\n");
					System.out.println("Tratamiento de la patologia es   : "+tratamiento+"\n");
					System.out.println("Causas de la patologia es   : "+causas+"\n");
		
		Patologia p = new Patologia(id, nom_patol,descripcion ,tratamiento,causas,null);
			
			
			lista_p.add(p);
	//	lista_p.add((Patologia) ListarSintomas.consultaSintoma());
				 }

			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if (rset != null) 	{ try { rset.close(); } catch (Exception e2) { e2.printStackTrace(); }}
			if (stmt != null)	{ try {	stmt.close(); } catch (Exception e2) { e2.printStackTrace(); }}
			if (conn != null) 	{ try { conn.close(); } catch (Exception e3) { e3.printStackTrace(); }}
		  	desconectate_D_SSH(); 
		}   

		
	

		
		
		/////////////////////
		return lista_p;
		
	}

}
