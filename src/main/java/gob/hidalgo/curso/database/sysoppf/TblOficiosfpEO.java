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
@Alias("TblOficiosfpEO")
public class TblOficiosfpEO  extends EntityObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String num_turno_orig; 
	private String desc_asunto;	
	private String remitente; 
	private Date fecha_solicitud; 
	private String desc_observaciones; 
	private Date fec_registro;
	private Date fecha_acuse;
	
	//Messages @NotNull
	@NotNull (message = "Ingresar el Año")
	private Integer anio; 
	
	@NotNull (message = "Ingresar el Área")
	private Integer id_area; 
	
	@NotNull (message = "Ingresar el Usuario")
	private Integer id_usuario_sol; 
	
	@NotBlank (message = "Ingrese el Número de Oficio")
	private String num_oficio;
	
	// FECHA_BUSQUEDA
		private Date fechaInicio;
		private Date fechaFinal;
	
	public TblOficiosfpEO() {
		super();
	}
}
