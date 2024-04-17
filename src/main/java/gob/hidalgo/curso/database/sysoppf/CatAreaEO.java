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
@Alias("CatAreaEO")
public class CatAreaEO extends EntityObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String desc_area;
	private String status_activo;
	
	public CatAreaEO() {
		super();
	}
}
