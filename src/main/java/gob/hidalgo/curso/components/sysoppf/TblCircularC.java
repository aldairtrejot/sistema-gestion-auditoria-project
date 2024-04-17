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
import gob.hidalgo.curso.database.sysoppf.TblCircularEO;
import gob.hidalgo.curso.utils.Modelo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("TblCircularC")
public class TblCircularC extends ConstantesOfiPartPf {

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
	
	private String cveNom = getCnomCircular();

	public TblCircularC() {
		super();
		log.debug("Se crea componente TblCircularC ");
	}
	
	public TblCircularEO nuevo() {
		return new TblCircularEO();
	}
	
	public Modelo<TblCircularEO> modelo(TblCircularEO tblCircularEO)  {
		Boolean bool = usuariosAdmin.usuarioAdmin();
		List<TblCircularEO> listado;
		String fechaInicio = fechaC.cambiarFecha(tblCircularEO.getFechaInicio());
		String fechaFinal = fechaC.cambiarFecha(tblCircularEO.getFechaFinal());
		Integer usuarioSys = usuarioSistemaC.usuarioActual();
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("fechaInicio", fechaInicio);
		parametros.put("fechaFinal", fechaFinal);
		parametros.put("usuarioSys", usuarioSys);
		if (bool == true) {
			listado = sqlSession.selectList("tbl_circular.listadoAdmin",parametros);
		} else {
			listado = sqlSession.selectList("tbl_circular.listado",parametros);
		}
		return new Modelo<>(listado);		
	}
	
	public void guardar(TblCircularEO tblCircularEO) {
		tblCircularEO.setNum_circular(catConsecutivosC.numOficio(tblCircularEO.getId(),tblCircularEO.getAnio(), tblCircularEO.getNum_circular(),cveNom));
		if(tblCircularEO.getId() == null) {
			sqlSession.insert("tbl_circular.agregar", tblCircularEO);
			bitacoraMovimientoC.bitacoraAgregar(tblCircularEO, "tbl_circular", "Add");
			mensajesC.mensajeInfo("Control de Circular Agregado");
		} else {
			sqlSession.update("tbl_circular.editar", tblCircularEO);
			bitacoraMovimientoC.bitacoraAgregar(tblCircularEO, "tbl_circular", "Edit");
			mensajesC.mensajeInfo("Control de Circular Modificado");
		}
	}
	
	public boolean ObjectNull(TblCircularEO t) {
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
