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
import gob.hidalgo.curso.database.sysoppf.TblExpedienteEO;
import gob.hidalgo.curso.utils.Modelo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("TblExpedienteC")
public class TblExpedienteC extends ConstantesOfiPartPf {

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
	
	private String cveNom = getCnomExpediente();

	public TblExpedienteC() {
		super();
		log.debug("Se crea componente TblExpedienteC");
	}
	
	public TblExpedienteEO nuevo() {
		return new TblExpedienteEO();
	}
	
	public Modelo<TblExpedienteEO> modelo(TblExpedienteEO tblExpedienteEO)  {
		Boolean bool = usuariosAdmin.usuarioAdmin();
		List<TblExpedienteEO> listado;
		Integer usuarioSys = usuarioSistemaC.usuarioActual();
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		String fechaInicio = fechaC.cambiarFecha(tblExpedienteEO.getFechaInicio());
		String fechaFinal = fechaC.cambiarFecha(tblExpedienteEO.getFechaFinal());
		parametros.put("fechaInicio", fechaInicio);
		parametros.put("fechaFinal", fechaFinal);
		parametros.put("usuarioSys", usuarioSys);
		if (bool == true) {
			listado = sqlSession.selectList("tbl_expediente.listadoAdmin",parametros);
		} else {
			listado = sqlSession.selectList("tbl_expediente.listado",parametros);
		}
		return new Modelo<>(listado);		
	}
	
	public void guardar(TblExpedienteEO tblExpedienteEO) {
		tblExpedienteEO.setNum_expediente(catConsecutivosC.numOficio(tblExpedienteEO.getId(), tblExpedienteEO.getAnio(), tblExpedienteEO.getNum_expediente(),
				cveNom));
		if(tblExpedienteEO.getId() == null) {
			sqlSession.insert("tbl_expediente.agregar", tblExpedienteEO);
			bitacoraMovimientoC.bitacoraAgregar(tblExpedienteEO, "tbl_expediente", "Add");
			mensajesC.mensajeInfo("Control de Expediente Agregado");
		} else {
			sqlSession.update("tbl_expediente.editar", tblExpedienteEO);
			bitacoraMovimientoC.bitacoraAgregar(tblExpedienteEO, "tbl_expediente", "Edit");
			mensajesC.mensajeInfo("Control de Expediente Modificado");
		}
	}
	public boolean ObjectNull(TblExpedienteEO t) {
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
