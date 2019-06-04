/**
 * 
 */
package co.com.almundo.callcenter.despachador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	
	private Map<String, Integer> conteoLlamadas;
	private Map<String, Long> tiempoLlamadas;
	
	/**
	 * Constructor
	 */
	public Dispatcher() {
		this.lineas = new Thread[10];
		this.lineasDisponibles = new ArrayList<Integer>();
		for (int i = 0; i < this.lineas.length; i++) {
			this.lineasDisponibles.add(i);
		}
		this.empleadosDisponibles = new ArrayList<Integer>();
		this.cola = new ArrayList<Call>();
		this.conteoLlamadas = new HashMap<String, Integer>();
		this.tiempoLlamadas = new HashMap<String, Long>();
	}

	/**
	 * Al finalizar una llamada se invoca este metodo
	 */
	public synchronized void update(Observable o, Object arg) {
		Call c = (Call) o;
		this.dispatchCall(c);
	}
	
	/**
	 * Se encarga de asignar las llamadas a partir de listas con índices disponibles,
	 * tanto para líneas como para empleados. Si no existen disponibles encola la llamada.
	 * La cola es FIFO.
	 * @param call
	 */
	public void dispatchCall(Call call) {
		//Si la llamada entrante ya fue atendida se ejecuta lo necesario para liberar recursos.
		if (call.isAtendida()) {
			this.lineasDisponibles.add(call.getLine());
			this.empleadosDisponibles.add(call.getEmpleado().getIde());
			Integer[] ems = this.empleadosDisponibles.toArray(new Integer[this.empleadosDisponibles.size()]);
			Arrays.sort(ems);
			this.empleadosDisponibles.clear();
			this.empleadosDisponibles.addAll(Arrays.asList(ems));
			if (log.isDebugEnabled()) {
				log.debug("[empleados disponibles] " + Arrays.toString(this.empleadosDisponibles.toArray()));
				log.debug("[lineas disponibles] " + Arrays.toString(this.lineasDisponibles.toArray()));
			}
			this.lineas[call.getLine()] = null;
			log.debug(" >> Liberando linea y empleado: [" + call.getLine() + ", " + call.getEmpleado().getIde() + "]");
			log.info("[TIEMPO TOTAL LLAMADA] " + call.getTiempo() + "ms");
			this.registrarEstadistica(call.getEmpleado().getCargo(), call.getTiempo());
			if (!this.cola.isEmpty()) {
				Call cn = this.cola.get(0);
				this.cola.remove(0);
				this.dispatchCall(cn);
			}
		//Si no es atendida y existen recursos disponibles, se asigna el hilo y el empleado.
		} else if (!call.isAtendida() && !this.lineasDisponibles.isEmpty() && !this.empleadosDisponibles.isEmpty()) {
			int n = this.lineasDisponibles.get(0);
			this.lineasDisponibles.remove(0);
			int m = this.empleadosDisponibles.get(0);
			this.empleadosDisponibles.remove(0);
			log.debug(" >> Asignando linea y empleado: [" + n + ", " + m + "]");
			Empleado e = this.empleados.get(m);
			e.setIde(n);
			call.setLine(n);
			call.setEmpleado(e);
			this.lineas[n] = new Thread(call);
			this.lineas[n].start();
		//Se encola la llamada
		} else {
			this.cola.add(call);
			log.debug(" >> Llamada en cola: (" + this.cola.size() + ") " + call.getCliente().getId() + " " + call.getCliente().getNombre());
		}
	}
	
	/**
	 * Registra el conteo y el tiempo total de las llamadas
	 * @param cargo
	 * @param tiempo
	 */
	private synchronized void registrarEstadistica(String cargo, long tiempo) {
		this.conteoLlamadas.put(cargo, this.conteoLlamadas.get(cargo) + 1);
		this.tiempoLlamadas.put(cargo, this.tiempoLlamadas.get(cargo) + tiempo);
	}
	
	/**
	 * Imprime las estadísticas.
	 */
	public void imprimirEstadisticas() {
		log.info("#################################################");
		log.info(" * [CARGO]: [CONTEO LLAMADAS], [TIEMPO TOTAL] -> [TIEMPO PROMEDIO]");
		Iterator<String> it = this.conteoLlamadas.keySet().iterator();
		while (it.hasNext()) {
			String k = it.next();
			log.info(" * " + k + ": " + this.conteoLlamadas.get(k) + ", " + this.tiempoLlamadas.get(k) + " -> " + (this.tiempoLlamadas.get(k) / this.conteoLlamadas.get(k)));
		}
		log.info("#################################################");
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
	
	/**
	 * Agrega los empleados que van a estar disponibles.
	 * @param empleados
	 */
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
			if (!this.conteoLlamadas.containsKey(this.getEmpleados().get(i).getCargo())) {
				this.conteoLlamadas.put(this.getEmpleados().get(i).getCargo(), 0);
				this.tiempoLlamadas.put(this.getEmpleados().get(i).getCargo(), 0l);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("[empleados disponibles] " + Arrays.toString(this.empleadosDisponibles.toArray()));
		}
	}
	
	/**
	 * Si existen hilos activos retorna verdadero.
	 * @return
	 */
	public boolean ocupado() {
		for (Thread t : this.lineas) {
			if (t != null && t.isAlive()) {
				return true;
			}
		}
		return false;
	}

}
