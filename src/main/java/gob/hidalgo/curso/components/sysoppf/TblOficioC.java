package gob.hidalgo.curso.components.sysoppf;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gob.hidalgo.curso.components.MensajesC;
import gob.hidalgo.curso.components.sysoppf.Admin.ConstantesOfiPartPf;
import gob.hidalgo.curso.components.sysoppf.Admin.FechaC;
import gob.hidalgo.curso.components.sysoppf.Admin.UsuarioSistemaC;
import gob.hidalgo.curso.components.sysoppf.Admin.UsuariosAdmin;
import gob.hidalgo.curso.database.sysoppf.TblOficioEO;
import gob.hidalgo.curso.utils.Modelo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("TblOficioC")
public class TblOficioC extends ConstantesOfiPartPf {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private CatConsecutivosC catConsecutivosC;

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
	
	private String cveNom = getCnomOficio();
	
	public TblOficioC() {
		super();
		log.debug("Se crea componente TblOficioC");
	}
	
	public TblOficioEO nuevo() {
		return new TblOficioEO ();
	}
	
	public Modelo<TblOficioEO> modelo(TblOficioEO tblOficioEO)  {
		Boolean bool = usuariosAdmin.usuarioAdmin();
		List<TblOficioEO> listado;
		String fechaInicio = fechaC.cambiarFecha(tblOficioEO.getFechaInicio());
		String fechaFinal = fechaC.cambiarFecha(tblOficioEO.getFechaFinal());
		Integer usuarioSys = usuarioSistemaC.usuarioActual();
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("fechaInicio", fechaInicio);
		parametros.put("fechaFinal", fechaFinal);
		parametros.put("usuarioSys", usuarioSys);
		if (bool == true) {
			listado = sqlSession.selectList("tbl_oficio.listadoAdmin",parametros);
		} else {
			listado = sqlSession.selectList("tbl_oficio.listado",parametros);
		}
		return new Modelo<>(listado);		
	}
	
	public void guardar(TblOficioEO tblOficioEO) {
		tblOficioEO.setNum_oficio(catConsecutivosC.numOficio(tblOficioEO.getId(), 
				tblOficioEO.getAnio(), tblOficioEO.getNum_oficio(), cveNom));
		if(tblOficioEO.getId() == null) {
			sqlSession.insert("tbl_oficio.agregar", tblOficioEO);
			bitacoraMovimientoC.bitacoraAgregar(tblOficioEO, "tbl_oficio", "Add");
			mensajesC.mensajeInfo("Tarjeta Informativa Agregada");
		} else {
			sqlSession.update("tbl_oficio.editar", tblOficioEO);
			bitacoraMovimientoC.bitacoraAgregar(tblOficioEO, "tbl_oficio", "Edit");
			mensajesC.mensajeInfo("Tarjeta Informativa Modificada");
		}
	}
	
	public boolean ObjectNull(TblOficioEO t) {
		Boolean bool = false;
		if (t != null) {
			bool = true;
		}
		return bool;
	}

	public String getCveNom() {
		return cveNom;
	}	
}
