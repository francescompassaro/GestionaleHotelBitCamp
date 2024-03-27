package classi;

import javax.swing.*;//questo  è per la finestra
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InterfacciaPrincipale extends JFrame {
	
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/gestionaleHoltelProva";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Francesco9000";
	
	//********************************* QUERY PER STANZE **********************
	private static final String CREATE_TABLE_QUERY_STANZE = "CREATE TABLE IF NOT EXISTS stanze(" +
	                                                 "id INT PRIMARY KEY AUTO_INCREMENT,"  +
			                                         "tipologia VARCHAR(100)NOT NULL,"     +
	                                                 "prezzo DOUBLE NOT NULL,"              +
	                                                 "numero_persone INT NOT NULL,"        +
	                                                 "disponibilita BOOLEAN NOT NULL"      +
			                                         ")";
	
	private static final String INSERT_QUERY_STANZE = "INSERT INTO stanze(tipologia,prezzo,numero_persone,disponibilita) VALUES (?,?,?,?)";
	
	
	private static final String SELECT_ALL_QUERY_STANZE = "SELECT * FROM stanze";
	
	private static final String UPDATE_QUERY_STANZE = "UPDATE stanze SET tipologia =?,prezzo = ?,numero_persone = ?,disponibilita = ? WHERE id=?";
	
	private static final String DELETE_QUERY_STANZE = "DELETE FROM stanze WHERE id = ?";
	
	//********************************* QUERY PER ANAGRAFICA OSPITI **********************
	private static final String CREATE_TABLE_QUERY_ANAGRAFICA_OSPITI = "CREATE TABLE IF NOT EXISTS anagraficaOspiti(" +
                                                                       "id INT PRIMARY KEY AUTO_INCREMENT,"            +
                                                                       "nome VARCHAR(50)NOT NULL,"                     +
                                                                       "cognome VARCHAR(50) NOT NULL,"                 +
                                                                       "codice_fiscale VARCHAR(50),"      +
                                                                       "tipo_e_nr_documento VARCHAR(100) NOT NULL "    +
                                                                        ")";

    private static final String INSERT_QUERY_ANAGRAFICA_OSPITI = "INSERT INTO anagraficaOspiti(nome,cognome,codice_fiscale,tipo_e_nr_documento ) VALUES (?,?,?,?)";


    private static final String SELECT_ALL_QUERY_ANAGRAFICA_OSPITI = "SELECT * FROM anagraficaOspiti";

    private static final String UPDATE_QUERY_ANAGRAFICA_OSPITI = "UPDATE anagraficaOspiti SET nome =?,cognome = ?,codice_fiscale = ?,tipo_e_nr_documento = ? WHERE id=?";
 
    private static final String DELETE_QUERY_ANAGRAFICA_OSPITI = "DELETE FROM anagraficaOspiti WHERE id = ?";
    
    ////********************************* QUERY PER SERVIZI PLUS **********************
	private static final String CREATE_TABLE_QUERY_SERVIZI_PLUS = "CREATE TABLE IF NOT EXISTS serviziPlus(" +
                                                                   "id INT PRIMARY KEY AUTO_INCREMENT,"     +
                                                                   "tipo_servizio VARCHAR(50) NOT NULL,"    + 
                                                                   "prezzo DOUBLE NOT NULL"                 +
                                                                    ")";

    private static final String INSERT_QUERY_SERVIZI_PLUS = "INSERT INTO serviziPlus(tipo_servizio,prezzo) VALUES (?,?)";


    private static final String SELECT_ALL_QUERY_SERVIZI_PLUS = "SELECT * FROM serviziPlus";

    private static final String UPDATE_QUERY_SERVIZI_PLUS = "UPDATE serviziPlus SET tipo_servizio =?,prezzo = ? WHERE id=?";

   private static final String DELETE_QUERY_SERVIZI_PLUS = "DELETE FROM serviziPlus WHERE id = ?";
   
	//********************************* QUERY PER FORMULA DI SOGGIORNO **********************
	private static final String CREATE_TABLE_QUERY_FORMULA_DI_SOGGIORNO = "CREATE TABLE IF NOT EXISTS formulaDiSoggiorno(" +
	                                                                      "id INT PRIMARY KEY AUTO_INCREMENT,"  +
			                                                              "tipologia VARCHAR(100)NOT NULL,"     +
	                                                                      "prezzo DOUBLE NOT NULL"              +
			                                                               ")";
	
	private static final String INSERT_QUERY_FORMULA_DI_SOGGIORNO = "INSERT INTO formulaDiSoggiorno(tipologia,prezzo) VALUES (?,?)";
	
	
	private static final String SELECT_ALL_QUERY_FORMULA_DI_SOGGIORNO = "SELECT * FROM formulaDiSoggiorno";
	
	private static final String UPDATE_QUERY_FORMULA_DI_SOGGIORNO = "UPDATE formulaDiSoggiorno SET tipologia =?,prezzo = ? WHERE id=?";
	
	private static final String DELETE_QUERY_FORMULA_DI_SOGGIORNO = "DELETE FROM formulaDiSoggiorno WHERE id = ?";
	
	
	//******************************** QUERY PER DIPENDENTI ************************************************
	private static final String CREATE_TABLE_QUERY_DIPENDENTI = "CREATE TABLE IF NOT EXISTS dipendenti(" +
                                                                  "id INT PRIMARY KEY AUTO_INCREMENT,"   +
                                                                  "nome VARCHAR(50) NOT NULL,"           +
                                                                  "cognome VARCHAR(50) NOT NULL,"        +
                                                                  "codice_fiscale VARCHAR(50) NOT NULL," +
                                                                  "tipo_di_contratto VARCHAR(100) NOT NULL,"+
                                                                  "mansione VARCHAR(100) NOT NULL,"       +
                                                                  "retribuzione_mensile DOUBLE NOT NULL"  +
                                                                  ")";

    private static final String INSERT_QUERY_DIPENDENTI = "INSERT INTO dipendenti(nome,cognome,codice_fiscale,tipo_di_contratto,mansione,retribuzione_mensile) VALUES (?,?,?,?,?,?)";


    private static final String SELECT_ALL_QUERY_DIPENDENTI = "SELECT * FROM dipendenti";

    private static final String UPDATE_QUERY_DIPENDENTI = "UPDATE dipendenti SET nome = ?,cognome = ?,codice_fiscale = ?,tipo_di_contratto = ?,mansione = ?,retribuzione_mensile = ? WHERE id=?";

    private static final String DELETE_QUERY_DIPENDENTI = "DELETE FROM dipendenti WHERE id = ?";
	
	
	
	//Creazione di tutti i JtextArea che metteremo in ogni panel apposito
	private JTextArea outputAreaStanze;
	private JTextArea outputAreaAnagraficaOspiti;
	private JTextArea outputAreaServiziPlus;
	private JTextArea outputAreaFormulaDiSoggiorno;
	private JTextArea outputAreaDipendenti;
	public  JPanel panel1;
	public  JPanel panel2;
	public  JPanel panel3;
	public  JPanel panel4;	
	public  JPanel panel5;
	public  JPanel panel6;
	public  JPanel imagePanelStanze;
	public  JScrollPane scrollPaneStanze;
	public  JPanel buttonPanelStanze;
	public  JButton addButtonStanze;
	public  JButton viewButtonStanze;
	public  JButton updateButtonStanze;
	public  JButton deleteButtonStanze;
	public  JTabbedPane tabbedPane;
	

	
	
	public InterfacciaPrincipale(String ruolo) {
		setTitle("GESTIONALE HOTEL");
		setSize(800,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		createTableStanzeIFNotExists();
		createTableAnagraficaOspitiIFNotExists();
		createTableServiziPlusIFNotExists();
		createTableFormulaDiSoggiornoIFNotExists();	
		createTableDipendentiIFNotExists();
		
	
		// *creazione di un pannello tabulare********************
		this.tabbedPane = new JTabbedPane();
		
		//creazione di pannelli per il nostro tabbedPane
		panel1 = new JPanel(new BorderLayout());
		panel2 = new JPanel(new BorderLayout());
		JPanel panel3 = new JPanel(new BorderLayout());
		JPanel panel4 = new JPanel(new BorderLayout());
		JPanel panel5 = new JPanel(new BorderLayout());
		JPanel panel6 = new JPanel(new BorderLayout());

		//richiamo dei vari outputArea che metteremo in ogni panel corrispondente
		outputAreaStanze = new JTextArea();
		outputAreaStanze.setEditable(false);
		outputAreaStanze.setBackground(Color.LIGHT_GRAY);
		outputAreaAnagraficaOspiti = new JTextArea();
		outputAreaAnagraficaOspiti.setEditable(false);
		outputAreaAnagraficaOspiti.setBackground(Color.LIGHT_GRAY);
		outputAreaServiziPlus = new JTextArea();
		outputAreaServiziPlus.setEditable(false);
		outputAreaServiziPlus.setBackground(Color.LIGHT_GRAY);
		outputAreaFormulaDiSoggiorno = new JTextArea();
		outputAreaFormulaDiSoggiorno.setEditable(false);
		outputAreaFormulaDiSoggiorno.setBackground(Color.LIGHT_GRAY);
		outputAreaDipendenti = new JTextArea();
		outputAreaDipendenti.setEditable(false);
		outputAreaDipendenti.setBackground(Color.LIGHT_GRAY);
		
		
		//creazione dei vari scrollPane
		scrollPaneStanze = new JScrollPane(outputAreaStanze);
		JScrollPane scrollPaneAnagraficaOspiti = new JScrollPane(outputAreaAnagraficaOspiti);
		JScrollPane scrollPaneServiziPlus = new JScrollPane(outputAreaServiziPlus);
		JScrollPane scrollPaneFormulaDiSoggiorno = new JScrollPane(outputAreaFormulaDiSoggiorno);
		JScrollPane scrollPaneDipendenti = new JScrollPane(outputAreaDipendenti);
		
		//creazione di un panel per eventuali immagini da aggiungere sulla destra
		imagePanelStanze = new JPanel();
		imagePanelStanze.setPreferredSize(new Dimension(250,450));
		imagePanelStanze.setBackground(Color.GRAY);
		
		JPanel imagePanelAnagraficaOspiti = new JPanel();
		imagePanelAnagraficaOspiti.setPreferredSize(new Dimension(250,450));
		imagePanelAnagraficaOspiti.setBackground(Color.GRAY);
		
		JPanel imagePanelServiziPlus = new JPanel();
		imagePanelServiziPlus.setPreferredSize(new Dimension(250,450));
		imagePanelServiziPlus.setBackground(Color.GRAY);
		
		JPanel imagePanelFormulaDiSoggiorno = new JPanel();
		imagePanelFormulaDiSoggiorno.setPreferredSize(new Dimension(250,450));
		imagePanelFormulaDiSoggiorno.setBackground(Color.GRAY);
		
		JPanel imagePanelDipendenti = new JPanel();
		imagePanelDipendenti.setPreferredSize(new Dimension(250,450));
		imagePanelDipendenti.setBackground(Color.GRAY);
		
		
		
		//*BOTTONI PER PANEL1 OVVERO STANZE*************
		//imposto un font per  le scritte sui bottoni
		Font buttonFont = new Font("Arial", Font.BOLD, 16);
		
		addButtonStanze = new JButton("Aggiungi");
		addButtonStanze.setFont(buttonFont);
		viewButtonStanze = new JButton("Aggiorna");
		viewButtonStanze.setFont(buttonFont);
		updateButtonStanze = new JButton("Modifica");
		updateButtonStanze.setFont(buttonFont);
		deleteButtonStanze = new JButton("Elimina");
		deleteButtonStanze.setFont(buttonFont);
		

		//gestiamo il click sui bottoni
		addButtonStanze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//metodo da inserire!!!!!!!
				aggiungiStanze();
			}
		});
		viewButtonStanze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//metodo da inserire!!!!!!!
				visualizzaStanze();
			}
		});
		
		updateButtonStanze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//metodo da inserire!!!!!!!
				modificaStanze();
			}
		});
	
		deleteButtonStanze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//metodo da inserire!!!!!!!
				eliminaStanze();
			}
		});
		
		buttonPanelStanze = new JPanel();
		buttonPanelStanze.setLayout(new GridLayout(1,4));//1 riga e 4 colonne
		buttonPanelStanze.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Aggiunge un margine al pannello
		buttonPanelStanze.setBackground(Color.GRAY);

		int hGap = 40; // Spazio orizzontale tra i bottoni
		int vGap = 40; // Spazio verticale tra i bottoni
		GridLayout layout = (GridLayout) buttonPanelStanze.getLayout();
		layout.setHgap(hGap);
		layout.setVgap(vGap);
		
		buttonPanelStanze.add(viewButtonStanze);
		buttonPanelStanze.add(addButtonStanze);
		buttonPanelStanze.add(updateButtonStanze);
		buttonPanelStanze.add(deleteButtonStanze); 
		
		panel1.add(scrollPaneStanze,BorderLayout.CENTER);
		panel1.add(imagePanelStanze,BorderLayout.EAST);
		panel1.add(buttonPanelStanze,BorderLayout.SOUTH);
		
		//************************* BUTTON PER PANEL2 OVVERO ANAGRAFICA OSPITI********************
		JButton addButtonAnagraficaOspiti = new JButton("Aggiungi");
		addButtonAnagraficaOspiti.setFont(buttonFont);
		JButton viewButtonAnagraficaOspiti= new JButton("Aggiorna");
		viewButtonAnagraficaOspiti.setFont(buttonFont);
		JButton updateButtonAnagraficaOspiti = new JButton("Modifica");
		updateButtonAnagraficaOspiti.setFont(buttonFont);
		JButton deleteButtonAnagraficaOspiti = new JButton("Elimina");
		deleteButtonAnagraficaOspiti.setFont(buttonFont);

		//gestiamo il click sui bottoni
		addButtonAnagraficaOspiti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aggiungiAnagraficaOspiti();
			}
		});
		
		viewButtonAnagraficaOspiti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visualizzaAnagraficaOspiti();
			}
		});
		
		updateButtonAnagraficaOspiti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificaAnagraficaOspiti();
			}
		});
		
		
		deleteButtonAnagraficaOspiti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminaAnagraficaOspiti();
			}
		});
		
        JPanel buttonPanelAnagraficaOspiti = new JPanel();
		
		buttonPanelAnagraficaOspiti.setLayout(new GridLayout(1,4));//1 riga e 4 colonne		
		buttonPanelAnagraficaOspiti.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Aggiunge un margine al pannello
		buttonPanelAnagraficaOspiti.setBackground(Color.GRAY);
	
		GridLayout layout2 = (GridLayout) buttonPanelAnagraficaOspiti.getLayout();
		layout2.setHgap(hGap);
		layout2.setVgap(vGap);
		
		buttonPanelAnagraficaOspiti.add(viewButtonAnagraficaOspiti);
		buttonPanelAnagraficaOspiti.add(addButtonAnagraficaOspiti);
		buttonPanelAnagraficaOspiti.add(updateButtonAnagraficaOspiti);
		buttonPanelAnagraficaOspiti.add(deleteButtonAnagraficaOspiti);		
		
		panel2.add(scrollPaneAnagraficaOspiti,BorderLayout.CENTER);
		panel2.add(imagePanelAnagraficaOspiti,BorderLayout.EAST);
		panel2.add(buttonPanelAnagraficaOspiti,BorderLayout.SOUTH);		
		
		// *********************** CREAZIONE BUTTON PER PANEL3 OVVERO SERVIZI PLUS ***************************+
		JButton addButtonServiziPlus = new JButton("Aggiungi");
		addButtonServiziPlus.setFont(buttonFont);
		JButton viewButtonServiziPlus = new JButton("Aggiorna");
		viewButtonServiziPlus.setFont(buttonFont);
		JButton updateButtonServiziPlus = new JButton("Modifica");
		updateButtonServiziPlus.setFont(buttonFont);
		JButton deleteButtonServiziPlus = new JButton("Elimina");
		deleteButtonServiziPlus.setFont(buttonFont);		

		//gestiamo il click sui bottoni
		addButtonServiziPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aggiungiServiziPlus();				
			}
		});
		
		viewButtonServiziPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visualizzaServiziPlus();
			}
		});
		
		updateButtonServiziPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificaServiziPlus();
			}
		});
		
		deleteButtonServiziPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminaServiziPlus();
			}
		});
		
       JPanel buttonPanelServiziPlus = new JPanel();
		
		buttonPanelServiziPlus.setLayout(new GridLayout(1,4));//1 riga e 4 colonne
		buttonPanelServiziPlus.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Aggiunge un margine al pannello
		buttonPanelServiziPlus.setBackground(Color.GRAY);

	
		GridLayout layout3 = (GridLayout) buttonPanelServiziPlus.getLayout();
		layout3.setHgap(hGap);
		layout3.setVgap(vGap);
		
		buttonPanelServiziPlus.add(viewButtonServiziPlus);
		buttonPanelServiziPlus.add(addButtonServiziPlus);
		buttonPanelServiziPlus.add(updateButtonServiziPlus);
		buttonPanelServiziPlus.add(deleteButtonServiziPlus); 
		
		panel3.add(scrollPaneServiziPlus,BorderLayout.CENTER);
		panel3.add(imagePanelServiziPlus,BorderLayout.EAST);
		panel3.add(buttonPanelServiziPlus,BorderLayout.SOUTH);
		
		//*********************************** CREAZIONE BUTTON PER PANEL4 OVVERO FORMILA DI SOGGIORNO ****************************
		JButton addButtonFormulaDiSoggiorno = new JButton("Aggiungi");
		addButtonFormulaDiSoggiorno.setFont(buttonFont);
		JButton viewButtonFormulaDiSoggiorno = new JButton("Aggiorna");
		viewButtonFormulaDiSoggiorno.setFont(buttonFont);
		JButton updateButtonFormulaDiSoggiorno = new JButton("Modifica");
		updateButtonFormulaDiSoggiorno.setFont(buttonFont);
		JButton deleteButtonFormulaDiSoggiorno = new JButton("Elimina");
		deleteButtonFormulaDiSoggiorno.setFont(buttonFont);
		

		//gestiamo il click sui bottoni
		addButtonFormulaDiSoggiorno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aggiungiFormulaDiSoggiorno();
			}
		});
		
		viewButtonFormulaDiSoggiorno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visualizzaFormulaDiSoggiorno();
			}
		});
		
		
		updateButtonFormulaDiSoggiorno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificaFormulaDiSoggiorno();
			}
		});
				
		deleteButtonFormulaDiSoggiorno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminaFormulaDiSoggiorno();
			}
		});
		
		JPanel buttonPanelFormulaDiSoggiorno = new JPanel();
		buttonPanelFormulaDiSoggiorno.setLayout(new GridLayout(1,4));//1 riga e 4 colonne
		buttonPanelFormulaDiSoggiorno.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Aggiunge un margine al pannello
		buttonPanelFormulaDiSoggiorno.setBackground(Color.GRAY);

		GridLayout layout4 = (GridLayout) buttonPanelFormulaDiSoggiorno.getLayout();
		layout4.setHgap(hGap);
		layout4.setVgap(vGap);
		
		buttonPanelFormulaDiSoggiorno.add(viewButtonFormulaDiSoggiorno);
		buttonPanelFormulaDiSoggiorno.add(addButtonFormulaDiSoggiorno);
		buttonPanelFormulaDiSoggiorno.add(updateButtonFormulaDiSoggiorno);
		buttonPanelFormulaDiSoggiorno.add(deleteButtonFormulaDiSoggiorno); 
		
		panel4.add(scrollPaneFormulaDiSoggiorno,BorderLayout.CENTER);
		panel4.add(imagePanelFormulaDiSoggiorno,BorderLayout.EAST);
		panel4.add(buttonPanelFormulaDiSoggiorno,BorderLayout.SOUTH);
		
		//*BOTTONI PER PANEL5 OVVERO DIPENDENTI*************
		
		JButton addButtonDipendenti = new JButton("Aggiungi");
		addButtonDipendenti.setFont(buttonFont);
		JButton viewButtonDipendenti = new JButton("Aggiorna");
		viewButtonDipendenti.setFont(buttonFont);
		JButton updateButtonDipendenti = new JButton("Modifica");
		updateButtonDipendenti.setFont(buttonFont);
		JButton deleteButtonDipendenti = new JButton("Elimina");
		deleteButtonDipendenti.setFont(buttonFont);
				
		//gestiamo il click sui bottoni
        addButtonDipendenti.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
				aggiungiDipendenti();
			}
		});
				
		viewButtonDipendenti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				visualizzaDipendenti();						
			}
		});
				
		updateButtonDipendenti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificaDipendenti();
			}
		});
								
		deleteButtonDipendenti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminaDipendenti();
			}
		});
				
       JPanel buttonPanelDipendenti = new JPanel();
		
		buttonPanelDipendenti.setLayout(new GridLayout(1,4));//1 riga e 4 colonne
		buttonPanelDipendenti.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Aggiunge un margine al pannello
		buttonPanelDipendenti.setBackground(Color.GRAY);

	
		GridLayout layout5 = (GridLayout) buttonPanelDipendenti.getLayout();
		layout5.setHgap(hGap);
		layout5.setVgap(vGap);
		
		
		buttonPanelDipendenti.add(viewButtonDipendenti);
		buttonPanelDipendenti.add(addButtonDipendenti);
		buttonPanelDipendenti.add(updateButtonDipendenti);
		buttonPanelDipendenti.add(deleteButtonDipendenti); 
		
		panel5.add(scrollPaneDipendenti,BorderLayout.CENTER);
		panel5.add(imagePanelDipendenti,BorderLayout.EAST);
		panel5.add(buttonPanelDipendenti,BorderLayout.SOUTH);
	
		
       JPanel buttonPanelContabilita = new JPanel();
		
       buttonPanelContabilita.setLayout(new GridLayout(1,4));//1 riga e 4 colonne
       buttonPanelContabilita.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Aggiunge un margine al pannello
       buttonPanelContabilita.setBackground(Color.GRAY);
       GridLayout layout6 = (GridLayout) buttonPanelDipendenti.getLayout();
       layout6.setHgap(hGap);
       layout6.setVgap(vGap);
		
		
       buttonPanelContabilita.add(viewButtonDipendenti);
       buttonPanelContabilita.add(addButtonDipendenti);
       buttonPanelContabilita.add(updateButtonDipendenti);
       buttonPanelContabilita.add(deleteButtonDipendenti); 
		
		panel6.add(scrollPaneDipendenti,BorderLayout.CENTER);
		panel6.add(imagePanelDipendenti,BorderLayout.EAST);
		panel6.add(buttonPanelDipendenti,BorderLayout.SOUTH);
		//aggiungiamo i pannelli al tabbedPane
		tabbedPane.addTab("Stanze               ", panel1);
		tabbedPane.addTab("Anagrafica Ospiti    ", panel2);
		tabbedPane.addTab("Servizi Plus         ", panel3);
		tabbedPane.addTab("Formula di soggiorno ", panel4);
		tabbedPane.addTab("Dipendenti           ", panel5);
		tabbedPane.addTab("Contabilità			", panel6);
		tabbedPane.setFont(new Font("Arial",Font.BOLD,13));
		
		mainPanel.add(tabbedPane);	
		
		visualizzaStanze();
		visualizzaServiziPlus();
		visualizzaFormulaDiSoggiorno();
		visualizzaDipendenti();
		visualizzaAnagraficaOspiti();
		
		/* RICHIAMA METODO CHE MOSTRA SOLO FUNZIONI PER RUOLO*/
		// RICHIAMO DELLA FUNZIONE 
		if (ruolo.equals("Receptionist")) {
			accessoPerRuoloReceptionist();
		}else if (ruolo.equals("Amministrazione")){
			accessoPerRuoloAmministrazione();	
			}
		
		add(mainPanel);
	}
	//****************** METODI PER STANZE *************************************+
	private void createTableStanzeIFNotExists() {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(CREATE_TABLE_QUERY_STANZE);
			stmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}	
	private void visualizzaStanze() {
		//metodo per visualizzare
		outputAreaStanze.setText("");//togliamo tutto ciò che eventualmente c'era all'interno e lo ripuliamo
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY_STANZE);
			while(rs.next()) {
				int id = rs.getInt("id");
				String tipologia = rs.getString("tipologia");
				double prezzo = rs.getDouble("prezzo");
				int numero_persone = rs.getInt("numero_persone");
				boolean disponibilita = rs.getBoolean("disponibilita");
				//append è il metodo che aggiunge alla jtextArea un valore
				outputAreaStanze.append("ID : " + id + " - Tipologia : " + tipologia + "    -     Prezzo : " + prezzo + " euro    -     Numero di persone : " + numero_persone + "    -     Disponibile : "+ disponibilita + "\n");
			}
			
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	private void aggiungiStanze() {
		//qui inseriamo dati utente
		String tipologia = JOptionPane.showInputDialog("Inserisci la tipologia della stanza: ");
		if(tipologia != null && !tipologia.isEmpty()) {
			double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci il prezzo della stanza : "));
			int numero_persone = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il numero di persone della stanza : "));
			int result = JOptionPane.showConfirmDialog(null, "La stanza è disponibile?", "Disponibilità", JOptionPane.YES_NO_OPTION);
	        boolean disponibilita = result == JOptionPane.YES_OPTION;
			insertStanze(tipologia,prezzo,numero_persone,disponibilita);
			outputAreaStanze.append("Stanza aggiunta  : " + tipologia + " - Prezzo : " + prezzo + " euro, Numero di persone : " + numero_persone + ",Disponibilità : "+ disponibilita + "\n");
		}
	}
	
	private void insertStanze(String tipologia,double prezzo,int numero_persone,boolean disponibilita) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY_STANZE);
			pstmt.setString(1, tipologia);
			pstmt.setDouble(2, prezzo);
			pstmt.setInt(3, numero_persone);
			pstmt.setBoolean(4, disponibilita);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void modificaStanze() {
		//qui modifica dati
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'id della stanza da modificare: "));
		String tipologia = JOptionPane.showInputDialog("inserisci il nuovo nome della stanza");
		if(tipologia != null && !tipologia.isEmpty()) {
			double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci il nuovo prezzo della stanza : "));
			int numero_persone = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il nuovo numero di persone della stanza : "));
			int result = JOptionPane.showConfirmDialog(null, "La stanza è disponibile?", "Disponibilità", JOptionPane.YES_NO_OPTION);
	        boolean disponibilita = result == JOptionPane.YES_OPTION;
			updateStanze(id,tipologia,prezzo,numero_persone,disponibilita);
			outputAreaStanze.append("Modifica effettuata su ID:" + id + " - Tipologia " +  tipologia + " - Prezzo : " + prezzo + " euro, Numero di persone : " + numero_persone + ",Disponibilità : "+ disponibilita + "\n");
		}
	}
	
	
	private void updateStanze(int id,String tipologia,double prezzo,int numero_persone,boolean disponibilita) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY_STANZE);
			pstmt.setString(1,tipologia);
			pstmt.setDouble(2,prezzo);
			pstmt.setInt(3, numero_persone);
			pstmt.setBoolean(4, disponibilita);
			pstmt.setInt(5, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void eliminaStanze() {
		//qui elimina dati
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID della stanza da eliminare"));
		deleteServizio(id);
		outputAreaStanze.append("Stanza eliminata : ID " + id + "\n");
	}
	private void deleteServizio(int id) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY_STANZE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//***************************** FINE METODI STANZE ******************************************************************
	
	
	//METODI PER ANAGRAFICA OSPITI !!!!!!*+
		private void createTableAnagraficaOspitiIFNotExists() {
				try {
					Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(CREATE_TABLE_QUERY_ANAGRAFICA_OSPITI);
					stmt.close();
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		
		
		
		private void visualizzaAnagraficaOspiti() {
			//metodo per visualizzare
			outputAreaAnagraficaOspiti.setText("");//togliamo tutto ciò che eventualmente c'era all'interno e lo ripuliamo
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY_ANAGRAFICA_OSPITI);
				while(rs.next()) {
					int id = rs.getInt("id");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					String codice_fiscale = rs.getString("codice_fiscale");
					String tipo_e_nr_documento  = rs.getString("tipo_e_nr_documento");
					//append è il metodo che aggiunge alla jtextArea un valore
					outputAreaAnagraficaOspiti.append("ID : " + id + "  -  Nome : " + nome + "    -     Cognome : " + cognome + "    -     Codice fiscale : " + codice_fiscale + "    -     Tipo e numero di documento : "+ tipo_e_nr_documento + "\n");
				}
				
				rs.close();
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		

		
		private void aggiungiAnagraficaOspiti() {
			//qui inseriamo dati utente
			String nome = JOptionPane.showInputDialog("Inserisci il nome del cliente: ");
			if(nome != null && !nome.isEmpty()) {
				String cognome = JOptionPane.showInputDialog("Inserisci il cognome del cliente: ");
				String codice_fiscale = JOptionPane.showInputDialog("Inserisci il codice fiscale del cliente: ");
				String tipo_e_nr_documento = JOptionPane.showInputDialog("Inserisci il tipo e il numero del documento : ");
				insertServizioAnagraficaOspiti(nome,cognome,codice_fiscale,tipo_e_nr_documento);
				outputAreaAnagraficaOspiti.append("Cliente aggiunto :   -  Nome : " + nome + "    -     Cognome : " + cognome + "    -     Codice fiscale : " + codice_fiscale + "    -     Tipo e numero di documento : "+ tipo_e_nr_documento + "\n");
			}
		}
		
		private void insertServizioAnagraficaOspiti(String nome,String cognome,String codice_fiscale,String tipo_e_nr_documento) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY_ANAGRAFICA_OSPITI);
				pstmt.setString(1, nome);
				pstmt.setString(2, cognome);
				pstmt.setString(3, codice_fiscale);
				pstmt.setString(4, tipo_e_nr_documento);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void modificaAnagraficaOspiti() {
			//qui modifica dati
			int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'id dell'anagrafica da modificare: "));
			String nome = JOptionPane.showInputDialog("inserisci il nuovo nome del cliente");
			if(nome != null && !nome.isEmpty()) {
				String cognome = JOptionPane.showInputDialog("Inserisci il nuovo cognome del cliente: ");
				String codice_fiscale = JOptionPane.showInputDialog("Inserisci il nuovo codice fiscale del cliente: ");
				String tipo_e_nr_documento = JOptionPane.showInputDialog("Inserisci il nuovo tipo e il numero del documento : ");
				updateServizioAnagraficaOspiti(id,nome,cognome,codice_fiscale,tipo_e_nr_documento);
				outputAreaAnagraficaOspiti.append("Modifica effettuata su ID:" + id + "  -  Nome : " + nome + "    -     Cognome : " + cognome + "    -     Codice fiscale : " + codice_fiscale + "    -     Tipo e numero di documento : "+ tipo_e_nr_documento + "\n");
			}
		}
		
		
		private void updateServizioAnagraficaOspiti(int id,String nome,String cognome,String  codice_fiscale,String tipo_e_nr_documento) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY_ANAGRAFICA_OSPITI);
				pstmt.setString(1,nome);
				pstmt.setString(2,cognome);
				pstmt.setString(3,codice_fiscale);
				pstmt.setString(4,tipo_e_nr_documento);
				pstmt.setInt(5, id);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		private void eliminaAnagraficaOspiti() {
			//qui elimina dati
			int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID dell'anagrafica da eliminare"));
			deleteServizioAnagraficaOspiti(id);
			outputAreaAnagraficaOspiti.append("Anagrafica eliminata : ID " + id + "\n");
		}
		private void deleteServizioAnagraficaOspiti(int id) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY_ANAGRAFICA_OSPITI);
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		//********************** FINE METODI PER ANAGRAFICA OSPITI **************************************************************
		
		//*INIZIO METODI PER  SERVIZI PLUS+
		private void createTableServiziPlusIFNotExists() {
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(CREATE_TABLE_QUERY_SERVIZI_PLUS);
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	
	
	
	private void visualizzaServiziPlus() {
		//metodo per visualizzare
		outputAreaServiziPlus.setText("");//togliamo tutto ciò che eventualmente c'era all'interno e lo ripuliamo
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY_SERVIZI_PLUS);
			while(rs.next()) {
				int id = rs.getInt("id");
				String tipo_servizio = rs.getString("tipo_servizio");
				double prezzo = rs.getDouble("prezzo");
				//append è il metodo che aggiunge alla jtextArea un valore
				outputAreaServiziPlus.append("ID : " + id + " - Tipo Servizio Plus : " + tipo_servizio + "    -     Prezzo : " + prezzo + " euro\n");
			}
			
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	private void aggiungiServiziPlus() {
		//qui inseriamo dati utente
		String tipo_servizio = JOptionPane.showInputDialog("Inserisci la tipologia del servizio: ");
		if(tipo_servizio != null && !tipo_servizio.isEmpty()) {
			double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci il prezzo del servizio : "));
			insertServiziPlus(tipo_servizio,prezzo);
			outputAreaServiziPlus.append("Servizio Plus aggiunto : " + tipo_servizio + " - Prezzo : " + prezzo + " euro\n");
		}
	}
	
	private void insertServiziPlus(String tipo_servizio,double prezzo) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY_SERVIZI_PLUS);
			pstmt.setString(1, tipo_servizio);
			pstmt.setDouble(2, prezzo);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void modificaServiziPlus() {
		//qui modifica dati
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'id del tipo di servizio da modificare: "));
		String tipo_servizio = JOptionPane.showInputDialog("inserisci il nuovo nome del Servizio Plus");
		if(tipo_servizio != null && !tipo_servizio.isEmpty()) {
			double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci il nuovo prezzo del Servizio Plus : "));
			updateServiziPlus(id,tipo_servizio,prezzo);
			outputAreaServiziPlus.append("Modifica effettuata su ID:" + id + " - Tipologia " +  tipo_servizio + " - Prezzo : " + prezzo + " euro\n");
		}
	}
	
	
	private void updateServiziPlus(int id,String tipo_servizio,double prezzo) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY_SERVIZI_PLUS);
			pstmt.setString(1,tipo_servizio);
			pstmt.setDouble(2,prezzo);
			pstmt.setInt(3, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void eliminaServiziPlus() {
		//qui elimina dati
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID del servizio Plus da eliminare : "));
		deleteServiziPlus(id);
		outputAreaServiziPlus.append("Servizio Plus eliminato : ID " + id + "\n");
	}
	private void deleteServiziPlus(int id) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY_SERVIZI_PLUS);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
		
	
	//************************************* FINE METODI SERVIZI PLUS ****************************************************
	
	//****************************************** INIZIO METODI FORMULA DI SOGGIORNO *****************************************

			private void createTableFormulaDiSoggiornoIFNotExists() {
				try {
					Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(CREATE_TABLE_QUERY_FORMULA_DI_SOGGIORNO);
					stmt.close();
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		
		
		
		private void visualizzaFormulaDiSoggiorno() {
			//metodo per visualizzare
			outputAreaFormulaDiSoggiorno.setText("");//togliamo tutto ciò che eventualmente c'era all'interno e lo ripuliamo
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY_FORMULA_DI_SOGGIORNO);
				while(rs.next()) {
					int id = rs.getInt("id");
					String tipologia = rs.getString("tipologia");
					double prezzo = rs.getDouble("prezzo");
					//append è il metodo che aggiunge alla jtextArea un valore
					outputAreaFormulaDiSoggiorno.append("ID : " + id + " - Tipologia formula di soggiorno : " + tipologia + "    -     Prezzo : " + prezzo + " euro\n");
				}
				
				rs.close();
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		

		
		private void aggiungiFormulaDiSoggiorno() {
			//qui inseriamo dati utente
			String tipologia = JOptionPane.showInputDialog("Inserisci la tipologia della formula di soggiorno: ");
			if(tipologia != null && !tipologia.isEmpty()) {
				double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci il prezzo della formula di soggiorno : "));
				insertFormulaDiSoggiorno(tipologia,prezzo);
				outputAreaFormulaDiSoggiorno.append("Formula di soggiorno aggiunta : " + tipologia + " - Prezzo : " + prezzo + " euro\n");
			}
		}
		
		private void insertFormulaDiSoggiorno(String tipologia,double prezzo) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY_FORMULA_DI_SOGGIORNO);
				pstmt.setString(1, tipologia);
				pstmt.setDouble(2, prezzo);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		private void modificaFormulaDiSoggiorno() {
			//qui modifica dati
			int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'id della formula di soggiorno da modificare : "));
			String tipologia = JOptionPane.showInputDialog("inserisci il nuovo nome della formula di soggiorno");
			if(tipologia != null && !tipologia.isEmpty()) {
				double prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserisci il nuovo prezzo della formula di soggiorno : "));
				updateFormulaDiSoggiorno(id,tipologia,prezzo);
				outputAreaFormulaDiSoggiorno.append("Modifica effettuata su ID:" + id + " - Tipologia Formula di Soggiorno " +  tipologia + " - Prezzo : " + prezzo + " euro\n");
			}
		}
		
		
		private void updateFormulaDiSoggiorno(int id,String tipologia,double prezzo) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY_FORMULA_DI_SOGGIORNO);
				pstmt.setString(1,tipologia);
				pstmt.setDouble(2,prezzo);
				pstmt.setInt(3, id);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		private void eliminaFormulaDiSoggiorno() {
			//qui elimina dati
			int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID della formula di soggiorno da eliminare : "));
			deleteFormulaDiSoggiorno(id);
			outputAreaFormulaDiSoggiorno.append("Formula di soggiorno eliminata : ID " + id + "\n");
		}
		private void deleteFormulaDiSoggiorno(int id) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY_FORMULA_DI_SOGGIORNO);
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
			
		// ******************************************************* FINE METODI FORMA DI SOGGIORNO ****************************************
		
		//*********************************************************** INIZIO METODI DIPENDENTI **********************************************
		private void createTableDipendentiIFNotExists() {
			try {
				Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(CREATE_TABLE_QUERY_DIPENDENTI);
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		/* METODO PER MOSTRARE OGGETTI PER RUOLO*/
		// RIMUOVIAMO I PENEL O DISABILITIAMO FUNZIONI CHE NON VOGLIAMO FAR VEDERE ALL'UTENTE LOGGATO
		private void accessoPerRuoloReceptionist() {
//			scrollPaneStanze.setEnabled(false);
//			addButtonStanze.setEnabled(false);
//			viewButtonStanze.setEnabled(false);
//			updateButtonStanze.setEnabled(false);
//			deleteButtonStanze.setEnabled(false);
//			deleteButtonStanze.setVisible(false);
//			scrollPaneStanze.setVisible(false);
			tabbedPane.remove(panel6);		// RIMOSSO PANNELLO DELLA CONTABILITÀ PER I RECEPTIONISTS (ESEMPIO)
		}
		/* METODO PER MOSTRARE OGGETTI PER RUOLO*/
		private void accessoPerRuoloAmministrazione() {
		// RIMUOVIAMO I PENEL O DISABILITIAMO FUNZIONI CHE NON VOGLIAMO FAR VEDERE ALL'UTENTE LOGGATO
			tabbedPane.remove(panel2);			
		}
	
	private void visualizzaDipendenti() {
		//metodo per visualizzare
		outputAreaDipendenti.setText("");//togliamo tutto ciò che eventualmente c'era all'interno e lo ripuliamo
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY_DIPENDENTI);
			while(rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String codice_fiscale = rs.getString("codice_fiscale");
				String tipo_di_contratto = rs.getString("tipo_di_contratto");
				String mansione = rs.getString("mansione");
				double retribuzione_mensile = rs.getDouble("retribuzione_mensile");
				//append è il metodo che aggiunge alla jtextArea un valore
				outputAreaDipendenti.append("ID : " + id + " - Nome : " + nome + "   -    Cognome : " + cognome + "   -   Codice Fiscale : " + codice_fiscale + "    -   Tipo di contratto :  " + tipo_di_contratto + "   -   Mansione : " + mansione + "    -   Retribuzione mensile : " + retribuzione_mensile +   " euro\n");
			}
			
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	private void aggiungiDipendenti() {
		//qui inseriamo dati utente
		String nome = JOptionPane.showInputDialog("Inserisci il nome del dipendente : ");
		if(nome != null && !nome.isEmpty()) {
			String cognome = JOptionPane.showInputDialog("Inserisci il cognome del dipendente : ");
			String codice_fiscale = JOptionPane.showInputDialog("Inserisci il codice fiscale del dipendente : ");
			String tipo_di_contratto = JOptionPane.showInputDialog("Inserisci il tipo di contratto del dipendente : ");
			String mansione = JOptionPane.showInputDialog("Inserisci il tipo di mansione del dipendente : ");
			double retribuzione_mensile = Double.parseDouble(JOptionPane.showInputDialog("Inserisci la retribuzione mensile del dipendente : "));
			insertDipendenti(nome,cognome,codice_fiscale,tipo_di_contratto,mansione,retribuzione_mensile);
			outputAreaDipendenti.append("Dipendente aggiunto :  - Nome : " + nome + "   -    Cognome : " + cognome + "   -   Codice Fiscale : " + codice_fiscale + "    -   Tipo di contratto :  " + tipo_di_contratto + "   -   Mansione : " + mansione + "    -   Retribuzione mensile : " + retribuzione_mensile +   "euro\n");
		}
	}
	
	private void insertDipendenti(String nome,String cognome,String codice_fiscale,String tipo_di_contratto,String mansione,double retribuzione_mensile) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY_DIPENDENTI);
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, codice_fiscale);
			pstmt.setString(4, tipo_di_contratto);
			pstmt.setString(5, mansione);
			pstmt.setDouble(6, retribuzione_mensile);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void modificaDipendenti() {
		//qui modifica dati
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'id del dipendente da modificare: "));
		String nome = JOptionPane.showInputDialog("Inserisci il nuovo nome del dipendente : ");
		if(nome != null && !nome.isEmpty()) {
			String cognome = JOptionPane.showInputDialog("Inserisci il nuovo  cognome del dipendente : ");
			String codice_fiscale = JOptionPane.showInputDialog("Inserisci il nuovo codice fiscale del dipendente : ");
			String tipo_di_contratto = JOptionPane.showInputDialog("Inserisci il nuovo tipo di contratto del dipendente : ");
			String mansione = JOptionPane.showInputDialog("Inserisci il nuovo tipo di mansione del dipendente : ");
			double retribuzione_mensile = Double.parseDouble(JOptionPane.showInputDialog("Inserisci la nuova retribuzione mensile del dipendente : "));
			updateDipendenti(id,nome,cognome,codice_fiscale,tipo_di_contratto,mansione,retribuzione_mensile);
			outputAreaDipendenti.append("Dipendente modificato : ID : " + id + "  - Nome : " + nome + "   -    Cognome : " + cognome + "   -   Codice Fiscale : " + codice_fiscale + "    -   Tipo di contratto :  " + tipo_di_contratto + "   -   Mansione : " + mansione + "    -   Retribuzione mensile : " + retribuzione_mensile +   " euro\n");
		}
	}
	
	
	private void updateDipendenti(int id,String nome,String cognome,String codice_fiscale,String tipo_di_contratto,String mansione,double retribuzione_mensile) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_QUERY_DIPENDENTI);
			pstmt.setString(1, nome);
			pstmt.setString(2, cognome);
			pstmt.setString(3, codice_fiscale);
			pstmt.setString(4, tipo_di_contratto);
			pstmt.setString(5, mansione);
			pstmt.setDouble(6, retribuzione_mensile);
			pstmt.setInt(7, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void eliminaDipendenti() {
		//qui elimina dati
		int id = Integer.parseInt(JOptionPane.showInputDialog("Inserisci l'ID del dipendente da eliminare"));
		deleteDipendenti(id);
		outputAreaDipendenti.append("Dipendente eliminato : ID " + id + "\n");
	}
	private void deleteDipendenti(int id) {
		try {
			Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			PreparedStatement pstmt = conn.prepareStatement(DELETE_QUERY_DIPENDENTI);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//**************************************** FINE METODI DIPENDENTI **************************************
		
		

}