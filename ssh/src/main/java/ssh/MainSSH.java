package ssh;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class MainSSH {
	
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

	
	
	public static void main(String[] args) throws Throwable {
		
		
		Connection conn = null;
		ResultSet rset = null;
		ResultSet rset2 = null;
		ResultSet rset3 = null;
		Statement stmt = null;
		
		try
		{
			
			
			conectate_A_SSH();
			
			DriverManager.registerDriver (new com.mysql.jdbc.Driver());// m�todo equivalente al anterior
			//Sea como sea, es, <<oye, si te piden una conexi�n, se la pides a esa clase!>>
			conn = DriverManager.getConnection (cadena_conexion, user, password);
  	        stmt = conn.createStatement();
			rset = stmt.executeQuery("SELECT des_sint FROM Sintomas where id_sint = 1");
			String nombre = null;
			Integer id = null;
			while (rset.next())
				
			    {
					  
					nombre = rset.getString(1);
					
					System.out.println("Sintoma  = "+nombre);
				 }
			
			/////////////////////////////
			
			rset2 = stmt.executeQuery(" SELECT nom_patol From Patologias Where id_patol IN (Select id_patol From Patologias Where id_patol=1)");
			String nom = null;
			
			while (rset2.next())
				
			    {
					  
					nom = rset2.getString(1);
					
					System.out.println("PATOLOGIA N 1 ES  = "+nom);
				 }
			//////////////////////////////
			
			rset3 = stmt.executeQuery(" SELECT * From Patologias Where nom_patol='Queratocono'");
			//rset3 = stmt.executeQuery(" SELECT des_patol,trat_patol,causa_patol,des_sint From Patologias p,Causas c,Sintomas s Where p.id_patol= c.Patologias_id_patol and c.Stintomas_id_sint=s.id_sint and nom_patol='Queratocono'");
		//	rset3 = stmt.executeQuery(" SELECT des_patol,trat_patol,causa_patol,des_sint From Patologias p,Causas c whre p.id_patol=c.Patologias_id_patol and Sintomas_id_sint in (select id_sint from Sintomas)");
			String descripcion = null;
			String tratamiento = null;
			String causas = null;
		//	String sintomas=null;
			
			while (rset3.next())
				
			    {
					  
					descripcion = rset3.getString(3);
					tratamiento = rset3.getString(4);
					causas = rset3.getString(5);
					//sintomas = rset3.getString();
					
					System.out.println("Descripcion de la patologia es   = "+descripcion +"\n");
					System.out.println("Tratamiento de la patologia es   = "+tratamiento+"\n");
					System.out.println("Causas de la patologia es   = "+causas+"\n");
					//System.out.println("Sintomas de la patologia es   = "+sintomas);
				 }
			////////////////////////
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally //libero recursos, de "adentro a fuera" , ResultSet, Statment, Conexion
		{
			if (rset != null) 	{ try { rset.close(); } catch (Exception e2) { e2.printStackTrace(); }}
			if (stmt != null)	{ try {	stmt.close(); } catch (Exception e2) { e2.printStackTrace(); }}
			if (conn != null) 	{ try { conn.close(); } catch (Exception e3) { e3.printStackTrace(); }}
		  	desconectate_D_SSH(); 
		}   

		
	}

}
