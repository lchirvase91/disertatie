package generareEtichetaColet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javaBeans.ClientBean;
import javaBeans.ComandaBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dbConnection.ConnectionManager;

public class GenerareEtichetaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GenerareEtichetaServlet() {
		super();
	}
	
	private static Font FONT1 = new Font(Font.FontFamily.TIMES_ROMAN, 18);
	private static Font FONT2 = new Font(Font.FontFamily.TIMES_ROMAN, 15);
	private static Font FONT3 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getConnection();
			String sql = "select exp.client_nume, exp.client_judet, exp.client_localitate, exp.client_adresa, exp.client_telefon, exp.client_email, dest.client_nume, dest.client_judet, dest.client_localitate, dest.client_adresa, dest.client_telefon, dest.client_email, comanda_nr_colete, comanda_greutate, comanda_observatii from client exp, client dest, comanda, colet where exp.client_id = comanda_exp_id and dest.client_id = comanda_dest_id and comanda_id = colet_comanda_id and comanda_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "2");
			rs = pst.executeQuery();
			ClientBean exp = null;
			ClientBean dest = null;
			ComandaBean comanda = null;
			ArrayList<ComandaBean> listaColete = new ArrayList();
			while (rs.next()) {
				exp = new ClientBean(rs.getString("exp.client_nume"), rs.getString("exp.client_judet"), rs.getString("exp.client_localitate"), rs.getString("exp.client_adresa"), rs.getString("exp.client_telefon"), rs.getString("exp.client_email"));
				dest = new ClientBean(rs.getString("dest.client_nume"), rs.getString("dest.client_judet"), rs.getString("dest.client_localitate"), rs.getString("dest.client_adresa"), rs.getString("dest.client_telefon"), rs.getString("dest.client_email"));
				comanda = new ComandaBean (exp, dest, rs.getString("comanda_nr_colete"), rs.getString("comanda_greutate"), rs.getString("comanda_observatii"));
				listaColete.add(comanda);
			}

			Document document = new Document(new Rectangle(600, 400));
			for (ComandaBean colet : listaColete) {
				
				PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
				document.open();
				
				PdfPTable table = new PdfPTable(4);
				table.setSpacingBefore(10f);
				table.setWidthPercentage(100);
				int[] columnWidths = { 25, 25, 25, 25 };
				table.setWidths(columnWidths);
				PdfPCell cell1 = new PdfPCell(new Phrase("EXPEDITOR:", FONT1));
				PdfPCell cell2 = new PdfPCell(new Phrase("DESTINATAR:", FONT1));
				PdfPCell cell3 = new PdfPCell(new Phrase("DETALII COMANDA:", FONT1));
				PdfPCell cell4 = new PdfPCell(new Phrase("QR CODE", FONT1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				
				
				Phrase phrase = new Phrase();
				phrase.add(new Chunk("Nume: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getDestinatar().getNume(), FONT2));
				table.addCell(phrase);
					
				phrase = new Phrase();
				phrase.add(new Chunk("Nume: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getDestinatar().getNume(), FONT2));
				table.addCell(phrase);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Colete: \n\n",  FONT3));
				phrase.add(new Chunk((listaColete.indexOf(colet) + 1) + " / " + listaColete.size(), FONT2));
				PdfPCell cell = new PdfPCell(phrase);
				cell.setRowspan(2);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(""));
				cell.setRowspan(6);
				table.addCell(cell);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Judet: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getExpeditor().getJudet(), FONT2));
				table.addCell(phrase);

				phrase = new Phrase();
				phrase.add(new Chunk("Judet: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getDestinatar().getJudet(), FONT2));
				table.addCell(phrase);


				phrase = new Phrase();
				phrase.add(new Chunk("Localitate: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getExpeditor().getLocalitate(), FONT2));
				table.addCell(phrase);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Localitate: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getDestinatar().getLocalitate(), FONT2));
				table.addCell(phrase);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Greutate totala: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getGreutate(), FONT2));
				cell = new PdfPCell(phrase);
				cell.setRowspan(2);
				table.addCell(cell);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Adresa: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getExpeditor().getAdresa(), FONT2));
				table.addCell(phrase);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Adresa: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getDestinatar().getAdresa(), FONT2));
				table.addCell(phrase);

				phrase = new Phrase();
				phrase.add(new Chunk("Telefon: \n\n",  FONT3));
				if (colet.getExpeditor().getTelefon() == null) {
					colet.getExpeditor().setTelefon(" - ");
				}
				phrase.add(new Chunk(colet.getExpeditor().getTelefon(), FONT2));
				table.addCell(phrase);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Telefon: \n\n",  FONT3));
				if (colet.getDestinatar().getTelefon() == null) {
					colet.getDestinatar().setTelefon(" - ");
				}
				phrase.add(new Chunk(colet.getDestinatar().getTelefon(), FONT2));
				table.addCell(phrase);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Observatii: \n\n",  FONT3));
				if (colet.getObservatii() == null) {
					colet.setObservatii(" - ");
				}
				phrase.add(new Chunk(colet.getObservatii(), FONT2));
				cell = new PdfPCell(phrase);
				cell.setRowspan(2);
				table.addCell(cell);

				phrase = new Phrase();
				phrase.add(new Chunk("Email: \n\n",  FONT3));
				if (colet.getExpeditor().getEmail() == null) {
					colet.getExpeditor().setEmail(" - ");
				}
				phrase.add(new Chunk(colet.getExpeditor().getEmail(), FONT2));
				table.addCell(phrase);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Email: \n\n",  FONT3));
				if (colet.getDestinatar().getEmail() == null) {
					colet.getDestinatar().setEmail(" - ");
				}
				phrase.add(new Chunk(colet.getDestinatar().getEmail(), FONT2));
				table.addCell(phrase);

				document.add(table);
				
				document.newPage();

			}
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
			if (conn != null) {
					conn.close();
				conn = null;
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
