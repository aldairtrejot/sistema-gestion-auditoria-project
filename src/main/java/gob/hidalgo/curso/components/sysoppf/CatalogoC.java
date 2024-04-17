package gob.hidalgo.curso.components.sysoppf;


import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import gob.hidalgo.curso.components.sysoppf.Admin.ConstantesOfiPartPf;
import gob.hidalgo.curso.components.sysoppf.Admin.FechaC;
import gob.hidalgo.curso.database.administracion.UsuarioEO;
import gob.hidalgo.curso.database.sysoppf.CatAreaEO;
import gob.hidalgo.curso.database.sysoppf.CatConsecutivosEO;
import gob.hidalgo.curso.database.sysoppf.CatEstatusEO;
import gob.hidalgo.curso.database.sysoppf.UserSolicitaEO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@RequestScoped
@ViewScoped
public class CatalogoC extends ConstantesOfiPartPf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private CatConsecutivosC catConsecutivosC;

	@Autowired
	private FechaC fechaC;

	private LinkedHashMap<String, Integer> catAnioList;
	private LinkedHashMap<String, Integer> catAreaList;
	private LinkedHashMap<String, Integer> catUsuariosList;
	private LinkedHashMap<String, Integer> catEstatusList;
	private LinkedHashMap<String, Integer> catAreaUserList;
	private LinkedHashMap<String, String> numOficio;
	private LinkedHashMap<String, Integer> catUsuariosAbogados;

	private Integer anioTurno;
	private Date fechaCatalogo = null;

	@PostConstruct
	public void init() {
		catAnioList = new LinkedHashMap<String, Integer>();
		catAnio(catAnioList);
		catAreaList = new LinkedHashMap<String, Integer>();
		catArea(catAreaList);
		catEstatusList = new LinkedHashMap<String, Integer>();
		catEstatus(catEstatusList);
		numOficio = new LinkedHashMap<String, String>();
		catNumOficio(numOficio);
		catUsuariosAbogados = new LinkedHashMap<String, Integer>();
		catUsuariosAbogados(catUsuariosAbogados);
		
	}

	@SuppressWarnings("unlikely-arg-type")
	public void cambioUsuarios(Integer id) {
		// idUsuario = id;
		if (id != null && !"".equals(id)) {
			catUsuariosList = new LinkedHashMap<String, Integer>();
			catUsuarios(catUsuariosList, id);
		} else {
			catUsuariosList = new LinkedHashMap<>();
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void cambioUsuariosArea(Integer id) {
		// idUsuarioArea = id;
		if (id != null && !"".equals(id)) {
			catAreaUserList = new LinkedHashMap<String, Integer>();
			catUsuariosArea(catAreaUserList, id);
		} else {
			catAreaUserList = new LinkedHashMap<>();
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void cambioTurno(Integer anio) {
		if (anio != null && !"".equals(anio)) {
			anioTurno = anio;
		}
	}
	
	/*
	public void cambioFecha(SelectEvent<Date> date) {
		fechaCatalogo = date.getObject();
	}*/

	public String cambioFechaVen(Date fecha) {
		/*Date fechaDate = fechaCatalogo;
		String fechaString = fechaC.cambiarFechaDMA(fecha);
		if (fechaDate != null) {
			if (fecha == null || fechA != fechaDate) {
				fechaDate = fechaC.agregarMeses(fechaDate);
				fechaString = fechaC.cambiarFechaDMA(fechaDate);
			}
		} else if (fechA != null) {
			fechaString = fechaC.cambiarFechaDMA(fecha);
		} else {
			fechaString = "";
		}
		return fechaString;*/
		String fechaString  = fechaC.cambiarFechaDMA(fecha);
		if (fecha == null) {
			Date fechaDate = fechaC.agregarMeses(fechaC.FechaActual());
			fechaString = fechaC.cambiarFechaDMA(fechaDate);
		}
		return fechaString;
	}

	private void catNumOficio(LinkedHashMap<String, String> map) {
		map.put(getCnumExpediente(), getCnumExpediente());
		map.put(getCnumCircular(), getCnumCircular());
		map.put(getCnumOficio(), getCnumOficio());
		map.put(getCnumTarjeta(), getCnumTarjeta());
	}

	public String impTurno(String numTurnoOrig, String cveNom) {
		String cveNomenglatura = cveNom;
		String numTurno = numTurnoOrig;
		Integer anio = anioTurno;
		if (anioTurno != null) {
			if (numTurnoOrig == "" || numTurnoOrig == null) {
				numTurno = catConsecutivosC.numOficio(anio, cveNomenglatura);
			}
		} else if (numTurno != "") {
			numTurno = numTurnoOrig;
		} else {
			numTurno = "";
		}
		return numTurno;
	}

	private void catUsuariosArea(LinkedHashMap<String, Integer> catAreaUserList, Integer id) {
		List<UserSolicitaEO> listado;
		listado = sqlSession.selectList("user_solicita.listado", id);
		for (UserSolicitaEO u : listado) {
			catAreaUserList.put(String.valueOf(u.getUsuario_sol()), u.getId_usuario_sol());
		}
	}
	
	private void catUsuariosAbogados(LinkedHashMap<String, Integer> catUsuariosAbogados) {
		List<UsuarioEO> listado;
		listado = sqlSession.selectList("usuarios.listadoUsuariosAbodago");
		for (UsuarioEO u : listado) {
			catUsuariosAbogados.put(String.valueOf(u.getNombre()), u.getId());
		}
	}

	private void catEstatus(LinkedHashMap<String, Integer> catUsuariosList) {
		List<CatEstatusEO> listado;
		listado = sqlSession.selectList("cat_estatus.listado");
		String string;
		for (CatEstatusEO c : listado) {
			string = String.valueOf(c.getId()) + " - " + String.valueOf(c.getDesc_estatus());
			catUsuariosList.put(string, c.getId());
		}
	}

	private void catUsuarios(LinkedHashMap<String, Integer> catUsuariosList, Integer id_area) {
		List<UsuarioEO> listado;
		listado = sqlSession.selectList("usuarios.listadoUsuarios", id_area);
		for (UsuarioEO u : listado) {
			catUsuariosList.put(String.valueOf(u.getNombre()), u.getId());
		}
	}

	private void catArea(LinkedHashMap<String, Integer> catAreaList) {
		List<CatAreaEO> listado;
		listado = sqlSession.selectList("cat_area.listado");
		String string;
		for (CatAreaEO c : listado) {
			string = String.valueOf(c.getId() + " - " + c.getDesc_area() + " " + c.getStatus_activo());
			catAreaList.put(string, c.getId());
		}
	}

	/*
	 * private void catUsuariosArea1(LinkedHashMap<String, Integer> catAreaUserList)
	 * { List<UserSolicitaEO> listado; listado =
	 * sqlSession.selectList("user_solicita.listadoUser"); for (UserSolicitaEO u :
	 * listado) {
	 * catAreaUserList.put(String.valueOf(u.getUsuario_sol()),u.getId_usuario_sol())
	 * ; } }
	 */

	private void catAnio(LinkedHashMap<String, Integer> catAnioList) {
		List<CatConsecutivosEO> listado;
		listado = sqlSession.selectList("cat_consecutivos.listadoAnio");
		for (CatConsecutivosEO c : listado) {
			catAnioList.put(String.valueOf(c.getAnio()), c.getAnio());
		}
	}

	public String FechaActual(Date fecha1) {
		String fechaString;
		if (fecha1 != null) {
			fechaString = fechaC.cambiarFechaDMA(fecha1);
		} else {
			java.util.Date fecha = new Date();
			fechaString = fechaC.cambiarFechaDMA(fecha);
		}
		return fechaString;
	}
}
