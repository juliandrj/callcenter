/**
 * 
 */
package co.com.almundo.callcenter.despachador;

import org.apache.log4j.Logger;

import co.com.almundo.callcenter.personas.Cliente;
import co.com.almundo.callcenter.personas.Empleado;

/**
 * @author juliandrj
 *
 */
public class Call extends Observable implements Dispatcheable {
	
	final static Logger log = Logger.getLogger(Call.class);
	
	private Cliente cliente;
	private Empleado empleado;
	
	public Call(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void atenderLlamada(Empleado empleado) throws InterruptedException {
		this.empleado = empleado;
		this.empleado.presentarse();
		this.empleado.atenderLlamada();
		log.info("[FIN DE LA LLAMADA] " + this.cliente.getId() + " - " + this.empleado.getId());
		this.notifyObservers(this);
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
