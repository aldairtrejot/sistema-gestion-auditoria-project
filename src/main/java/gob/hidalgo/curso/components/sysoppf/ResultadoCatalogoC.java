package gob.hidalgo.curso.components.sysoppf;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@ViewScoped
public class ResultadoCatalogoC {

	@Autowired
	private SqlSession sqlSession;
	
	public String CatalogoArea(Integer id) {
		String string = null;
		if(id != null) {
			string = id + " - " + sqlSession.selectOne("cat_area.listadoString", id);
		}
		return string;
	}
	
	public String CatalogoAreaSN(Integer id) {
		String string = null;
		if(id != null) {
			string =  sqlSession.selectOne("cat_area.listadoString", id);
		}
		return string;
	}
	
	public String CatalogoUsuarios(Integer id) {
		String string = null;
		if (id != null) {
			string = sqlSession.selectOne("usuarios.listadoString", id);
		}
		return string;
	}
	
	 public String CatalogoUsuariosSys(Integer id) {
		 String string = null;
		 if(id != null) {
			 string = sqlSession.selectOne("user_solicita.listadoString", id);
		 }
		 return string;
	 }
	 
	 public String CatalogoEstatus(Integer id) {
		 String string = null;
		 if(id != null) {
			 string = id + " - " +sqlSession.selectOne("cat_estatus.listadoString",id);
		 }
		 return string;
	 }
	 
	 public String cambiarFecha(Date fechaInicio) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String fechaFinal = null;
			if ( fechaInicio != null) {
				 fechaFinal = formatter.format(fechaInicio);
			}
			return fechaFinal;
		}
	 
	 public Integer consecutivo() {
		 Integer numero = sqlSession.selectOne("cat_consecutivos.listaConsecutivo",1);
		 return numero;
	 }
	
}
