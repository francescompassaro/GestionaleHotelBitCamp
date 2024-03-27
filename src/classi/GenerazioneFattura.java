package classi;

import java.io.IOException;


import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;




public class GenerazioneFattura {

	//private static String PATH = "C:/Users/Francis/Desktop/WorkSpaces/workspace-1/EsempiPDF/";
	// oppure ""
	private static String PATH = "src/fatturePDF/";
	private static String PDF_NAME = "fattura4.pdf"; 
	// si può implementare anche un metodo che prende gli id da sql e costruisce il filename del PDF
	
	public static void main(String[] args) {

		System.out.println("Creazione pdf...");
		
		try (PDDocument doc = new PDDocument()) {
            PDPage pdPage = new PDPage();
            doc.addPage(pdPage);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, pdPage)) {
                
               	contentStream.beginText();
                // Impostazione del font
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Titolo del documento");
                contentStream.newLineAtOffset(0, -20);
                
                // Scrivere l'intestazione della fattura

                contentStream.showText("Fattura");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Numero fattura: 12345");


                contentStream.newLineAtOffset(0, -50);
                contentStream.showText("Cliente:");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Nome cliente");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Indirizzo cliente");

                contentStream.newLineAtOffset(0, -50);
                contentStream.showText("Prodotto");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Quantità");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Prezzo");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Totale");

                contentStream.newLineAtOffset(-300, -30);
                contentStream.showText("Prodotto 1");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("1");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("€10");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("€10");

                // Scrivere il totale
                contentStream.newLineAtOffset(0, -50);
                contentStream.showText("Totale:");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("€10");

                contentStream.endText();
            }

            doc.save(PATH+PDF_NAME);
            System.out.println("Fattura generata");
            JOptionPane.showMessageDialog(null, "Fattura generata");
 
        } catch (IOException ioException) {
            System.out.println("Handling IOException...");
        }

	}// fine main
	


}
