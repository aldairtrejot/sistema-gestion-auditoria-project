package gob.hidalgo.curso.components.sysoppf.report;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import gob.hidalgo.curso.components.sysoppf.Admin.ConstantesOfiPartPf;

@Component("GenerarPDF")
public class GenerarPDF extends ConstantesOfiPartPf {

	private Map<String, String> turno = new HashMap<String, String>();

	public GenerarPDF(Map<String, String> turno) {
		super();
		this.turno = turno;
	}

	public void reporte() throws IOException {
		/*
		InputStream inputStream = getClass().getResourceAsStream(getREPORTE_PLATILLA_PDF());
		PdfWriter writer = new PdfWriter(System.getProperty(getREPORTE_JAVA()) + getREPORTE_GENERADO_PDF());
		PdfReader reader = new PdfReader(getREPORTE_PLATILLA_PDF());
		PdfWriter writer = new PdfWriter(getREPORTE_GENERADO_PDF());
		
		FacesContext facesContext = FacesContext.getCurrentInstance();  
        ExternalContext externalContext = facesContext.getExternalContext();  
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    	ServletContext context = request.getServletContext();
    	String filePath = context.getRealPath("src/main/webapp/resources/reporte/REPORTE_GENERADO.pdf"); //RUta*/
    	
		InputStream inputStream = getClass().getResourceAsStream(getREPORTE_PLATILLA_PDF());
		PdfWriter writer = new PdfWriter(System.getProperty(getREPORTE_JAVA()) + getREPORTE_GENERADO_PDF());
		PdfReader reader = new PdfReader(inputStream);
		PdfDocument pdfDocument = new PdfDocument(reader, writer);
		addContentToDocument(pdfDocument); 
	}

	private void addContentToDocument(PdfDocument pdfDocument) throws MalformedURLException {
		Document document = new Document(pdfDocument);
		add(pdfDocument, document, getRfecha(),56, 689,8);
		add(pdfDocument, document, getRnoTurnoSF(),159, 689,8);
		add(pdfDocument, document, getRnoOficio(), 210, 689,7);
		add(pdfDocument, document, getRnoTurnoPF(), 486, 689,8);
		add(pdfDocument, document, getRremitido(), 85, 667,8);
		add(pdfDocument, document, getRcargo(), 57, 658,8);
		add(pdfDocument, document, getRlugar(), 57, 647,8);
		add(pdfDocument, document, getRAsunto(), 27, 592,7);
		add(pdfDocument, document, getRTurneseA(), 27, 550,8);
		add(pdfDocument, document, getRFojas(),479, 563,8);
		add(pdfDocument, document, getRTomo(), 449, 553,8);
		add(pdfDocument, document, getRObservaciones(), 25, 485,7);
		add(pdfDocument, document, getRTermino(), 315, 521,8);
		document.close();
	}
	
	private void add(PdfDocument pdfDocument,Document document, String clave, Integer A, Integer D, Integer size) {
		Paragraph paragraph = new Paragraph().setFontSize(size);
		paragraph.add(turno.get(clave));
		paragraph.setFixedPosition(1, A, D, 500);
		document.add(paragraph);
	}
}