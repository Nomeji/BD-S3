import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

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
		String url="jdbc:oracle:thin:cbouch3/Superhugsar1@oracle.iut-orsay.fr:1521:etudom";//connexion a la bd à modifier pour vous.
				Connection co=OutilsJDBC.openConnection(url);
				System.out.println("connexion ouverte");
				
				
				//verification des fonctions
				//fonction1 nbstage
				CallableStatement cst =co.prepareCall("{?=call nbstage()}");
				cst.registerOutParameter(1,java.sql.Types.INTEGER);
				boolean succes =cst.execute();
				float resultat =cst.getInt(1);
				System.out.println("Fonction 1:");
				System.out.println(resultat);
				
				//fonction2 nbsansstage
				cst =co.prepareCall("{?=call nbnonstage()}");
				cst.registerOutParameter(1,java.sql.Types.INTEGER);
				succes =cst.execute();
				resultat =cst.getInt(1);
				System.out.println("Fonction 2:");
				System.out.println(resultat);
				//Verification de la table statistique
				ResultSet resul=OutilsJDBC.exec1Requete("Select * From statistique",co,0);
				
				while(resul.next()){
				int colonne1 = resul.getInt(1);
				int colonne2 =resul.getInt(2);
				System.out.println("Verification de la table statistique");
				System.out.println("Colonne1 ="+colonne1);
				System.out.println("Colonne2 ="+colonne2);				
				}
				
				//fonction3 nbnonstageannee(dateUser date)				
				cst =co.prepareCall("{?=call nbnonstageannee(?)}");				
				cst.setDate(2,new Date(112,0,01));// vous pouvez modifer date !!! pour année compter a partir de 1900 et pour moins à partir de 0
				cst.registerOutParameter(1,java.sql.Types.INTEGER);				
				System.out.println("Fonction 3:");
				succes =cst.execute();
				resultat =cst.getInt(1);
				System.out.println(resultat);
	
				
				
				//fonction4 nbstagiaireparentreprise(n IN number, nomentreprise Out Varchar2)
				int annee=3;//vous pouvez modifier ce chiffre pour changer les annee
				cst =co.prepareCall("{?=call nbentreprise()}");
				cst.registerOutParameter(1, java.sql.Types.INTEGER);
				succes=cst.execute();
				int nbentreprise=cst.getInt(1);
				System.out.println(nbentreprise);
				cst =co.prepareCall("{?=call nbstagiaireparentreprise(?,?)}");
				for (int i=1; i<=nbentreprise;i++){
					cst.setInt(2,annee);//vous pouvez modifier la date (deuxieme parametre)
					cst.setInt(3,i);
					cst.registerOutParameter(1,java.sql.Types.INTEGER);
					cst.registerOutParameter(2,java.sql.Types.INTEGER);
					cst.registerOutParameter(3,java.sql.Types.INTEGER);
					//vous pouvez modifier le deuxieme parametre pour changer le nombre d'annee
					cst.execute();					
					resultat=cst.getInt(1);
					System.out.println("l'entreprise numéro "+i+" a eu "+resultat+" stagiaires");
				}
			//fonction5 nbstagiairemoyen(n integer)
				cst =co.prepareCall("{?=call nbstagiairemoyen(?)}");
				cst.setInt(2,3);//vous pouvez modifier le deuxieme parametre
				cst.registerOutParameter(1,java.sql.Types.INTEGER);
				cst.registerOutParameter(2,java.sql.Types.INTEGER);
				succes=cst.execute();
				resultat =cst.getInt(1);
				System.out.println("Fonction 5:");
				System.out.println(resultat);
				
				//fonction6 nbstagezonechoisi(departement varchar2,ville varchar2)
				cst =co.prepareCall("{?=call nbstagezonechoisi(?,?)}");
				cst.setString(2,"ESSONNE");//vous pouvez modifier le deuxième paramètre !!! departement en MAJUSCULE verifier que le departement existe bien dans données test
				cst.setString(3,"Massy");//vous pouvez modifier le deuxième paramètre !!!ville avec première lettre en majuscule et les autres en miniscule verifier que la ville existe bien dans données test
				cst.registerOutParameter(1,java.sql.Types.INTEGER);
				cst.registerOutParameter(2,java.sql.Types.VARCHAR);
				cst.registerOutParameter(3,java.sql.Types.VARCHAR);
				succes=cst.execute();
				resultat=cst.getInt(1);
				System.out.println("Fonction 6:");
				System.out.println(resultat);
				
	/*		//fonction7  nbstagetoutezone (departement Out Varchar2, ville Out Varchar2)
				// nous n'arrivons pas à recuperer les valeurs de la fonction entreprisecontact quelque soit la méthode que l'on utiliser il faudrait que la fonction ne retourne qu'une seul ligne
				cst =co.prepareCall("{?=call departement()}");
				cst.registerOutParameter(1,java.sql.Types.VARCHAR);
				resul=cst.executeQuery();
				while(resul.next()){
					String departement=resul.getString(1);
					System.out.println("departement");
				}
				
				
				cst =co.prepareCall("{?=call nbstagetoutezone(?,?)}");
				cst.registerOutParameter(1,java.sql.Types.INTEGER);
				cst.registerOutParameter(2,java.sql.Types.VARCHAR);	
				cst.registerOutParameter(3,java.sql.Types.VARCHAR);	
				cst.execute();
				
				
				while(resul.next()){
					String departement = resul.getString(2);
					String ville = resul.getString(3);
					int nbstagiaire = resul.getInt(1);
					System.out.println(departement+" "+ville+" : "+nbstagiaire);
				}*/
				
				//fonction8 entreprisecontact (n in integer, telephone out varchar2, mRH out varchar2, tRH out varchar2)
				//comme pour la fonction 7 nous n'arrivons pas à recuperer les valeurs de la fonction entreprisecontact quelque soit la méthode que l'on utiliser il faudrait que la fonction ne retourne qu'une seul ligne 
	/*			cst =co.prepareCall("{?=call entreprisecontact(?,?,?,?)}");
				cst.registerOutParameter(1,java.sql.Types.VARCHAR);
				cst.registerOutParameter(2,java.sql.Types.INTEGER);	
				cst.registerOutParameter(3,java.sql.Types.VARCHAR);
				cst.registerOutParameter(4,java.sql.Types.VARCHAR);	
				cst.registerOutParameter(5,java.sql.Types.VARCHAR);	
				resul=cst.getResultSet();

				while(resul.next()){
					String entreprise = resul.getString(1);
					String tel = resul.getString(3);
					String mRH = resul.getString(4);
					String tRH = resul.getString(5);
					System.out.println(entreprise+" telephone "+tel+" mail du RH "+mRH+" tel du RH");
				}*/
				
						
				OutilsJDBC.closeConnection(co);
				System.out.println("connexion fermee");		
	
		}
}
