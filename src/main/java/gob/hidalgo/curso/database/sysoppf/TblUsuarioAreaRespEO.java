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
@Alias("TblUsuarioAreaRespEO")
public class TblUsuarioAreaRespEO  extends EntityObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id_area;
	private Integer id_usuario;
	private String status;
	
	public TblUsuarioAreaRespEO() {
		super();
	}
}
