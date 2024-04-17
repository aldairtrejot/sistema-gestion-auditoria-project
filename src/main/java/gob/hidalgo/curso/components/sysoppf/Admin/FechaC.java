package gob.hidalgo.curso.components.sysoppf.Admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component("FechaC")
public class FechaC extends ConstantesOfiPartPf {
	
	public String cambiarFecha(Date fechaInicio) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String fechaFinal = null;
		if ( fechaInicio != null) {
			 fechaFinal = formatter.format(fechaInicio);
		}
		return fechaFinal;
	}
	
	public String cambiarFechaDMA(Date fechaInicio) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFinal = null;
		if ( fechaInicio != null) {
			 fechaFinal = formatter.format(fechaInicio);
		}
		return fechaFinal;
	}
	
	public Date agregarMeses(Date fecha) {
		 Calendar calendar = Calendar.getInstance();
         calendar.setTime(fecha);
         calendar.add(Calendar.MONTH, getCmeses());
        fecha = calendar.getTime();
        return fecha;
	}
	
	public Date FechaActual() {
		java.util.Date fecha = new Date();
		return fecha;
	}
}
