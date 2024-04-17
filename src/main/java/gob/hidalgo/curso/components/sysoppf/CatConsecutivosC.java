package gob.hidalgo.curso.components.sysoppf;

import java.util.HashMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("CatConsecutivosC")
public class CatConsecutivosC {
	
		@Autowired
		private SqlSession sqlSession;
		
		public String numOficio(Integer id_objeto, Integer anio,String num_oficio_orig ,String cveNomenglatura) {
			String num_oficio = null;
			if(id_objeto == null) {
				HashMap<String, Object> parametros;
				parametros = new HashMap<>();
				parametros.put("anio", anio);
				parametros.put("cveNomenglatura", cveNomenglatura);
				Integer num_consecutivo = sqlSession.selectOne("cat_consecutivos.listaConsecutivo", parametros); num_consecutivo ++;
				String cve_nomenglatura = sqlSession.selectOne("cat_consecutivos.listadoNomenglatura", parametros);  cve_nomenglatura.replaceAll("\\[|\\]", "");
				String numero_consecutivoString = String.valueOf(num_consecutivo);
				num_oficio = cve_nomenglatura + "/" + agregar0(numero_consecutivoString) + "/" + anio;
				insertarConsecutivos(num_consecutivo, anio, cveNomenglatura);
			} else {
				num_oficio = num_oficio_orig;
			}
			return  num_oficio;
		}
		
		public String numOficio(Integer anio, String cveNomenglatura) {
				HashMap<String, Object> parametros;
				String num_oficio = "";
				parametros = new HashMap<>();
				parametros.put("anio", anio);
				parametros.put("cveNomenglatura", cveNomenglatura);
				Integer num_consecutivo = sqlSession.selectOne("cat_consecutivos.listaConsecutivo", parametros); num_consecutivo ++;
				String cve_nomenglatura = sqlSession.selectOne("cat_consecutivos.listadoNomenglatura", parametros);  cve_nomenglatura.replaceAll("\\[|\\]", "");
				String numero_consecutivoString = String.valueOf(num_consecutivo);
				num_oficio = cve_nomenglatura + "/" + agregar0(numero_consecutivoString) + "/" + anio;
			return  num_oficio;
		}

		private void insertarConsecutivos(Integer num, Integer anio, String cveNomenglatura) {
			HashMap<String, Object> parametros;
			parametros = new HashMap<>();
			parametros.put("num", num);
			parametros.put("anio", anio);
			parametros.put("cveNomenglatura", cveNomenglatura);
			sqlSession.insert("cat_consecutivos.editarConsecutivo",parametros);
		}
		
		private String agregar0(String numero) {
			String numeroAux = "";
			int numero_caracteres = numero.length();
			while (numero_caracteres != 4) {
				numeroAux += "0";
				numero_caracteres ++;
			}
			return (numeroAux += numero);
		}	
}
