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
import gob.hidalgo.curso.database.sysoppf.TblTarjetaInfoEO;
import gob.hidalgo.curso.utils.Modelo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("TblTarjetaInfoC")
public class TblTarjetaInfoC extends ConstantesOfiPartPf {

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
	
	private String cveNom = getCnomTarjeta();

	public TblTarjetaInfoC() {
		super();
		log.debug("Se crea componente TblTarjetaInfoC");
	}
	
	public TblTarjetaInfoEO nuevo() {
		return new TblTarjetaInfoEO();
	}
	
	public Modelo<TblTarjetaInfoEO> modelo(TblTarjetaInfoEO tblTarjetaInfoEO)  {
		Boolean bool = usuariosAdmin.usuarioAdmin();
		List<TblTarjetaInfoEO> listado;
		String fechaInicio = fechaC.cambiarFecha(tblTarjetaInfoEO.getFechaInicio());
		String fechaFinal = fechaC.cambiarFecha(tblTarjetaInfoEO.getFechaFinal());
		Integer usuarioSys = usuarioSistemaC.usuarioActual();
		HashMap<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("fechaInicio", fechaInicio);
		parametros.put("fechaFinal", fechaFinal);
		parametros.put("usuarioSys", usuarioSys);
		if (bool == true) {
			listado = sqlSession.selectList("tbl_tarjetainfor.listadoAdmin",parametros);
		} else {
			listado = sqlSession.selectList("tbl_tarjetainfor.listado",parametros);
		}
		return new Modelo<>(listado);		
	}
	
	public void guardar(TblTarjetaInfoEO tblTarjetaInfoEO) {
		tblTarjetaInfoEO.setNum_tarjeta(catConsecutivosC.numOficio(tblTarjetaInfoEO.getId(),
				tblTarjetaInfoEO.getAnio(), tblTarjetaInfoEO.getNum_tarjeta(),cveNom));
		if(tblTarjetaInfoEO.getId() == null) {
			sqlSession.insert("tbl_tarjetainfor.agregar", tblTarjetaInfoEO);
			bitacoraMovimientoC.bitacoraAgregar(tblTarjetaInfoEO, "tbl_tarjetainfor", "Add");
			mensajesC.mensajeInfo("Tarjeta Informativa Agregada");
		} else {
			sqlSession.update("tbl_tarjetainfor.editar", tblTarjetaInfoEO);
			bitacoraMovimientoC.bitacoraAgregar(tblTarjetaInfoEO, "tbl_tarjetainfor", "Edit");
			mensajesC.mensajeInfo("Tarjeta Informativa Modificada");
		}
	}
	
	public boolean ObjectNull(TblTarjetaInfoEO t) {
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
