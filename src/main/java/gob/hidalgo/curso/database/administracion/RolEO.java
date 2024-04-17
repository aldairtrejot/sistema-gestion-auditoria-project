package gob.hidalgo.curso.database.administracion;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import gob.hidalgo.curso.utils.EntityObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("RolEO")
public class RolEO extends EntityObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nombre;
	private String descripcion;
}
