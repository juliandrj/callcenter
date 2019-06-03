/**
 * 
 */
package co.com.almundo.callcenter.personas;

import org.apache.log4j.Logger;

/**
 * @author juliandrj
 *
 */
public abstract class Empleado extends Persona {

	final static Logger log = Logger.getLogger(Empleado.class);
	private String cargo;

	@Override
	public void presentarse() {
		log.info("[" + this.getCargo().toUpperCase() + " " + this.getId() + "] Hola, soy el " + this.getCargo() + " " + this.getNombre() + ", ¿en qué puedo colaborarle?");
	}
	
	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	public void atenderLlamada() throws InterruptedException {
		long tiempoLlamada = 1000l * (5l + ((long) Math.ceil(5 * Math.random())));
		Thread.sleep(tiempoLlamada);
		log.info(" * Tiempo de la llamada: " + tiempoLlamada + "ms");
	}
	
}
