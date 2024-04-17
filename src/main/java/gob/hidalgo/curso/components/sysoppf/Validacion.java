package gob.hidalgo.curso.components.sysoppf;

import javax.annotation.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import gob.hidalgo.curso.components.MensajesC;
import gob.hidalgo.curso.components.sysoppf.Admin.UsuariosAdmin;

@ManagedBean
public class Validacion {

	@Autowired
	private MensajesC mensajesC;

	@Autowired
	private SqlSession sqlSession;

	@SuppressWarnings("unused")
	@Autowired
	private UsuariosAdmin usuariosAdmin;

	public void numTurno(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String numTurno = String.valueOf(arg2);
		Integer numeroTurno = sqlSession.selectOne("tbl_turno.listadoTurno", numTurno);
		if (numTurno != "" ) {
			if (numeroTurno == null) {
				mensajesC.messageError("El Núm. de Turno No Esta Asociado a Correspondencia");
			}
		}
	}

	public void cancelamiento(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String numEstatus = String.valueOf(arg2);
		String cancelamiento = "3";
		if (numEstatus.equals(cancelamiento)) {
			mensajesC.messageError("El Estatus No Puede Estar Cancelado");
		}
	}
}
