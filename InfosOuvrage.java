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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * InfosOuvrage hérite de la classe Application
 */
public class InfosOuvrage extends Application {
	
	private String titre;
	private Statement stmt;
	private Connection cn;
	private ResultSet rs;
	private Label connexionLabel;

	/**
	 * constructeur
	 */
	public InfosOuvrage() {
		
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
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		/**
		 * création de l'onglet info ouvrage
		 */
		InfosOuvrage i = new InfosOuvrage();
		primaryStage.setTitle("Consulter les ouvrages");
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(0, 15, 0, 15));
		
		/**
		 * création des zones de textes + bouton 
		 */
		TextField titreTextField = new TextField();
		Label titreLabel = new Label("Consulter les informations de quel ouvrage ?");
		
		Button btn = new Button("Consulter");
		
		vbox.getChildren().addAll(titreLabel,titreTextField, btn);

		/**
		 * action dans zone texte
		 */
		btn.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				i.setTitre(titreTextField.getText());
				
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

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/**
				 * accès et actions sur la BDD
				 */
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
				 * renvoie les info sur tel ouvrage
				 */
				try {
					String sql1 = "SELECT titre, nbDePage, dateSortie, commentaire"
							+ " FROM ouvrage where titre like '"+i.getTitre()+"'";
						
					 rs = stmt.executeQuery(sql1);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
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
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/**
		 		* création scène
		 		*/
				Scene sc = new Scene(vbox,300,300);
				primaryStage.setScene(sc);
				primaryStage.show();
				
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

}
