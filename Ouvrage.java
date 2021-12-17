import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Ouvrage hérite de la classe Application
 */
public class Ouvrage extends Application {
	
	private String titre;
	private int nbDePage;
	private String dateSortie;
	private String commentaire;
	private Statement stmt;
	private Connection cn;
	
	/**
	 * constructeur
	 */
	public Ouvrage() {
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
	 * 
	 * @param primaryStage
	 * @throws Exception
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public void start(Stage primaryStage) throws Exception, SQLException, ClassNotFoundException {
		Ouvrage o = new Ouvrage();
		primaryStage.setTitle("Ajouter ouvrage");
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 15, 0, 15));
		
		/**
		 * création des zones de textes + bouton
		 */
		TextField titreTextField = new TextField();
		Label titreLabel = new Label("Titre :");
		
		TextField dateSortieTextField = new TextField();
		Label dateSortieLabel = new Label("Date de sortie :");
		
		TextArea commentaireTextArea = new TextArea();
		Label commentaireLabel = new Label("Commentaire :");
		
		Button btn = new Button("Ajouter");
		
		/*HBox nomBox = new HBox();
		nomBox.getChildren().addAll(nomLabel,nomTextField);
		HBox prenomBox = new HBox();
		prenomBox.getChildren().addAll(prenomLabel,prenomTextField);
		HBox mailBox = new HBox();
		mailBox.getChildren().addAll(mailLabel,mailTextField);
		HBox mdpBox = new HBox();
		mdpBox.getChildren().addAll(mdpLabel,mdpTextField);*/
		vbox.getChildren().addAll(titreLabel,titreTextField,dateSortieLabel,dateSortieTextField
				,commentaireLabel,commentaireTextArea,btn);
		
		/**
		 * action dans zones textes
		 */
		btn.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				o.setTitre(titreTextField.getText());
				o.setDateSortie(dateSortieTextField.getText());
				o.setCommentaire(commentaireTextArea.getText());


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
				 * ajout d'un ouvrage dans la BDD
				 */
				String sql = "INSERT INTO ouvrage (titre, nbDePage, dateSortie, commentaire) VALUES ('"+o.getTitre()+"','"
				+o.getNbDePage()+"','"+o.getDateSortie()+"','"+o.getCommentaire()+"')";
				try {
					int rs = stmt.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		);

		/**
		 * création scène
		 */
		Scene sc = new Scene(vbox,300,300);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	/**
	 * retour String titre
	 * @return
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * change la val this.titre
	 * @param titre
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * retour int nbDePage
	 * @return
	 */
	public int getNbDePage() {
		return nbDePage;
	}


	/**
	 * change la val this.nbDePage
	 * @param titre
	 */
	public void setNbDePage(int nbDePage) {
		this.nbDePage = nbDePage;
	}


	/**
	 * retour String dateSortie
	 * @return
	 */
	public String getDateSortie() {
		return dateSortie;
	}

	/**
	 * change la val this.dateSortie
	 * @param titre
	 */
	public void setDateSortie(String dateSortie) {
		this.dateSortie = dateSortie;
	}

	/**
	 * retour String commentaire
	 * @return
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * retour String commentaire
	 * @return
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}



}
