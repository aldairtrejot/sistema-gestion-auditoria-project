package gob.hidalgo.curso.database.administracion;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gob.hidalgo.curso.utils.EntityObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("UsuarioEO")
public class UsuarioEO extends EntityObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotBlank(message = "Favor de anotar el Nick")
	private String nick;

	@NotBlank(message = "Favor de anotar el Nombre")
	private String nombre;
	
	private Boolean habilitado;
	private String pw;
	private String pw2;
	
	public UsuarioEO() {
		super();
		habilitado = true;
	}
	
	@JsonIgnore
	public String getNombreBiblioteca() {
		return getNombre();
	}
}
