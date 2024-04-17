package gob.hidalgo.curso.components.sysoppf.Admin;

import javax.annotation.ManagedBean;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@ManagedBean
@Component("UsuariosAdmin")
public class UsuariosAdmin {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private UsuarioSistemaC usuarioSistemaC;

	public boolean usuarioAdmin() {
		Integer id_usuario = usuarioSistemaC.usuarioActual();
		Integer id = sqlSession.selectOne("usuarios.listadoAdmin", id_usuario);
		boolean bool = false;
		if (id != null) {
			bool = true;
		}
		return bool;
	}
	
	public UsuariosAdmin() {
		super();
	}
	
}
