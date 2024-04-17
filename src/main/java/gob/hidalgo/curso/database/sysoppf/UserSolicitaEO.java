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
@Alias("UserSolicitaEO")
public class UserSolicitaEO extends EntityObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id_usuario_sol;
	private String usuario_sol;
	
	public UserSolicitaEO() {
		super();
	}
}
