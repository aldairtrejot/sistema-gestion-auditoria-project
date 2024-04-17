package gob.hidalgo.curso.components.sysoppf;

import java.util.Date;
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
import gob.hidalgo.curso.database.sysoppf.TblturnoEO;
import gob.hidalgo.curso.utils.Modelo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("TblturnoC")
public class TblturnoC extends ConstantesOfiPartPf{

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

	private String cveNom = getCnomCorrespondencia();
	private Integer cancelado = getCidEstatusCancelado();
	
	public TblturnoC() {
		super();
		log.debug("Se crea componente TblturnoC");
	}

	public TblturnoEO nuevo() {
		return new TblturnoEO();
	}

	public Modelo<TblturnoEO> modelo(TblturnoEO tblturnoEO) {
		Boolean bool = usuariosAdmin.usuarioAdmin();
		List<TblturnoEO> listado;
		String fechaInicio = fechaC.cambiarFecha(tblturnoEO.getFechaInicio());
		String fechaFinal = fechaC.cambiarFecha(tblturnoEO.getFechaFinal());
		Integer usuarioSys = usuarioSistemaC.usuarioActual();
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("fechaInicio", fechaInicio);
		parametros.put("fechaFinal", fechaFinal);
		parametros.put("usuarioSys", usuarioSys);
		if (bool) { // USUARIOS_ADMIN
			listado = sqlSession.selectList("tbl_turno.listadoAdmin", parametros);
		} else {// USUARIOS_RESTRICCIONES
			listado = sqlSession.selectList("tbl_turno.listado", parametros);
		}
		return new Modelo<>(listado);
	}

	public void guardar(TblturnoEO tbl_turno) {
		/*
		if (tbl_turno.getFecha_reg() != null) {
			Date fechaAuxDate = tbl_turno.getFecha_reg();
			tbl_turno.setFec_vencimiento(fechaC.agregarMeses(fechaAuxDate));
		}*/
		
		if (tbl_turno.getFecha_reg() == null && tbl_turno.getFec_vencimiento() == null) {
				tbl_turno.setFecha_reg(fechaC.FechaActual());
				tbl_turno.setFec_vencimiento(fechaC.agregarMeses(tbl_turno.getFecha_reg()));
		}
		
		
		tbl_turno.setNum_turno(catConsecutivosC.numOficio(tbl_turno.getId(), tbl_turno.getAnio_oficio(),	tbl_turno.getNum_turno(), cveNom));
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("tbl_turno", tbl_turno);
		if (tbl_turno.getId() == null) {
			sqlSession.insert("tbl_turno.agregar", parametros);
			bitacoraMovimientoC.bitacoraAgregar(tbl_turno, "tbl_turno", "Add");
			mensajesC.mensajeInfo("Control de Correspondencia Agregado");
		} else {
			sqlSession.update("tbl_turno.editar", parametros);
			bitacoraMovimientoC.bitacoraAgregar(tbl_turno, "tbl_turno", "Edit");
			mensajesC.mensajeInfo("Control de Correspondencia Modificado");
		}
	}

	public void editarEstatus(TblturnoEO tblturnoEO) {
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("id",tblturnoEO.getId());
		parametros.put("cancelado",cancelado);
		sqlSession.update("tbl_turno.editarEstatus", parametros);
		bitacoraMovimientoC.bitacoraAgregar(tblturnoEO, "tbl_turno", "Cancel");
		mensajesC.mensajeInfo("Correspondencia Cancelada");
	}
	
	public boolean ObjectNull(TblturnoEO t) {
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
