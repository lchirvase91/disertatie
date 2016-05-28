package generareEtichetaColet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

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
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dbConnection.ConnectionManager;

public class GenerareEtichetaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GenerareEtichetaServlet() {
		super();
	}
	
	private static Font FONT1 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font FONT2 = new Font(Font.FontFamily.TIMES_ROMAN, 15);
	private static Font FONT3 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String comandaId = request.getParameter("comanda_id");
		
		
		Connection conn = null;
		PreparedStatement pst1 = null, pst2 = null, pst3 = null;
		ResultSet rs1 = null, rs2 = null;
		try {
			conn = ConnectionManager.getConnection();
			String select1 = "select colet_id, count(*) from colet where colet_comanda_id = ?";
			pst1 = conn.prepareStatement(select1);
			pst1.setString(1, comandaId);
			rs1 = pst1.executeQuery();
			int i = 0;
			while (rs1.next()) {
				
				final String awb = String.valueOf(Calendar.getInstance().getTimeInMillis());
				
				
				String update1 = "update colet set colet_awb = ? where colet_id = ? and colet_awb is null";
				String update2 = "update colet set colet_status = 'procesat' where colet_id = ?";
				pst2 = conn.prepareStatement(update1);
				pst2.setString(1, awb);
				pst2.setString(2, rs1.getString("colet_id"));
				int result = pst2.executeUpdate();
				if (result > 0) {
					pst2 = conn.prepareStatement(update2);
					pst2.setString(1, rs1.getString("colet_id"));
					pst2.executeUpdate();
				}
			}
			
			
			String select2 = "select colet_awb, exp.client_nume, exp.client_judet, exp.client_localitate, exp.client_adresa, exp.client_telefon, exp.client_email, dest.client_nume, dest.client_judet, dest.client_localitate, dest.client_adresa, dest.client_telefon, dest.client_email, comanda_nr_colete, comanda_greutate, comanda_observatii from client exp, client dest, comanda, colet where exp.client_id = comanda_exp_id and dest.client_id = comanda_dest_id and comanda_id = colet_comanda_id and comanda_id = ?";
			pst3 = conn.prepareStatement(select2);
			pst3.setString(1, comandaId);
			rs2 = pst3.executeQuery();
			ClientBean exp = null;
			ClientBean dest = null;
			ComandaBean comanda = null;
			ArrayList<ComandaBean> listaComenzi = new ArrayList();
			while (rs2.next()) {
				exp = new ClientBean(rs2.getString("exp.client_nume"), rs2.getString("exp.client_judet"), rs2.getString("exp.client_localitate"), rs2.getString("exp.client_adresa"), rs2.getString("exp.client_telefon"), rs2.getString("exp.client_email"));
				dest = new ClientBean(rs2.getString("dest.client_nume"), rs2.getString("dest.client_judet"), rs2.getString("dest.client_localitate"), rs2.getString("dest.client_adresa"), rs2.getString("dest.client_telefon"), rs2.getString("dest.client_email"));
				comanda = new ComandaBean (exp, dest, rs2.getString("comanda_nr_colete"), rs2.getString("comanda_greutate"), rs2.getString("comanda_observatii"));
				comanda.setAwb(rs2.getString("colet_awb"));
				listaComenzi.add(comanda);
			}
			

			Document document = new Document(new Rectangle(600, 400));
			for (ComandaBean colet : listaComenzi) {
				
				PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
				document.open();
				
				PdfPTable table = new PdfPTable(4);
				table.setSpacingBefore(10f);
				table.setWidthPercentage(100);
				int[] columnWidths = { 25, 25, 25, 25 };
				table.setWidths(columnWidths);
				PdfPCell cellAWB = new PdfPCell(new Phrase("AWB: " + colet.getAwb(), FONT1));
				cellAWB.setFixedHeight(30f);
				cellAWB.setColspan(4);
				PdfPCell cell1 = new PdfPCell(new Phrase("EXPEDITOR:", FONT1));
				PdfPCell cell2 = new PdfPCell(new Phrase("DESTINATAR:", FONT1));
				PdfPCell cell3 = new PdfPCell(new Phrase("DETALII COMANDA:", FONT1));
				PdfPCell cell4 = new PdfPCell(new Phrase("QR CODE", FONT1));
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellAWB.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cellAWB);
				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				
				
				Phrase phrase = new Phrase();
				phrase.add(new Chunk("Nume: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getExpeditor().getNume(), FONT2));
				table.addCell(phrase);
					
				phrase = new Phrase();
				phrase.add(new Chunk("Nume: \n\n",  FONT3));
				phrase.add(new Chunk(colet.getDestinatar().getNume(), FONT2));
				table.addCell(phrase);
				
				phrase = new Phrase();
				phrase.add(new Chunk("Colete: \n\n",  FONT3));
				phrase.add(new Chunk((listaComenzi.indexOf(colet) + 1) + " / " + listaComenzi.size(), FONT2));
				PdfPCell cell = new PdfPCell(phrase);
				cell.setRowspan(2);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(""));
				generateQRCode(cell, colet.getAwb());
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
				
			if (rs1 != null) {
				rs1.close();
				rs1 = null;
			}
			if (rs2 != null) {
				rs2.close();
				rs2 = null;
			}
			if (pst1 != null) {
				pst1.close();
				pst1 = null;
			}
			if (pst2 != null) {
				pst2.close();
				pst2 = null;
			}
			if (pst3 != null) {
				pst3.close();
				pst3 = null;
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

	private void generateQRCode(PdfPCell cell, String content) {

		BarcodeQRCode qrcode = new BarcodeQRCode(content, 1, 1, null);
		try {
			Image qrcodeImage = qrcode.getImage();
			cell.addElement(qrcodeImage);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
}
