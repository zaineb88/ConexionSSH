package Patologia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Main {

	
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


		Connection conn = null;
		ResultSet rset = null;
		Statement stmt = null;
		
		try
		{
			conectate_A_SSH();
			
			DriverManager.registerDriver (new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection (cadena_conexion, user, password);
  	        stmt = conn.createStatement();
  	        
  	        
  	      List<Patologia> lista_patologias = null;
  	      List<Sintomas> lista_sintomas = null;
  	      lista_patologias = Consultas.listarPatologias(stmt, conn);
  	      Patologia pato=lista_patologias.get(0);
  	    		  System.out.println(pato);
  	      HashMap<Patologia, List<Sintomas>> hm = new HashMap<Patologia, List<Sintomas>>();
  	    
  	      
  	      
  	      //////////////////////
  	      
  	      for (Patologia patologia : lista_patologias) {
  				lista_sintomas = Consultas.buscarSintomasPorPatologia(conn, patologia.getNom_patol());
  	 	    	hm.put(patologia, lista_sintomas);
  	 	    	System.out.println(patologia+" = "+hm.get(patologia));
  	  	      }
  	      
         	List<Sintomas> ls= hm.get(pato);
         	for(Sintomas sintoma:ls)
          	{
  	        	System.out.println(sintoma);
  	          }
//  	      /////////////////
//
//	      long tiempoInicial = System.currentTimeMillis();
//	      System.out.println(tiempoInicial);
//  	      /* Sin acceder a la base de datos */
//  	      int id = 1;
//  	      int i = 0;
//  	      while(i<lista_patologias.size() && lista_patologias.get(i).getId_patol()!=id)
//  	      {
//  	    	  i++;
//  	      }
//  	      System.out.println(lista_patologias.get(i).getNom_patol());
//  	      /* Accediendo a la base de datos */
//  	      /*Patologia p = Consultas.buscarPatologiasPorId(stmt, conn, 3);
//  	      System.out.println(p.getNombre());*/
//  	      
//  	      long tiempoFinal = System.currentTimeMillis();
//  	      System.out.println(tiempoFinal);
//  	      
//  	      long tiempoTranscurrido = tiempoFinal - tiempoInicial;
//  	      System.out.println(tiempoTranscurrido);
//  	      //System.currentTimeMillis();
//  	      
//			
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

		
	}

}