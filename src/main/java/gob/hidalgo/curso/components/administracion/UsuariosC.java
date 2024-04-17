package gob.hidalgo.curso.components.administracion;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import gob.hidalgo.curso.beans.seguridad.Usuario;
import gob.hidalgo.curso.components.MensajesC;
import gob.hidalgo.curso.database.administracion.GrupoEO;
import gob.hidalgo.curso.database.administracion.UsuarioEO;
import gob.hidalgo.curso.utils.Modelo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("UsuariosC")
public class UsuariosC {
	
	@Autowired 
	private SqlSession sqlSession;
	
	@Autowired
	private MensajesC mensajesC;
	
	public UsuariosC() {
		super();
		log.debug("Se crea componente UsuariosC");
	}

	public Modelo<UsuarioEO> modelo() {
		List<UsuarioEO> listado;
		listado = sqlSession.selectList("usuarios.listado");
		return new Modelo<>(listado);
	}
	
	public Modelo<UsuarioEO> modeloPorGrupo(GrupoEO grupo) {
		List<UsuarioEO> listado;
		listado = sqlSession.selectList("usuariosGrupos.usuariosPorGrupo", grupo);
		return new Modelo<>(listado);
	}
	
	public UsuarioEO nuevo() {
		return new UsuarioEO();
	}
	
	public void guardar(UsuarioEO usuario) {
		if(usuario.getId() == null) {
			String pw = BCrypt.hashpw(usuario.getPw(), BCrypt.gensalt(10));
			HashMap<String, Object> parametros;
			parametros = new HashMap<>();
			parametros.put("pw", pw);
			parametros.put("usuario", usuario);
			sqlSession.insert("usuarios.insertar", parametros);
			mensajesC.mensajeInfo("Usuario agregado exitosamente");
		} else {
			sqlSession.update("usuarios.actualizar", usuario); 
			mensajesC.mensajeInfo("Usuario actualizado exitosamente");
		}
	}
	
	public boolean cambiarPassword(String actual, String nueva){
		boolean respuesta;
		HashMap<String, Object> parametros;
		UsuarioEO usuario;
		respuesta = false;
		parametros = new HashMap<>();
		usuario = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsuario();
		parametros.put("actual", BCrypt.hashpw(actual, BCrypt.gensalt(10)));
		parametros.put("nueva", BCrypt.hashpw(nueva, BCrypt.gensalt(10)));
		parametros.put("usuario", usuario);
		String actualbd = sqlSession.selectOne("usuarios.comprobarPassword", parametros);
		if (BCrypt.checkpw(actual, actualbd)) {
			sqlSession.update("usuarios.cambiarPassword", parametros);
			respuesta = true;
		} else {
			respuesta = false;
		}
		return respuesta;
	}
}
