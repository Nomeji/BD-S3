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
	public static String saisinomActeur(){
		System.out.println("Entrer le nom d'un acteur :");
		Scanner sc = new Scanner(System.in);
		String j =sc.next();
		return j;
	}

	public static void main(String[] args) throws SQLException {
		String url="jdbc:oracle:thin:cbouch3/Superhugsar1@oracle.iut-orsay.fr:1521:etudom";
				Connection co=OutilsJDBC.openConnection(url);
				System.out.println("connexion ouverte");
				//Qestion1
				/*ResultSet resul=OutilsJDBC.exec1Requete("Select * From ENS2004.Exemplaire",co,0);
				ResultSetMetaData res= resul.getMetaData();
				int nbrC=res.getColumnCount();
				for(int i=1; i<=nbrC;i++){
					String name=res.getColumnName(i);
					int nulla =res.isNullable(i);
					String nullable;
					if(nulla!=1){
						nullable="NOT NULL  ";
					}
					else{
						nullable="";
					}
					String cname= res.getColumnTypeName(i);
					System.out.println(name+"     "+nullable+"      "+cname);
				}*/
//Question2
		/*	PreparedStatement psm =co.prepareStatement("Select numindividu, nomIndividu,prenomIndividu from ENS2004.Individu Where nomIndividu=?");
				PreparedStatement psm2 =co.prepareStatement("Select titre From ens2004.film F, ens2004.Acteur A Where F.numfilm=A.numfilm and A.numindividu=?");
				System.out.println("Entrer le nom d'un acteur :");
				Scanner sc = new Scanner(System.in);
				String j =sc.next();
				String nomIndividu=j;
				while(nomIndividu!=""){					
					psm.setString(1,nomIndividu);					
					ResultSet myresultat = psm.executeQuery();
					while(myresultat.next()){
						String nomA=myresultat.getString("nomIndividu");
						String prenom=myresultat.getString("prenomIndividu");
						int num=myresultat.getInt("numIndividu");
						System.out.println("Nom : "+nomA+"   Prenom : "+prenom);
						psm2.setInt(1,num);
						ResultSet myres=psm2.executeQuery();
						while(myres.next()){
							String montitre=myres.getString("titre");
							System.out.println(montitre);
						}
						System.out.println("***************");
					}
					nomIndividu=saisinomActeur();
				}*/
//Question3  				
			/*	System.out.println("Entrer le nom d'un acteur :");
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
				CallableStatement cst =co.prepareCall("{? =call nbrefilm(?)}");				
				i=1;
				int a=1;				
				resul=OutilsJDBC.exec1Requete(rec, co,0);				
				while(resul.next()){
					String nomindividu=resul.getString("nomIndividu");
					String prenomindividu=resul.getString("prenomIndividu");
					int num=resul.getInt("numIndividu");
					if (x==i){
						cst.setInt(2, num);
						cst.registerOutParameter(1,java.sql.Types.INTEGER);
						boolean succes =cst.execute();
						int rNB=cst.getInt(1);
						System.out.println("Nombre de FIlm ="+rNB);
						}
					i++;
				}
				cst.close();*/
				
//Question4
			/*	System.out.println("Entrer le nom d'un realisateur :");
				Scanner sc = new Scanner(System.in);
				String j =sc.next();
				System.out.println(j);
				String rec ="Select distinct nomIndividu from ENS2004.Individu I, ens2004.Film Where realisateur=numindividu and nomIndividu='"+j+"'";				
				System.out.println(rec);
				CallableStatement cst =co.prepareCall("{? =call nbFilm2(?)}");	
				ResultSet resul=OutilsJDBC.exec1Requete(rec, co,0);
				int i=1; 
				while(resul.next()){
					String nomindividu=resul.getString("nomIndividu");					
					cst.setString(2, nomindividu);
					cst.registerOutParameter(1,java.sql.Types.INTEGER);
					cst.registerOutParameter(2,java.sql.Types.VARCHAR);
					boolean succes =cst.execute();
					int rNB=cst.getInt(1);
					System.out.println(" Le réalisateur: "+nomindividu+" a réalisé(e): "+rNB+" film(s)");
				}	*/
//Question5
				/*System.out.println("Entrer le nom d'un realisateur :");
				Scanner sc = new Scanner(System.in);
				String j =sc.next();
				System.out.println(j);				
				String rec ="Select distinct nomIndividu from ENS2004.Individu I, ens2004.Film Where realisateur=numindividu and nomIndividu='"+j+"'";
				CallableStatement cst =co.prepareCall("{call unTitre(?,?,?)}");	
				ResultSet resul=OutilsJDBC.exec1Requete(rec, co,0);
				while(resul.next()){
					String nomindividu=resul.getString("nomIndividu");
					cst.setString(1, nomindividu);
					cst.registerOutParameter(1,java.sql.Types.VARCHAR);
					cst.registerOutParameter (2,java.sql.Types.VARCHAR);
					cst.registerOutParameter (3,java.sql.Types.VARCHAR);										
					boolean succes =cst.execute();
					String prenomr=cst.getString(2);
					String titrer=cst.getString(3);					
					System.out.println(" Le réalisateur: "+nomindividu+" son prenom: "+prenomr+"  un de ses films : "+titrer);					
				}*/
//Question6
				System.out.println("Entrer le nom d'un Acteur :");
				Scanner sc = new Scanner(System.in);
				String j =sc.next();
				System.out.println(j);				
				String rec ="Select distinct nomIndividu from ENS2004.Individu I, ens2004.Film Where realisateur=numindividu and nomIndividu='"+j+"'";
				CallableStatement cst =co.prepareCall("{?=call unecomedie(?,?)}");
				ResultSet resul=OutilsJDBC.exec1Requete(rec, co,0);	
				PreparedStatement psm =co.prepareStatement("select distinct libellegenre from ens2004.Acteur A,ens2004.Film F, ens2004.Genre G, ens2004.Individu I, ens2004.genreFilm Z Where A.numIndividu=I.numIndividu and F.numfilm= A.numFIlm and Z.numfilm=F.numFilm and G.codegenre=Z.codeGenre and I.nomindividu=? ");				
				while(resul.next()){						
					String nomindividu=resul.getString("nomIndividu");
					cst.setString(2, nomindividu);
					cst.registerOutParameter(1,java.sql.Types.VARCHAR);
					cst.registerOutParameter (2,java.sql.Types.VARCHAR);					
					cst.registerOutParameter (3,java.sql.Types.VARCHAR);										
					boolean succes =cst.execute();	
					String titrecomedie=cst.getString(1);
					String prenomr=cst.getString(3);									
					System.out.println(" Le réalisateur: "+nomindividu+" son prenom: "+prenomr+"  une de ses comedie : "+titrecomedie);	
					psm.setString(1,nomindividu);
					ResultSet myres=psm.executeQuery();
					System.out.println("Il a joué dans (type film) : ");
					while (myres.next()){
						String typefilm=myres.getString("libellegenre");
						System.out.println("-"+typefilm);
						}				
				}
								
			//	cst.close();
				
				
				OutilsJDBC.closeConnection(co);
				System.out.println("connexion fermee");
	}
}

