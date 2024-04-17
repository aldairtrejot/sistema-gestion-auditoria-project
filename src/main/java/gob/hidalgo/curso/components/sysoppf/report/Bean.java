package gob.hidalgo.curso.components.sysoppf.report;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

@Named
@SessionScoped
public class Bean {
    public void redireccionar() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            
            // Redireccionar al usuario a otra página
            response.sendRedirect("/resources/reporte/REPORTE_GENERADO.pdf");
            
            // Importante: llamar a responseComplete() para indicar a JSF que no se deben procesar más acciones
            context.responseComplete();
        } catch (Exception e) {
            // Manejar cualquier excepción
            e.printStackTrace();
        }
    }
} 