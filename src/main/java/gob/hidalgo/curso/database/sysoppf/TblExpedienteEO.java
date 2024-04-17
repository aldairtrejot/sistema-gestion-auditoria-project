package gob.hidalgo.curso.database.sysoppf;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import gob.hidalgo.curso.utils.EntityObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("TblExpedienteEO")
public class TblExpedienteEO  extends EntityObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String num_expediente;
	private String desc_observaciones;
	private Date fecha_solicitud;
	private Date fec_registro;
	private String num_turno_orig;
	
	//Messages @NotNull
	@NotNull (message = "Ingresar el Año")
	private Integer anio;
	
	@NotNull (message = "Ingresar el Área")
	private Integer id_area;
	
	@NotNull (message = "Ingresar el Usuario")
	private Integer id_usuario_sol;
	
	@NotBlank (message = "Ingresar Remitente")
	private String remitente;
	
	// FECHA_BUSQUEDA
		private Date fechaInicio;
		private Date fechaFinal;
	
	public TblExpedienteEO() {
		super();
	}
}
