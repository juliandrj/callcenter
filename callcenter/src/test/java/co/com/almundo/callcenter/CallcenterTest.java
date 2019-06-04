package co.com.almundo.callcenter;

import org.apache.log4j.Logger;
import org.junit.Test;

import co.com.almundo.callcenter.despachador.Call;
import co.com.almundo.callcenter.despachador.Dispatcher;
import co.com.almundo.callcenter.helpers.InitHelper;
import co.com.almundo.callcenter.personas.Cliente;

/**
 * Unit test for simple App.
 */
public class CallcenterTest {
	
	final static Logger log = Logger.getLogger(CallcenterTest.class);

    /**
     * Test para validar comportamiento en diferente numero de llamadas entrantes.
     * @param n
     */
    private void llamadasEntrantes(int n) {
    	log.info("############################[ " + n + " ]############################");
		Dispatcher d = new Dispatcher();
    	d.setEmpleados(InitHelper.cargarListadoEmpleados());
    	for (int i = 0; i < n; i++) {
    		Cliente c = InitHelper.crearClienteAleatorio();
    		Call call = new Call(c);
    		call.addObserver(d);
    		d.dispatchCall(call);
    	}
    	while(d.ocupado()) {}
    	d.imprimirEstadisticas();
    	log.info("############################[ " + n + " ]############################");
    }
    
    @Test
    public void unaLlmada() {
    	this.llamadasEntrantes(1);
    }
    
    @Test
    public void diezLlmadas() {
    	this.llamadasEntrantes(10);
    }
    
    @Test
    public void cienLlmadas() {
    	this.llamadasEntrantes(100);
    }
    
    

}
