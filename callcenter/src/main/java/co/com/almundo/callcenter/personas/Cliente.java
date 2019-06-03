/**
 * 
 */
package co.com.almundo.callcenter.personas;

import org.apache.log4j.Logger;

/**
 * @author juliandrj
 *
 */
public class Cliente extends Persona {
	
	final static Logger log = Logger.getLogger(Cliente.class);

	@Override
	public void presentarse() {
		log.info("[CLIENTE " + this.getId() + "] Hola, mi nombre es " + this.getNombre());
	}

}
