package gob.hidalgo.curso.database.sysoppf;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import gob.hidalgo.curso.utils.EntityObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("BitacoraMovimientoEO")
public class BitacoraMovimientoEO extends EntityObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id_usuario;
	private String desc_tabla;
	private String desc_accion;
	private Date fecha_hora;
	
	public BitacoraMovimientoEO() {
		super();
	}
}
