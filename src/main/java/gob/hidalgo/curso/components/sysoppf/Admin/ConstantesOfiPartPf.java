package gob.hidalgo.curso.components.sysoppf.Admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstantesOfiPartPf {
	
	/*
	 * AGREGAR 3 MESES A FECHA tbl_turno, fec_vencimiento
	 */
	final private Integer Cmeses = 3; 
	
	
	
	/*
	 *TIPO DE DOCUMENTO COMBOX
	 */
	final private String CnumExpediente = "No. ExpedienteE";
	final private String CnumCircular = "No. CircularE";
	final private String CnumOficio = "No. OficioE";
	final private String CnumTarjeta = "No. TarjetaE";
	
	
	
	/*
	 * 
	 * CAT CONSECUTIVOS
	 */
	final private String CnomCorrespondencia = "DAOF";
	final private String CnomCircular = "CIR";
	final private String CnomExpediente = "EXP";
	final private String CnomOficio = "OF. COCTE-DAOF-";
	final private String CnomTarjeta = "TI";
	
	
	/*
	 * CAT ESTATUS
	 */
	final private Integer CidEstatusCancelado = 3; //Cancelamiento
	
	
	
	/*
	 * GENERAR REPORTE
	 */
	
	/*
	 --SERVER--
	final private String REPORTE_PLATILLA_PDF = "/root/home/plantillas/REPORTE_PLANTILLA.pdf";
	final private String REPORTE_GENERADO_PDF = "/root/home/plantillas/REPORTE_GENERADO.pdf";
	
	--LOCALHOST--
	final private String REPORTE_PLATILLA_PDF =  "/Users/aldairtrejo/Desktop/reporte/REPORTE_PLANTILLA.pdf";
	final private String REPORTE_GENERADO_PDF = "/Users/aldairtrejo/Desktop/reporte/REPORTE_GENERADO.pdf";
	*/

	final private String REPORTE_PLATILLA_PDF = "/reporte/REPORTE_PLANTILLA.pdf";
	final private String REPORTE_GENERADO_PDF = "REPORTE_GENERADO.pdf";
	final private String REPORTE_JAVA = "java.io.tmpdir";
	
	final private String Rfecha = "Rfecha_reg";									//fecha
	final private String RnoTurnoSF = "Rbturnosf";							// No turno S.F.
	final private String RnoOficio = "Rnum_expediente_ori";				//No de oficio
	final private String RnoTurnoPF = "Rnum_turno";						//No de oficio sistema 
	final private String Rremitido = "Rremitente";								//Remitido por
	final private String Rcargo = "Rcargo_remitente";						//Cargo
	final private String Rlugar = "Rlugar";											//Lugar
	final private String RAsunto = "Rasunto";										//Asunto
	final private String RTurneseA = "Rid_area_atension";					//Turnese a
	final private String RFojas = "Rnum_fojas";									//Fojas
	final private String RTomo = "Rnum_tomos";								//Tomos
	final private String RObservaciones = "Rdesc_observacion";		//Observaciones
	final private String RTermino = "Rterrmino";								//Termino
	
}
