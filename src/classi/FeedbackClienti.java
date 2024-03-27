package classi;

import javax.swing.*;//questo  è per la finestra
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FeedbackClienti extends JFrame{
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/gestionaleHoltelProva";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Purosangue90!";
	
	//********************************* QUERY PER STANZE **********************
	private static final String CREATE_TABLE_QUERY_FEEDBACK_CLIENTE = "CREATE TABLE IF NOT EXISTS feedbackCliente(" +
	                                                 "id INT PRIMARY KEY AUTO_INCREMENT,"  +
			                                         "id_prenotazione INT NOT NULL,"       +
	                                                 "nome_prenotazione VARCHAR(100) NOT NULL,"+
			                                         "pulizia_camera INT NOT NULL,"         +
	                                                 "cortesia_del_personale INT NOT NULL,"    +
	                                                 "qualita_cibo INT NOT NULL,"          +
	                                                 "posizione_albergo INT NOT NULL,"     +
	                                                 "servizi_extra INT ,"                 +
	                                                 "note_aggiuntive TEXT ,"              +
	                                                 "risposta_del_personale TEXT"         +
			                                         ")";
	
	
	
	
	private static final String INSERT_QUERY_FEEDBACK_CLIENTE = "INSERT INTO feedbackCliente(id_prenotazione,nome_prenotazione,pulizia_camera,cortesia_del_personale,qualita_cibo,posizione_albergo,servizi_extra,note_aggiuntive,risposta_del_personale) VALUES (?,?,?,?,?,?,?,?,?)";
	
	
	private static final String SELECT_ALL_QUERY_FEEDBACK_CLIENTE = "SELECT * FROM feedbackCliente";
	
	private static final String UPDATE_QUERY_FEEDBACK_CLIENTE = "UPDATE feedbackCliente SET risposta_del_personale = ? WHERE id=?";
	
	
	
	
	private JTextArea outputAreaFeedbackCliente;
	
	private static final String PASSWORD = "user";
	

	public FeedbackClienti(){
		
		setTitle("Feedback Clienti");
		setSize(400,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		
		//*creazione di un pannello tabulare********************
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//creazione di pannelli per il nostro tabbedPane
		JPanel panel1 = new JPanel(new BorderLayout());
		
		outputAreaFeedbackCliente = new JTextArea();
		outputAreaFeedbackCliente.setEditable(false);
		int red = 221;
		int green = 160;
		int blue = 229;

		Color customColor = new Color(red, green, blue);
		outputAreaFeedbackCliente.setBackground(customColor);
		
		
		JScrollPane scrollPaneFeedbackClienti = new JScrollPane(outputAreaFeedbackCliente);
		
		ImageIcon immagine = new ImageIcon("C:\\Users\\cinqu\\OneDrive\\Desktop\\managerHotel20Marzo\\bannerHotel.png");
		int width = 400; // Larghezza desiderata
		int height = 110; // Altezza desiderata
		Image img = immagine.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		immagine = new ImageIcon(img);
		JLabel imageLabel = new JLabel(immagine);
		JPanel imagePanelFeedbackClienti = new JPanel(new BorderLayout());
		imagePanelFeedbackClienti.setPreferredSize(new Dimension(400,110));
		imagePanelFeedbackClienti.add(imageLabel,BorderLayout.CENTER);
		
		
		
         Font buttonFont = new Font("Arial", Font.BOLD, 12);
		
		JButton addButtonFeedbackClienti = new JButton("Aggiungi");
		addButtonFeedbackClienti.setFont(buttonFont);
		JButton viewButtonFeedbackClienti = new JButton("Aggiorna");
		viewButtonFeedbackClienti.setFont(buttonFont);
		JButton updateButtonFeedbackClienti = new JButton("Rispondi");
		updateButtonFeedbackClienti.setFont(buttonFont);
		
		
		addButtonFeedbackClienti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//metodo da inserire!!!!!!!
				aggiungiFeedbackClienti();
				
			}
		});
		
		viewButtonFeedbackClienti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//metodo da inserire!!!!!!!
				visualizzaFeedbackClienti();
				
			}
		});
		
		
		updateButtonFeedbackClienti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//metodo da inserire!!!!!!!
				modificaFeedbackClienti();
			}
		});
		
		

		
       JPanel buttonPanelFeedbackClienti = new JPanel();
		
		buttonPanelFeedbackClienti.setLayout(new GridLayout(1,4));//1 riga e 4 colonne
		buttonPanelFeedbackClienti.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)); // Aggiunge un margine al pannello
		buttonPanelFeedbackClienti.setBackground(Color.GRAY);

		int hGap = 5; // Spazio orizzontale tra i bottoni
		int vGap = 5; // Spazio verticale tra i bottoni
		GridLayout layout = (GridLayout) buttonPanelFeedbackClienti.getLayout();
		layout.setHgap(hGap);
		layout.setVgap(vGap);
		
		
		buttonPanelFeedbackClienti.add(viewButtonFeedbackClienti);
		buttonPanelFeedbackClienti.add(addButtonFeedbackClienti);
		buttonPanelFeedbackClienti.add(updateButtonFeedbackClienti);
		
		
		JPanel pannelloUnito = new JPanel(new BorderLayout());
		pannelloUnito.add(imagePanelFeedbackClienti,BorderLayout.CENTER);
		pannelloUnito.add(buttonPanelFeedbackClienti,BorderLayout.SOUTH);
		
		panel1.add(scrollPaneFeedbackClienti,BorderLayout.CENTER);
		panel1.add(pannelloUnito,BorderLayout.SOUTH);
		
		tabbedPane.addTab("I VOSTRI FEEDBACK               ", panel1);
		tabbedPane.setFont(new Font("Arial",Font.BOLD,12));
		
		

		mainPanel.add(tabbedPane);	
		
		add(mainPanel);
		
	
	}
	
	
	
	private void createTableFeedbackClientiIFNotExists() {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(CREATE_TABLE_QUERY_FEEDBACK_CLIENTE);
			stmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}



   private void visualizzaFeedbackClienti() {
	//metodo per visualizzare
	outputAreaFeedbackCliente.setText("");//togliamo tutto ciò che eventualmente c'era all'interno e lo ripuliamo
	try {
		Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY_FEEDBACK_CLIENTE);
		while(rs.next()) {
			int id = rs.getInt("id");
			int id_prenotazione = rs.getInt("id_prenotazione");
			String nome_prenotazione = rs.getString("nome_prenotazione");
			int pulizia_camera = rs.getInt("pulizia_camera");
			int cortesia_del_personale = rs.getInt("cortesia_del_personale");
			int qualita_cibo = rs.getInt("qualita_cibo");
			int posizione_albergo = rs.getInt("posizione_albergo");
			int servizi_extra = rs.getInt("servizi_extra");
			String note_aggiuntive = rs.getString("note_aggiuntive");
			String risposta_del_personale = rs.getString("risposta_del_personale");
			outputAreaFeedbackCliente.append("ID : " + id + " ID PRENOTAZIONE : " + id_prenotazione + " , Nome della prenotazione : " + nome_prenotazione + "- Pulizia della camera VOTO : " + pulizia_camera + "  , Cortesia del personale VOTO : " + cortesia_del_personale + "   , Qualità del cibo VOTO : " + qualita_cibo + "   ,Posizione dell'albergo VOTO: " + posizione_albergo + "   ,Servizi EXTRA VOTO: " + servizi_extra + "  ,Note aggiuntive : " + note_aggiuntive + "   , Risposta del personale : " + risposta_del_personale +"\n");
		}
		
		rs.close();
		stmt.close();
		conn.close();
	}catch(SQLException e) {
		e.printStackTrace();
	}
}



   private void aggiungiFeedbackClienti() {
	    // qui inseriamo dati utente
	    int id_prenotazione = Integer.parseInt(JOptionPane.showInputDialog("Grazie per aver soggiornato presso il nostro Hotel!\nLa tua opinione è molto importante per noi!\nPer lasciare il tuo feedback inserisci come prima cosa\nl'ID della tua prenotazione indicata in fattura\n(puoi chiedere in reception se non ne sei in possesso) : "));
	    String nome_prenotazione = JOptionPane.showInputDialog("Inserisci il nome con cui hai effettuato la prenotazione : ");  
	    if (nome_prenotazione != null && !nome_prenotazione.isEmpty()) {
	        int pulizia_camera;
	        do {
	            pulizia_camera = Integer.parseInt(JOptionPane.showInputDialog("Ti chiediamo di dare un VOTO da 1 a 5 ai nostri servizi.\nPULIZIA DELLA CAMERA :"));
	            if (pulizia_camera < 1 || pulizia_camera > 5) {
	                JOptionPane.showMessageDialog(null, "Inserire un valore da 1 a 5.", "Errore", JOptionPane.ERROR_MESSAGE);
	            }
	        } while (pulizia_camera < 1 || pulizia_camera > 5);

	        int cortesia_del_personale;
	        do {
	            cortesia_del_personale = Integer.parseInt(JOptionPane.showInputDialog("CORTESIA DEL PERSONALE :"));
	            if (cortesia_del_personale < 1 || cortesia_del_personale > 5) {
	                JOptionPane.showMessageDialog(null, "Inserire un valore da 1 a 5.", "Errore", JOptionPane.ERROR_MESSAGE);
	            }
	        } while (cortesia_del_personale < 1 || cortesia_del_personale > 5);

	        int qualita_cibo;
	        do {
	            qualita_cibo = Integer.parseInt(JOptionPane.showInputDialog("QUALITA' DEL CIBO :"));
	            if (qualita_cibo < 1 || qualita_cibo > 5) {
	                JOptionPane.showMessageDialog(null, "Inserire un valore da 1 a 5.", "Errore", JOptionPane.ERROR_MESSAGE);
	            }
	        } while (qualita_cibo < 1 || qualita_cibo > 5);

	        int posizione_albergo;
	        do {
	            posizione_albergo = Integer.parseInt(JOptionPane.showInputDialog("POSIZIONE DELL'ALBERGO :\n(vicino alle maggiori attrazioni da vedere,facile da raggiungere ecc) "));
	            if (posizione_albergo < 1 || posizione_albergo > 5) {
	                JOptionPane.showMessageDialog(null, "Inserire un valore da 1 a 5.", "Errore", JOptionPane.ERROR_MESSAGE);
	            }
	        } while (posizione_albergo < 1 || posizione_albergo > 5);

	        int servizi_extra = 0; // Inizializziamo a 0
	        String note_aggiuntive = ""; // Inizializziamo come stringa vuota
	        String risposta_del_personale = ""; // Inizializziamo come stringa vuota
	        // Chiediamo all'utente di inserire i valori solo se vuole
	        String servizi_extraInput = JOptionPane.showInputDialog("SERVIZI EXTRA\n(Puoi lasciare in bianco nel caso non ne avessi usufruito) :");
	        if (servizi_extraInput != null && !servizi_extraInput.isEmpty()) {
	            servizi_extra = Integer.parseInt(servizi_extraInput);
	        }
	        
	        String note_aggiuntiveInput = JOptionPane.showInputDialog("Hai altro da aggiungere? : ");
	        if (note_aggiuntiveInput != null) {
	            note_aggiuntive = note_aggiuntiveInput;
	        }

	        String risposta_del_personaleInput = JOptionPane.showInputDialog("Ti chiediamo di lasciare in bianco questo campo per permettere al nostro staff di rispondere ad eventuali tue annotazioni:");
	        if (risposta_del_personaleInput != null) {
	            risposta_del_personale = risposta_del_personaleInput;
	        }

	        // Inserimento dei feedback nel database
	        insertFeedbackClienti(id_prenotazione, nome_prenotazione, pulizia_camera, cortesia_del_personale, qualita_cibo, posizione_albergo, servizi_extra, note_aggiuntive, risposta_del_personale);
	        outputAreaFeedbackCliente.append("Grazie per aver registrato il tuo feedback!  ID PRENOTAZIONE : " + id_prenotazione + " , Nome della prenotazione : " + nome_prenotazione + "- Pulizia della camera VOTO : " + pulizia_camera + "  , Cortesia del personale VOTO : " + cortesia_del_personale + "   , Qualità del cibo VOTO : " + qualita_cibo + "   ,Posizione dell'albergo VOTO: " + posizione_albergo + "   ,Servizi EXTRA VOTO: " + servizi_extra + "  ,Note aggiuntive : " + note_aggiuntive + "   , Risposta del personale : " + risposta_del_personale + "\n");
	    }
	}


private void insertFeedbackClienti(int id_prenotazione,String nome_prenotazione,int pulizia_camera,int cortesia_del_personale,int qualita_cibo,int posizione_albergo,int servizi_extra,String note_aggiuntive,String risposta_del_personale) {
	try {
		Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
		PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY_FEEDBACK_CLIENTE);
		pstmt.setInt(1, id_prenotazione);
		pstmt.setString(2,nome_prenotazione);
		pstmt.setInt(3, pulizia_camera);
		pstmt.setInt(4, cortesia_del_personale);
		pstmt.setInt(5, qualita_cibo);
		pstmt.setInt(6, posizione_albergo);
		pstmt.setInt(7, servizi_extra);
		pstmt.setString(8,note_aggiuntive);
		pstmt.setString(9,risposta_del_personale);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
	}catch(SQLException e) {
		e.printStackTrace();
	}
}

//Aggiornamento del metodo modificaFeedbackClienti per richiedere la password
private void modificaFeedbackClienti() {
// Chiedi all'utente di inserire la password
JOptionPane.showMessageDialog(null, "Questo servizio è accessibile solo dal personale autorizzato!");
String password = JOptionPane.showInputDialog("Inserisci la password per accedere alla modifica:");

// Controlla se la password inserita è corretta
if (checkPassword(password)) {
   // Inserisci il codice per la modifica dei feedback qui
   int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'id del feedback da rispondere: "));
   String risposta_del_personale = JOptionPane.showInputDialog("Inserisci la risposta al feedback del cliente:");
   updateFeedbackClienti(id,risposta_del_personale);
   outputAreaFeedbackCliente.append("Risposta inserita su ID:" + id + "  ,Risposta inserita : " + risposta_del_personale);
} else {
   JOptionPane.showMessageDialog(null, "Password non corretta. Accesso negato.", "Errore", JOptionPane.ERROR_MESSAGE);
}
}



private void updateFeedbackClienti(int id,String risposta_del_personale) {
	try {
		Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
		PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY_FEEDBACK_CLIENTE);
		pstmt.setString(1,risposta_del_personale);
		pstmt.setInt(2, id);
		pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		
	}catch(SQLException e) {
		e.printStackTrace();
	}
}

//Metodo per controllare se la password inserita corrisponde a quella predefinita
private boolean checkPassword(String password) {
 return PASSWORD.equals(password);
}






	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FeedbackClienti feedbackClienti = new FeedbackClienti();
				feedbackClienti.setVisible(true);
				feedbackClienti.createTableFeedbackClientiIFNotExists();
				feedbackClienti.visualizzaFeedbackClienti();
				
				}
		});

	

	}

}