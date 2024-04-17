package gob.hidalgo.curso.components.sysoppf.Admin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("UsuarioSistemaC")
public class UsuarioSistemaC {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Integer usuarioActual() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String nick = authentication.getName();
		Integer id = sqlSession.selectOne("usuarios.resultadoId",nick);
		return id;
	}
}
