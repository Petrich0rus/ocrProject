import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * consulterOuvrage hérite de la classe Application
 */
public class consulterOuvrage extends Application {
	
	private Statement stmt;
	private Connection cn;
	private ResultSet rs;
	private Label connexionLabel;

	/**
	 * laucher
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);


	}
	/**
	 * 
	 * @param primaryStage
	 * @throws Exception
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void start(Stage primaryStage) throws Exception, SQLException, ClassNotFoundException {
		
		Connexion c = new Connexion();
		primaryStage.setTitle("Consulter les ouvrages");
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 15, 0, 15));
		
		/**
		 * création du path et des logs vers la BDD
		 */
		String url="jdbc:mysql://localhost:3306/logiciel?serverTimezone=UTC";
		String login="root";
		String password="root"; 

		/**
		 * accès et actions sur la BDD
		 */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			cn  = DriverManager.getConnection(url,login,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stmt = cn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		/**
		 * accès à l'ouvrage en question
		 */
		try {
			String sql1 = "SELECT titre, nbDePage, dateSortie, commentaire"
					+ " FROM ouvrage INNER JOIN utilisateur ON ouvrage.idUtilisateur"
					+ "=utilisateur.idUtilisateur where mail like "
					+ "'"+c.getLogin()+"'";
			 rs = stmt.executeQuery(sql1);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(rs.next()) {
			String t = rs.getString("titre");
			String n = rs.getString("nbDePage");
			String d = rs.getString("dateSortie");
			String co = rs.getString("commentaire");
			String Newligne = System.getProperty("line.separator");
			connexionLabel = new Label("Titre : "+t+" | Nombre de pages : "+n+" | "
					+ "Date de sortie : "+d+" | Commentaire : "+co+Newligne);
			vbox.getChildren().add(connexionLabel);
		}
		
	
		
		Scene sc = new Scene(vbox,300,300);
		primaryStage.setScene(sc);
		primaryStage.show();
		
	}

}
