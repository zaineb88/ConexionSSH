package Patologia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class ListarSintomas {
	

	
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
        sesion.connect(); //ssh connection established!

        //by security policy, you must connect through a fowarded port          
        sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT); 


	}
	
	private static void desconectate_D_SSH ()
	{
		sesion.disconnect();
	}


	
	
	public static List<Sintomas> consultaSintoma () throws Throwable
	
	{
	 

		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		List <Sintomas> lista_s;
		lista_s = new ArrayList<Sintomas>();
		try
		{
			
			
			conectate_A_SSH();
			
			DriverManager.registerDriver (new com.mysql.jdbc.Driver());// método equivalente al anterior
			//Sea como sea, es, <<oye, si te piden una conexión, se la pides a esa clase!>>
			conn = DriverManager.getConnection (cadena_conexion, user, password);
	        stmt = conn.createStatement();
	        
	        /////////// consulta de los sintomas
	        
	        System.out.println("-----LA LISTA DE SINTOMAS-----\n");
	        
			rset = stmt.executeQuery("SELECT * FROM Sintomas");
			String sintoma = null;
			int id=0;
			while (rset.next())
				
			    {
					  id=rset.getInt(1);
					sintoma = rset.getString(2);
					
					
					System.out.println(sintoma);
					Sintomas sin=new Sintomas(id, sintoma);
		            	lista_s.add(sin);
			
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


	 return lista_s;
	}
	
	
	
	
	
	
	public static void main(String[] args) throws Throwable {
		
		
		consultaSintoma();
		
	}
	
	
	

}


