/**
 * 
 */
package co.com.almundo.callcenter.personas;

import org.apache.log4j.Logger;

/**
 * @author juliandrj
 *
 */
public abstract class Persona {
	
	final static Logger log = Logger.getLogger(Persona.class);
	
	private long id;
	private String nombre;
	
	public void presentarse() {
		log.info("[" + this.id + "] Hola, mi nombre es " + this.nombre);
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
