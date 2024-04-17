package gob.hidalgo.curso.components;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContextHolder;

@Component("MensajesC")
public class MensajesC {
	
	public void mensajeError(String mensaje) {
		RequestContextHolder.getRequestContext().getMessageContext().addMessage(new MessageBuilder().error().defaultText(mensaje).build());
	}
	
	public void mensajeInfo(String mensaje) {
		RequestContextHolder.getRequestContext().getMessageContext().addMessage(new MessageBuilder().info().defaultText(mensaje).build());
	}
	
	public void mensajeWarn(String mensaje) {
		RequestContextHolder.getRequestContext().getMessageContext().addMessage(new MessageBuilder().warning().defaultText(mensaje).build());
	}
	
	public void mensajeFatal(String mensaje) {
		RequestContextHolder.getRequestContext().getMessageContext().addMessage(new MessageBuilder().fatal().defaultText(mensaje).build());
	}
	
	public void messageError(String message) {
		FacesMessage messageError = new FacesMessage(message); messageError.setSeverity(FacesMessage.SEVERITY_ERROR); throw new ValidatorException(messageError);
	}
}
