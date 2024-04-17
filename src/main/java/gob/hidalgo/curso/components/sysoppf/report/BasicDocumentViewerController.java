package gob.hidalgo.curso.components.sysoppf.report;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import gob.hidalgo.curso.components.sysoppf.Admin.ConstantesOfiPartPf;

@Named
@SessionScoped
public class BasicDocumentViewerController extends ConstantesOfiPartPf implements Serializable {

    private static final long serialVersionUID = 1L;

    private String downloadFileName = "pfe-rocks.pdf";
    private StreamedContent content;

    public void onPrerender(final ComponentSystemEvent event) {

        try {

            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final Document document = new Document();

            
            
            PdfWriter.getInstance(document, out);
            document.open();
            
            /*
            for (int i = 0; i < 50; i++) {
                document.add(new Paragraph("All work and no play makes Jack a dull boy"));
            }*/

            document.close();
            content = DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(out.toByteArray()))
                        .contentType("application/pdf").name("jack.pdf").build();
        }
        catch (final Exception e) {

        }
    }
    
    @SuppressWarnings("deprecation")
	public StreamedContent getdFile() throws IOException, FileNotFoundException {
    	File file = new File("/Users/aldairtrejo/Desktop/reporte/REPORTE_PLANTILLA.pdf");
        return new DefaultStreamedContent(new FileInputStream(file),"application/pdf");
    }

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(final StreamedContent content) {
        this.content = content;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(final String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
}