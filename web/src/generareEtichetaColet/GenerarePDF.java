package generareEtichetaColet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbConnection.ConnectionManager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerarePDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GenerarePDF() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		int idAvizier = Integer.parseInt(request.getParameter("id"));
//		Connection conn = null;
//		PreparedStatement pst1 = null;
//		ResultSet rs = null;
		try {
//			String subiect=null, anUniv=null, disciplina=null, dataExamen=null, sesiune=null;
//			int anStudiu = 0;
//			conn = ConnectionManager.getConnection();
//			String sql = "select * from Avizier where Avizier_id=?";
//			pst1 = conn.prepareStatement(sql);
//			pst1.setInt(1, idAvizier);
//			rs = pst1.executeQuery();
//			while (rs.next()) {
//				subiect = rs.getString("Avizier_titlu");
//				anUniv = rs.getString("Avizier_anuniv");
//				anStudiu = Integer.parseInt(rs.getString("Avizier_anstud"));
//				disciplina = rs.getString("Avizier_disciplina");
//				dataExamen = rs.getString("Avizier_dataexamen");
//				sesiune = rs.getString("Avizier_sesiune");
//			}
//			String sql1 = "select Student_nume, Student_prenume, Catalog_nota from Student, SitStudent, Catalog, Disciplina where Student_cods=SitStudent_cods and SitStudent_codss=Catalog_codss and Catalog_coddisc=Disciplina_coddisc and SitStudent_anuniv=? and SitStudent_anstud=? and Catalog_sesiune=? and Catalog_datan=? and Disciplina_denumire=? and Disciplina_anuniv=? and Disciplina_anstud=?";
//			System.out.println(dataExamen);
//			pst1 = conn.prepareStatement(sql1);
//			pst1.setString(1, anUniv);
//			pst1.setInt(2, anStudiu);
//			pst1.setString(3, sesiune);
//			pst1.setString(4, dataExamen);
//			pst1.setString(5, disciplina);
//			pst1.setString(6, anUniv);
//			pst1.setInt(7, anStudiu);
//			rs = pst1.executeQuery();
			
			Document document = new Document();
//			for (int i =0 ; i < 3; i++) {
				PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
				document.open();
				
				
				Paragraph titlu = new Paragraph("Rezultate examen");
				titlu.setAlignment(Element.ALIGN_CENTER);
				document.add(titlu);
				PdfPTable table = new PdfPTable(2);
				table.setSpacingBefore(10f);
				table.setWidthPercentage(100);
				int[] columnWidths = { 90, 10 };
				table.setWidths(columnWidths);
				PdfPCell cell1 = new PdfPCell(new Paragraph("NUME"));
				PdfPCell cell2 = new PdfPCell(new Paragraph("NOTA"));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell1);
				table.addCell(cell2);
				document.add(table);
				
				document.newPage();
				
				
				
				// step 4
		        document.add(new Paragraph("This page will NOT be followed by a blank page!"));
		        document.newPage();
		        // we don't add anything to this page: newPage() will be ignored
		        document.newPage();
		        document.add(new Paragraph("This page will be followed by a blank page!"));
		        document.newPage();
		        writer.setPageEmpty(false);
		        document.newPage();
		        document.add(new Paragraph("The previous page was a blank page!"));
//			}
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (Exception e) {
//				}
//				rs = null;
//			}
//			if (pst1 != null) {
//				try {
//					pst1.close();
//				} catch (Exception e) {
//				}
//				pst1 = null;
//			}
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e) {
//				}
//				conn = null;
//			}
//		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
