package gob.hidalgo.curso.converters;

import java.util.Base64;
import java.util.StringTokenizer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@FacesConverter("ObjectConverter")
public class ObjectConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String cadena;
		ObjectMapper objectMapper;
		StringTokenizer tokenizer;
		Object objeto;
		String clase;
		String json;
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			cadena = new String(Base64.getDecoder().decode(value), "utf-8");
			tokenizer = new StringTokenizer(cadena, "@", true);
			if(tokenizer.countTokens() >= 3){
				clase = tokenizer.nextToken();
				tokenizer.nextToken(); //Descartar la @
				json = tokenizer.nextToken("");
			} else {
				clase = null;
				json = null;
			}
			objeto = objectMapper.readValue(json, Class.forName(clase));
		} catch (Exception e) {
			objeto = null;
			e.printStackTrace();
		}
		return objeto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		StringBuilder stringBuilder;
		ObjectMapper objectMapper;
		String respuesta=null;
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		stringBuilder = new StringBuilder(value.getClass().getName());
		stringBuilder.append("@");
		try {
			stringBuilder.append(objectMapper.writeValueAsString(value));
			respuesta = Base64.getEncoder().encodeToString(stringBuilder.toString().getBytes("utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return respuesta;
	}
}