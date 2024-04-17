package gob.hidalgo.curso.components.administracion;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component("ParametrosC")
public class ParametrosC {
	
	private final String version = "0.0.0";
	private LocalDate fechaHoy;
	
	public ParametrosC() {
		super();
		fechaHoy = LocalDate.now();
		log.debug("Se crea objeto ParametrosC");
	}
	
	@Scheduled(cron = "1 * * * * *")
	private void actualizarFechaHoy() {
		fechaHoy = LocalDate.now();
	}
	
}
