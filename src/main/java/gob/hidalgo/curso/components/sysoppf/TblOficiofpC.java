package gob.hidalgo.curso.components.sysoppf;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gob.hidalgo.curso.components.MensajesC;
import gob.hidalgo.curso.components.sysoppf.Admin.FechaC;
import gob.hidalgo.curso.components.sysoppf.Admin.UsuarioSistemaC;
import gob.hidalgo.curso.components.sysoppf.Admin.UsuariosAdmin;
import gob.hidalgo.curso.database.sysoppf.TblOficiosfpEO;
import gob.hidalgo.curso.utils.Modelo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("TblOficiofpC")
public class TblOficiofpC {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private MensajesC mensajesC;
	
	@Autowired
	private FechaC fechaC;
	
	@Autowired
	private UsuarioSistemaC usuarioSistemaC;
	
	@Autowired
	private BitacoraMovimientoC bitacoraMovimientoC;
	
	@Autowired
	private UsuariosAdmin usuariosAdmin;

	public TblOficiofpC() {
		super();
		log.debug("Se crea componente TblOficiofpC");
	}
	
	public TblOficiosfpEO nuevo() {
		return new TblOficiosfpEO ();
	}
	
	public Modelo<TblOficiosfpEO> modelo(TblOficiosfpEO tblOficiosfpEO)  {
		Boolean bool = usuariosAdmin.usuarioAdmin();
		List<TblOficiosfpEO> listado;
		String fechaInicio = fechaC.cambiarFecha(tblOficiosfpEO.getFechaInicio());
		String fechaFinal = fechaC.cambiarFecha(tblOficiosfpEO.getFechaFinal());
		Integer usuarioSys = usuarioSistemaC.usuarioActual();
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("fechaInicio", fechaInicio);
		parametros.put("fechaFinal", fechaFinal);
		parametros.put("usuarioSys", usuarioSys);
		if (bool == true) {
			listado = sqlSession.selectList("tbl_oficiosfp.listadoAdmin",parametros);
		} else {
			listado = sqlSession.selectList("tbl_oficiosfp.listado",parametros);
		}
		
		return new Modelo<>(listado);		
	}
	
	public void guardar(TblOficiosfpEO tblOficiosfpEO) {
		if(tblOficiosfpEO.getId() == null) {
			sqlSession.insert("tbl_oficiosfp.agregar", tblOficiosfpEO);
			bitacoraMovimientoC.bitacoraAgregar(tblOficiosfpEO, "tbl_oficiosfp", "Add");
			mensajesC.mensajeInfo("Oficio Agregado");
		} else {
			sqlSession.update("tbl_oficiosfp.editar", tblOficiosfpEO);
			bitacoraMovimientoC.bitacoraAgregar(tblOficiosfpEO, "tbl_oficiosfp", "Edit");
			mensajesC.mensajeInfo("Oficio Modificado");
		}
	}
	
	public boolean ObjectNull(TblOficiosfpEO t) {
		Boolean bool = false;
		if (t != null) {
			bool = true;
		}
		return bool;
	}
}
