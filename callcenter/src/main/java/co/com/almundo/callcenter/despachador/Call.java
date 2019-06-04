/**
 * 
 */
package co.com.almundo.callcenter.despachador;

import java.util.Observable;

import org.apache.log4j.Logger;

import co.com.almundo.callcenter.personas.Cliente;
import co.com.almundo.callcenter.personas.Empleado;

/**
 * @author juliandrj
 *
 */
public class Call extends Observable implements Runnable {
	
	final static Logger log = Logger.getLogger(Call.class);
	
	private Cliente cliente;
	private Empleado empleado;
	
	public Call(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void run() {
		try {
			this.empleado.presentarse();
			this.empleado.atenderLlamada();
			this.setChanged();
			log.info("[FIN DE LA LLAMADA] Fue un gusto atenderlo -> " + this.cliente.getId() + " - " + this.empleado.getId());
			this.notifyObservers();
		} catch(InterruptedException ex) {
			log.error("No se logro atender la llamada.", ex);
		}
	}
	
	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the empleado
	 */
	public Empleado getEmpleado() {
		return empleado;
	}
	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

}
