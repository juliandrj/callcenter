/**
 * 
 */
package co.com.almundo.callcenter.helpers;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import co.com.almundo.callcenter.personas.Cliente;
import co.com.almundo.callcenter.personas.Director;
import co.com.almundo.callcenter.personas.Empleado;
import co.com.almundo.callcenter.personas.Operador;
import co.com.almundo.callcenter.personas.Supervisor;

/**
 * @author juliandrj
 *
 */
public final class InitHelper {
	
	final static Logger log = Logger.getLogger(InitHelper.class);

	public static Empleado[] cargarListadoEmpleados() {
		try {
			InputStream is = InitHelper.class.getClassLoader().getResourceAsStream("empleados.json");
	    	InputStreamReader isr = new InputStreamReader(is);
	    	JsonElement json = new Gson().fromJson(isr, JsonElement.class);
	    	List<Operador> op = new ArrayList<Operador>();
	    	Operador[] ops = new Gson().fromJson(json.getAsJsonObject().get("operadores").getAsJsonArray(), Operador[].class);
	    	op.addAll(Arrays.asList(ops));
	    	log.debug(" * Operadores: " + op.size());
	    	List<Supervisor> su = new ArrayList<Supervisor>();
	    	Supervisor[] sus = new Gson().fromJson(json.getAsJsonObject().get("supervisores").getAsJsonArray(), Supervisor[].class);
	    	su.addAll(Arrays.asList(sus));
	    	log.debug(" * Supervisores: " + su.size());
	    	List<Director> di = new ArrayList<Director>();
	    	Director[] dis = new Gson().fromJson(json.getAsJsonObject().get("directores").getAsJsonArray(), Director[].class);
	    	di.addAll(Arrays.asList(dis));
	    	log.debug(" * Directores: " + di.size());
	    	List<Empleado> empleados = new ArrayList<Empleado>();
	    	empleados.addAll(op);
	    	empleados.addAll(su);
	    	empleados.addAll(di);
	    	log.debug(" -> Total: " + empleados.size());
	    	return empleados.toArray(new Empleado[empleados.size()]);
		} catch (Exception ex) {
			log.error("No se logro cargar el listado de empleados", ex);
			return null;
		}
	}
	
	public static Cliente crearClienteAleatorio() {
		Cliente c = new Cliente();
		c.setId((long) Math.ceil(Math.random() * 10000000));
		c.setNombre(RandomStringUtils.random(16));
		return c;
	}
	
}
