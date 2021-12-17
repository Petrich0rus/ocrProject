import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Inscription hérite d'Application
 */
public class Inscription extends Application {
	
	private String nom;
	private String prenom;
	private String mail;
	private String mdp;
	private Statement stmt;
	private Connection cn;
	private ResultSet rs1;
	
	/**
	 * constructeur
	 */
	public Inscription() {
	}

	/**
	 * laucher
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);


	}
	
	/**
	 * Création fenêtre Inscription,
	 * Zones de textes (différent champ relatif à l'insciption ) + bouton 
	 * @param primaryStage
	 * @throws Exception
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void start(Stage primaryStage) throws Exception, SQLException, ClassNotFoundException {
		Inscription i = new Inscription();
		primaryStage.setTitle("Inscription");
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 15, 0, 15));
		
		TextField nomTextField = new TextField();
		Label nomLabel = new Label("Nom :");
		
		TextField prenomTextField = new TextField();
		Label prenomLabel = new Label("Pr�nom :");
		
		TextField mailTextField = new TextField();
		Label mailLabel = new Label("Adresse mail :");
		
		TextField mdpTextField = new TextField();
		Label mdpLabel = new Label("Mot de passe :");
		
		Button btn = new Button("Valider");
		
		/*HBox nomBox = new HBox();
		nomBox.getChildren().addAll(nomLabel,nomTextField);
		HBox prenomBox = new HBox();
		prenomBox.getChildren().addAll(prenomLabel,prenomTextField);
		HBox mailBox = new HBox();
		mailBox.getChildren().addAll(mailLabel,mailTextField);
		HBox mdpBox = new HBox();
		mdpBox.getChildren().addAll(mdpLabel,mdpTextField);*/

		/**
		 * ajout des différentes saisie à Children
		 */
		vbox.getChildren().addAll(nomLabel,nomTextField,prenomLabel,prenomTextField, mailLabel, mailTextField,mdpLabel,mdpTextField,btn);
		
		/**
		 * action dans zones textes
		 */
		btn.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				i.setNom(nomTextField.getText());
				i.setPrenom(prenomTextField.getText());
				i.setMail(mailTextField.getText());
				i.setMdp(mdpTextField.getText());
				
				i.testInscription(i);
			
			}
		}
		);

		/**
		 * création scène
		 */
		BorderPane root = new BorderPane();
		Scene sc = new Scene(vbox,300,300);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	/**
	 * test de connection à la BDD pour l'inscription 
	 * @param i
	 */
	public void testInscription(Inscription i) {
	
		String url="jdbc:mysql://localhost:3306/logiciel?serverTimezone=UTC";
		String login="root";
		String password="root"; 

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
		try {
			String sql1 = "SELECT * FROM utilisateur where mail like '"+i.getMail()+"'";
			 rs1 = stmt.executeQuery(sql1);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/**
		 * est ce que l'utilisateur est déjà inscrit ?
		 */
		try {
			if(rs1.next()) {
		
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText("Erreur !");
					alert.setContentText("L'adresse mail est d�j� utilis� !");

					alert.showAndWait();
				
			}
			/**
			 * sinon effectue l'inscription
			 */
			else
			{
			String sql = "INSERT INTO utilisateur (nom, prenom, mail, password) VALUES ('"+i.getNom()+"','"
			+i.getPrenom()+"','"+i.getMail()+"','"+i.getMdp()+"')";
			try {
				int rs = stmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("Success !");
			alert.setContentText("Vous avez �t� enregistr� avec succ�s !");

			alert.showAndWait();
}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * retour String nom
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * change la val this.nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * retour String prenom
	 * @return
	 */
	public String getPrenom() {
		return prenom;
	}

/**
	 * change la val this.prenom
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * retour String mail
	 * @return
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * change la val this.mail
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * retour String mdp
	 * @return
	 */
	public String getMdp() {
		return mdp;
	}

	/**
	 * change la val this.mdp
	 * @param mdp
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

}
