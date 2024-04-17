package gob.hidalgo.curso.components.sysoppf.report;

import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContextHolder;import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.codec.Base64.InputStream;

import gob.hidalgo.curso.components.MensajesC;
import gob.hidalgo.curso.components.sysoppf.ResultadoCatalogoC;
import gob.hidalgo.curso.components.sysoppf.Admin.ConstantesOfiPartPf;
import gob.hidalgo.curso.components.sysoppf.Admin.FechaC;
import gob.hidalgo.curso.database.sysoppf.TblturnoEO;
import lombok.Getter;

@ManagedBean
@Getter
@Component ("Reporte")
public class Reporte extends ConstantesOfiPartPf{
	
	private static final int DEFAULT_BUFFER_SIZE = 8192;

	@Autowired
	private FechaC fechaC;
	
	@Autowired
	private ResultadoCatalogoC ResultadoCatalogoC; 
	
	@Autowired
	private MensajesC mensajesC;
	
	private Map<String, String> turno= new HashMap<String, String>();
	
	public void reporte(TblturnoEO tblturnoEO) throws IOException, DocumentException  {
		try {
			String cambiarFecha =  String.valueOf(fechaC.cambiarFechaDMA(tblturnoEO.getFecha_reg()));
			String fechaTermino = String.valueOf(fechaC.cambiarFechaDMA(tblturnoEO.getFec_vencimiento()));
			String numTurno = tblturnoEO.getNum_expediente_ori() + " : " + String.valueOf(tblturnoEO.getNum_oficio());
			String area = ResultadoCatalogoC.CatalogoAreaSN(tblturnoEO.getId_area_atension());
			
			
			turno.put(getRfecha(), cambiarFecha);
			turno.put(getRnoTurnoSF(), tblturnoEO.getBturnosf());
			turno.put(getRnoOficio(), numTurno);
			turno.put(getRnoTurnoPF(), tblturnoEO.getNum_turno());
			turno.put(getRremitido(), tblturnoEO.getRemitente());
			turno.put(getRcargo(), tblturnoEO.getCargo_remitente());
			turno.put(getRlugar(), tblturnoEO.getLugar());
			turno.put(getRAsunto(),tblturnoEO.getAsunto());
			turno.put(getRTurneseA(),area);
			turno.put(getRFojas(), noNullI(tblturnoEO.getNum_fojas()));
			turno.put(getRTomo(),noNullI(tblturnoEO.getNum_tomos()));
			turno.put(getRObservaciones(), tblturnoEO.getDesc_observacion());
			turno.put(getRTermino(),tblturnoEO.getDesc_termino());
			GenerarReporte();
		} catch (Exception e) {
		}
	}
	
	private String noNullI(Integer numero) {
		String numeroSalida = "";
		if(numero != null) {
			numeroSalida = String.valueOf(numero);
		}
		return numeroSalida;
	}
	
	private void GenerarReporte() throws IOException, DocumentException, PrinterException, ServletException  {
			GenerarPDF generarPDF = new GenerarPDF(turno);
			generarPDF.reporte();
/*
		FacesContext facesContext = FacesContext.getCurrentInstance();  
        ExternalContext externalContext = facesContext.getExternalContext();  
     	HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();  
        HttpServletResponse response=(HttpServletResponse)facesContext.getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        System.out.println("Se llega a la funcion");
	*/
			/*
			File file = new File(System.getProperty(getREPORTE_JAVA()) + getREPORTE_GENERADO_PDF());
			FacesContext facesContext = FacesContext.getCurrentInstance();  
	        ExternalContext externalContext = facesContext.getExternalContext();  
	     	HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();  
	     	HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
	     	//FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(System.getProperty(getREPORTE_JAVA()) + getREPORTE_GENERADO_PDF()))
	     	response.sendRedirect( request.getContextPath() + "/app/sysoppf/controlCorrespondencia" + FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/reporte/REPORTE_PLANTILLA.pdf"));
	     	System.out.println("URL:" + request.getContextPath() + "/app/sysoppf/controlCorrespondencia" + FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/reporte/REPORTE_PLANTILLA.pdf"));
	*/
			/*
			File file = new File("/Users/aldairtrejo/Desktop/reporte/REPORTE_PLANTILLA.pdf");
            FacesContext facesContext = FacesContext.getCurrentInstance();  
            ExternalContext externalContext = facesContext.getExternalContext();  
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();  

            BufferedInputStream input = null;  
            BufferedOutputStream output = null;
            if (file.exists()) {
            	System.out.println("El archivo existe");
            }
            
            try {  
            	try {
					
				
                // Open file.                                                                                          
                input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);  

                // Init servlet response.  
                response.reset();             
                response.setHeader("Content-Type", "application/pdf"); 
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Content-Length", String.valueOf(file.length()));            
                response.setHeader("Content-Disposition", "inline; filename=\"REPORTE_PLANTILLA.pdf\"");  
                output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);  

                // Write file contents to response.  
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];  
                int length;  
                while ((length = input.read(buffer)) > 0) {  
                    output.write(buffer, 0, length);  
                }              
                 // Finalize task.  
                output.flush();                
                System.out.println("Se termina el proceso");
            	} catch (Exception e) {
					System.out.println("Error " + e);
				}
            }finally{  
                output.close();  
                input.close();  
            }         
            facesContext.responseComplete(); 
			*/

	
	}

}
