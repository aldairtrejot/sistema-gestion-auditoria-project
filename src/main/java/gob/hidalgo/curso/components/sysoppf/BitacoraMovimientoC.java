package gob.hidalgo.curso.components.sysoppf;

import java.util.HashMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gob.hidalgo.curso.components.sysoppf.Admin.UsuarioSistemaC;

@Component("BitacoraMovimientoC")
public class BitacoraMovimientoC {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired 
	private UsuarioSistemaC usuarioSistemaC;
	
	public void bitacoraAgregar(Object object, String nombreTabla, String accion) {
		String desc_accion = accion + " - VALUES - " + object.toString();
		Integer id_usuario = usuarioSistemaC.usuarioActual();
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("id_usuario", id_usuario);
		parametros.put("nombreTabla", nombreTabla);
		parametros.put("desc_accion", desc_accion);
		sqlSession.insert("bitacora_movimiento.agregar", parametros);
	}
}
