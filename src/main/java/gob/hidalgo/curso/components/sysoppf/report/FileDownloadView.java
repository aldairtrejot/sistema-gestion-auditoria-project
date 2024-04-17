package gob.hidalgo.curso.components.sysoppf.report;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;


import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.lowagie.text.DocumentException;

import gob.hidalgo.curso.components.MensajesC;
import gob.hidalgo.curso.components.sysoppf.Admin.ConstantesOfiPartPf;
import gob.hidalgo.curso.database.sysoppf.TblturnoEO;
import lombok.Getter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javax.servlet.ServletContext;
@Getter
@Named
@RequestScoped
public class FileDownloadView extends ConstantesOfiPartPf{
	
	@Autowired
	private Reporte reporte;
	
	@Autowired
	private MensajesC mensajesC;
	
    public StreamedContent imprimir(TblturnoEO tblturnoEO) throws IOException, DocumentException {
      	FacesContext facesContext = FacesContext.getCurrentInstance();  
        ExternalContext externalContext = facesContext.getExternalContext();  
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    	ServletContext context = request.getServletContext();
    	String filePath = context.getRealPath("src/main/webapp/resources/reporte/REPORTE_PLANTILLA.pdf"); //RUta
    	System.out.println("Ruta: " + filePath);
    	reporte.reporte(tblturnoEO);
    	
    	/*
    	 * 
    	 * 
    	 * 
    	 * 
    	 */
    	
    //	PdfWriter writer = new PdfWriter("/Users/aldairtrejo/Desktop/reporte1.pdf");
    /*
    	PdfWriter writer = new PdfWriter(System.getProperty(getREPORTE_JAVA()) + getREPORTE_GENERADO_PDF());
    	PdfDocument pdf = new PdfDocument(writer);
    	Document document = new Document(pdf);
    	document.add(new Paragraph("Este archivo fue generado con iText7!"));
    	document.close();*/
    	

    	/*
    	InputStream inputStream = getClass().getResourceAsStream(getREPORTE_PLATILLA_PDF());
    	File file = new File("filename.pdf");
		PdfWriter writer = new PdfWriter(file);
    	//PdfWriter writer = new PdfWriter(archivoSalida);
		PdfReader reader = new PdfReader(inputStream);
		PdfDocument pdfDocument = new PdfDocument(reader, writer);
		addContentToDocument(pdfDocument,tblturnoEO);// 
		
		
		ClassLoader classLoader = getClass().getClassLoader();
		File resourcesDirectory = new File(classLoader.getResource("").getPath());
		String rutaArchivo = "reporte/reporte.pdf";
		
		
		try {
			
			String nombreArchivo = "miarchivo.txt"; // Nombre del archivo que deseas guardar
			File archivo = new File(resourcesDirectory.getAbsolutePath() + File.separator + nombreArchivo);
			System.out.println("URL " + resourcesDirectory.getAbsolutePath()  + File.separator + rutaArchivo);
			String contenido = "Contenido del archivo"; // El contenido que deseas escribir en el archivo
			try (FileWriter writer1 = new FileWriter(archivo)) {
			    writer1.write(contenido);
			}
			
			FileOutputStream archivoSalida = new FileOutputStream(archivo);
			
		//	  byte[] bytes = FileUtils.readFileToByteArray(file);
			// Convertir el contenido a un arreglo de bytes
	         byte[] bytesContenido =FileUtils.readFileToByteArray(file);

	         // Escribir los bytes en el archivo
	         archivoSalida.write(bytesContenido);

	         // Cerrar el flujo de salida
	         archivoSalida.close();
			System.out.println("Se cierra el archivo");
		} catch (Exception e) {
			System.out.println("error" + e);
		}
		*/
    	File initialFile = new File(System.getProperty(getREPORTE_JAVA()) + getREPORTE_GENERADO_PDF());
        InputStream targetStream = new FileInputStream(initialFile);
    	StreamedContent fileimprimir;
    	 fileimprimir = DefaultStreamedContent.builder()
                 //.name("OficialiaDePartesReporte.pdf")
    			 .name("ReporteTurno.pdf")
                 .contentType("document/pdf")
                 .stream(() -> targetStream)
                 .build();
    	 return fileimprimir;
    }
    
    private void addContentToDocument(PdfDocument pdfDocument, TblturnoEO t) throws MalformedURLException {
		Document document = new Document(pdfDocument);
		add(pdfDocument, document, t.getRemitente(), 107, 626);
		add(pdfDocument, document, t.getCargo_remitente(), 69, 613);
		add(pdfDocument, document, t.getLugar(), 69, 600);
		document.close();
	}
	
	private void add(PdfDocument pdfDocument,Document document, String clave, Integer A, Integer D) {
		Paragraph paragraph = new Paragraph().setFontSize(9);
		paragraph.add(clave);
		paragraph.setFixedPosition(1, A, D, 500);
		document.add(paragraph);
	}
    
	private void guardar() {
		  String contenido = "¡Hola, este es un ejemplo para guardar un archivo en Java!";

	        try {
	            // Crear un objeto FileOutputStream con la ruta del archivo
	            FileOutputStream archivoSalida = new FileOutputStream("");

	            // Convertir el contenido a un arreglo de bytes
	            byte[] bytesContenido = contenido.getBytes();

	            // Escribir los bytes en el archivo
	            archivoSalida.write(bytesContenido);

	            // Cerrar el flujo de salida
	            archivoSalida.close();

	            System.out.println("Archivo guardado exitosamente.");
	        } catch (IOException e) {
	            System.out.println("Ocurrió un error al guardar el archivo: " + e.getMessage());
	        }
	    }
	}
	
/*
 *    private StreamedContent file;

    public FileDownloadView() {
        file = DefaultStreamedContent.builder()
                .name("downloaded_boromir.pdf")
                .contentType("image/jpg")
                .stream(() -> getClass().getResourceAsStream(getREPORTE_PLATILLA_PDF()))
                .build();
    }
    public StreamedContent getFile() {
        return file;
    }
    
    */
