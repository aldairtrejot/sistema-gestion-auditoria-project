package gob.hidalgo.curso.database.sysoppf;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import gob.hidalgo.curso.utils.EntityObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("CatConsecutivosEO")
public class CatConsecutivosEO extends EntityObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cve_nomenglatura;
	private String cve_correspondencia;
	private String desc_corrrespondencia;
	private Integer num_consecutivo;
	private Integer anio;
	
	public CatConsecutivosEO() {
		super();
	}
}
