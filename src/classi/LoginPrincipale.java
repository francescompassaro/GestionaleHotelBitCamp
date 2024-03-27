
package classi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class LoginPrincipale extends JFrame {
	
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/utentiHotel";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Francesco9000";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM utenti";

	// LABELS
	JLabel lblTitoloAPP = new JLabel("Login al gestionale dell'Hotel", SwingConstants.LEFT);
	JLabel lblUserName = new JLabel("Nome Utente", SwingConstants.LEFT);
	JLabel lblPasswd = new JLabel("Password", SwingConstants.LEFT);

	// TEXT FIELDS
	JTextField nomeTextBox = new JTextField(20);
	JPasswordField passwordTextBox = new JPasswordField(20);

	// FONTS
	Font f1 = new Font("Calibri", Font.BOLD, 25);
	Font f2 = new Font("Liberation Sans", Font.PLAIN, 15);
	Font f3 = new Font("Liberation Sans", Font.PLAIN, 15);

	// BUTTONS
	JButton btnAccedi = new JButton("ACCEDI");

	public LoginPrincipale() {

		super("Finestra di accesso");
		// DEFINIZIONE PANNELLO 1
		JPanel pannello1 = new JPanel();
		pannello1.setLayout(new GridLayout(11, 2));
		pannello1.setBorder(BorderFactory.createEmptyBorder(20, 60, 0, 60));

		lblTitoloAPP.setFont(f1);
		lblTitoloAPP.setForeground(Color.black);
		lblUserName.setFont(f2);
		lblUserName.setForeground(Color.gray);
		lblPasswd.setFont(f2);
		lblPasswd.setForeground(Color.gray);
		

		nomeTextBox.setEditable(true);
		passwordTextBox.setEditable(true);
		passwordTextBox.setEchoChar('☢');

		pannello1.add(lblTitoloAPP);
		pannello1.add(new JLabel(""));
		pannello1.add(lblUserName);
		pannello1.add(nomeTextBox);
		pannello1.add(lblPasswd);
		pannello1.add(passwordTextBox);
		pannello1.add(new JLabel(""));
		pannello1.setPreferredSize(new Dimension(40, 160));
		pannello1.setBackground(Color.blue);
		pannello1.setForeground(Color.white);
		pannello1.add(btnAccedi);
		pannello1.setBackground(new java.awt.Color(255, 251, 242));

		// DEFINIZIONE PANNELLO 2
		JPanel pannelloExtra = new JPanel();
		pannelloExtra.setLayout(new FlowLayout());
		pannelloExtra.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));
		pannelloExtra.setSize(new Dimension(500, 30));

		ImageIcon bgLabel = new ImageIcon(LoginPrincipale.class.getResource("../img/reception.jpg"));

//		Image image = bgLabel.getImage(); // transform it
//		Image newimg = image.getScaledInstance(500, 400, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//		bgLabel = new ImageIcon(newimg);
		JLabel myLabel = new JLabel(bgLabel);
		pannelloExtra.add(myLabel);
		

		// DEFINIZIONE PANNELLO PADRE
		JSplitPane pannelloPadre = new JSplitPane(SwingConstants.HORIZONTAL, pannello1, pannelloExtra);
		pannelloPadre.setDividerLocation(300);
		setContentPane(pannelloPadre);
		setMinimumSize(new Dimension(400, 500));

		// IMPOSTAZIONI INIZIALI
		setSize(450, 650);
		setLocation(600, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		
		

		// EVENTO PULSANTE ACCESSO
		btnAccedi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String ruolo  = nomeTextBox.getText();
				System.out.println("aaa");
				System.out.println(funzioneAccesso(nomeTextBox.getText(), passwordTextBox.getPassword()));
				System.out.println(funzioneAccessoRuolo(nomeTextBox.getText(), passwordTextBox.getPassword()));
				if (funzioneAccesso(nomeTextBox.getText(), passwordTextBox.getPassword()).equals("SI") 
						&& funzioneAccessoRuolo(nomeTextBox.getText(), passwordTextBox.getPassword()).equals("Receptionist")) {
					System.out.println("Accesso all'area reception");
					System.out.println(funzioneAccesso(nomeTextBox.getText(), passwordTextBox.getPassword()));
//					SwingUtilities.invokeLater(() -> new InterfacciaPrincipale());
					InterfacciaPrincipale x = new InterfacciaPrincipale(ruolo);
					x.setVisible(true);
				}else if (funzioneAccesso(nomeTextBox.getText(), passwordTextBox.getPassword()).equals("SI")
						&& funzioneAccessoRuolo(nomeTextBox.getText(), passwordTextBox.getPassword()).equals("Amministrazione")) {
					System.out.println("accesso nel caso amministrazione");
					InterfacciaPrincipale x = new InterfacciaPrincipale(ruolo);
					x.setVisible(true);
				} else if (funzioneAccesso(nomeTextBox.getText(), passwordTextBox.getPassword()).equals("NO")){
					System.out.println("credenziali sbagliate");
					JOptionPane.showMessageDialog(null, "Credenziali errate");
					SwingUtilities.invokeLater(() -> new LoginPrincipale());
				}
				setEnabled(false);
				setVisible(false);
			}
		});
	}

	public String funzioneAccesso(String nomeInserito, char[] passwdInserita) {

		String risposta = "";
//		String destinazioneRuolo = "";
		String passwdOK = new String(passwdInserita);
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
			ArrayList<String> arrayUserName = new ArrayList<>();
			ArrayList<String> arrayPasswd = new ArrayList<>();
			ArrayList<String> arrayRuolo = new ArrayList<>();

			while (rs.next()) {
				String ruolo = rs.getString("ruolo");
				String userName = rs.getString("nomeutente");
				String password = rs.getString("passwords");
				arrayUserName.add(userName);
				arrayPasswd.add(password);
				arrayRuolo.add(ruolo);
			}
			if (arrayUserName.contains(nomeInserito)) { // CONTROLLO DELLA PRESENZA DEL NOME UTENTE NEL DB
				for (int i = 0; i < arrayUserName.size(); i++) { // SE NOME UTENTE C'È FAI IL CONTROLLO DELLA PASSWORD
						if (arrayUserName.get(i).equals(nomeInserito) && arrayPasswd.get(i).equals(passwdOK)) {
							risposta = "SI";
							return risposta;
					}
				}
			} else {
				risposta = "NO";
				System.out.println("Nell'else");
				return risposta;
								
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risposta;
	}

	public String funzioneAccessoRuolo(String nomeInserito, char[] passwdInserita) {
		String destinazioneRuolo = "";
		String passwdOK = new String(passwdInserita);
		try {
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);
			ArrayList<String> arrayUserName = new ArrayList<>();
			ArrayList<String> arrayPasswd = new ArrayList<>();
			ArrayList<String> arrayRuolo = new ArrayList<>();

			while (rs.next()) {
				String ruolo = rs.getString("ruolo");
				String userName = rs.getString("nomeutente");
				String password = rs.getString("passwords");
				arrayUserName.add(userName);
				arrayPasswd.add(password);
				arrayRuolo.add(ruolo);
			}
			if (arrayUserName.contains(nomeInserito)) { // CONTROLLO DELLA PRESENZA DEL NOME UTENTE NEL DB
				for (int i = 0; i < arrayUserName.size(); i++) { // SE NOME UTENTE È PRESENTE FAI IL CONTROLLO DELLA PASSWORD
//					System.out.println(i);
					if (arrayRuolo.get(i).equals("Receptionist")) {
						destinazioneRuolo = "Receptionist";
						if (arrayUserName.get(i).equals(nomeInserito) && arrayPasswd.get(i).equals(passwdOK)) {

							destinazioneRuolo = "Receptionist";
							return destinazioneRuolo;
						}
					}
					else if(arrayRuolo.get(i).equals("Amministrazione")){
						if (arrayUserName.get(i).equals(nomeInserito) && arrayPasswd.get(i).equals(passwdOK)) {
							
							destinazioneRuolo = "Amministrazione";
							return destinazioneRuolo;

						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	
		
		return destinazioneRuolo;
	}

}
