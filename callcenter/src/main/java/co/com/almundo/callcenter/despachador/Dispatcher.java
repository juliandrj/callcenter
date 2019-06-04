/**
 * 
 */
package co.com.almundo.callcenter.despachador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import co.com.almundo.callcenter.personas.Empleado;

/**
 * @author juliandrj
 *
 */
public class Dispatcher implements Observer {
	
	final static Logger log = Logger.getLogger(Dispatcher.class);
	
	private List<Empleado> empleados;
	private List<Call> cola;
	
	private Thread[] lineas;
	private List<Integer> lineasDisponibles;
	private List<Integer> empleadosDisponibles;
	
	public Dispatcher() {
		this.lineas = new Thread[10];
		this.lineasDisponibles = new ArrayList<Integer>();
		for (int i = 0; i < this.lineas.length; i++) {
			this.lineasDisponibles.add(i);
		}
		this.empleadosDisponibles = new ArrayList<Integer>();
	}

	public void update(Observable o, Object arg) {
		log.debug(">> " + ((Call) o).getEmpleado().getCargo());
	}
	
	public void dispatchCall(Call call) {
		this.cola.add(call);
	}

	/**
	 * @return the empleados
	 */
	public List<Empleado> getEmpleados() {
		if (this.empleados == null) {
			this.empleados = new ArrayList<Empleado>();
		}
		return empleados;
	}
	
	public void setEmpleados(Empleado... empleados) {
		int n = 0;
		if (!this.empleadosDisponibles.isEmpty()) {
			Integer[] ns = this.empleadosDisponibles.toArray(new Integer[this.empleadosDisponibles.size()]);
			Arrays.sort(ns);
			n = ns[ns.length - 1] + 1;
		}
		this.getEmpleados().addAll(Arrays.asList(empleados));
		for (int i = 0; i < this.getEmpleados().size(); i++) {
			this.empleadosDisponibles.add(n + i);
		}
	}

}
