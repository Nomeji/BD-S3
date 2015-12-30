import java.sql.*;
import java.util.Scanner;

public class OutilsJDBC {
	public static Connection openConnection(String url) {
		Connection co = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			co = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			System.out.println("il manque le driver oracle");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("impossible de se connecter à l'url : " + url);
			System.exit(1);
		}
		return co;
	}

	public static ResultSet exec1Requete(String requete, Connection co, int type) {
		ResultSet res = null;
		try {
			Statement st;
			if (type == 0) {
				st = co.createStatement();
			} else {
				st = co.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			}
			;
			res = st.executeQuery(requete);
		} catch (SQLException e) {
			System.out.println("Problème lors de l'exécution de la requete : "
					+ requete);
		}
		;
		return res;
	}

	public static void closeConnection(Connection co) {
		try {
			co.close();
			System.out.println("Connexion fermée!");
		} catch (SQLException e) {
			System.out.println("Impossible de fermer la connexion");
		}
	}

	public static void main(String[] args) throws SQLException {
		String url="jdbc:oracle:thin:cbouch3/Superhugsar1@oracle.iut-orsay.fr:1521:etudom";
			
				Connection co=OutilsJDBC.openConnection(url);
				System.out.println("connexion ouverte");
				//Question2
				//traitement et	requete		
			ResultSet resul=OutilsJDBC.exec1Requete("Select * from ENS2004.Film Where rownum<=10", co,0);
				
				while(resul.next())
			    {
				           int numfilm=resul.getInt("numFilm");
				           String nomfilm=resul.getString("titre");
				           int realisateur=resul.getInt("Realisateur");
				           System.out.println("numfilm : "+numfilm+" nomfilm:"+ nomfilm+" realisateur:"+realisateur);
			    }
				//Question3
				/*ResultSet resul=OutilsJDBC.exec1Requete("Select * from ENS2004.Individu Where NOMINDIVIDU='FONDA'", co,0);
				while(resul.next())
			    {
				           int numindividu=resul.getInt("numIndividu");
				           String nomindividu=resul.getString("nomindividu");
				           String prenomindividu=resul.getString("prenomindividu");
				           System.out.println("numIndividu : "+numindividu+" nomindividu:"+ nomindividu+" Prenom Individu:"+prenomindividu);
			    }*/
	/* EXO4	    System.out.println("Entrer le nom d'un acteur :");
				Scanner sc = new Scanner(System.in);
				String j =sc.next();
				System.out.println(j);
				String rec ="Select nomIndividu,prenomIndividu, numIndividu from ENS2004.Individu Where nomIndividu='"+j+"'";
				System.out.println(rec);
				ResultSet resul=OutilsJDBC.exec1Requete(rec, co,0);
				int i=1; 
				while(resul.next()){
					String nomindividu=resul.getString("nomIndividu");
					String prenomindividu=resul.getString("prenomIndividu");
					int num=resul.getInt("numIndividu");
					System.out.println(i+" nomindividu: "+nomindividu+" prenomindividu: "+prenomindividu);		
					i++;
				}
				System.out.println("Choisir l'individu demander (les chiffres)");
				j=sc.next();
				int x =Integer.parseInt(j);
				while (x<1 || x>i-1){
					System.out.println("valeur non valable doit etre compris entre 1 et "+i+", "+i+"non compris" );
					j=sc.next();
					x =Integer.parseInt(j);
				}		
				System.out.println(x);
				i=1;
				int a=1;				
				resul=OutilsJDBC.exec1Requete(rec, co,0);				
				while(resul.next()){
					String nomindividu=resul.getString("nomIndividu");
					String prenomindividu=resul.getString("prenomIndividu");
					int num=resul.getInt("numIndividu");
					if (x==i){
						rec ="Select titre,F.numFilm From ens2004.film F, ens2004.Acteur A Where F.numfilm=A.numfilm and A.numindividu="+num+"";
						ResultSet resul2=OutilsJDBC.exec1Requete(rec, co,0);
						while(resul2.next()){
							String montitre=resul2.getString("titre");
							int numFilm =resul2.getInt("numFilm");
							System.out.println(a+"   "+montitre+ "   numFilm : "+numFilm);	
							a++;
						}
					}
					i++;
				}
				System.out.println("Choisir le film");
				j=sc.next();
				x=Integer.parseInt(j);
				while (x<1 || x>a-1){
					System.out.println("valeur non valable doit etre compris entre 1 et "+a+", "+a+"non compris" );
					j=sc.next();
					x =Integer.parseInt(j);
				}	
				System.out.println(x);
				i=1;
				a=1;
				ResultSet resul2=OutilsJDBC.exec1Requete(rec, co,0);
				while(resul2.next()){
					int numfilm=resul2.getInt("numFilm");
					if(x==a){
						rec ="Select count(numExemplaire) from ens2004.exemplaire E, ens2004.film F where E.numFilm=F.numfilm and f.numfilm="+numfilm+"";
						ResultSet resul3=OutilsJDBC.exec1Requete(rec, co,0);
						while(resul3.next()){
							int nbExmplaire=resul3.getInt("count(numExemplaire)");
							System.out.println("il y a "+nbExmplaire);	
							i++;
						}
					}
					a++;
				}*/
				
				
			
				
				//sc.close();
				OutilsJDBC.closeConnection(co);
				System.out.println("connexion fermee");
	}
}