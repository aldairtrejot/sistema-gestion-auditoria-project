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
@Alias("UserSolAreaEO")
public class UserSolAreaEO extends EntityObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id_usuario_sol;
	private Integer id_area;
	
	public UserSolAreaEO() {
		super();
	}
}
