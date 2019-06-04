package co.com.almundo.callcenter;

import co.com.almundo.callcenter.despachador.Dispatcher;
import co.com.almundo.callcenter.helpers.InitHelper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Dispatcher d = new Dispatcher();
    	d.setEmpleados(InitHelper.cargarListadoEmpleados());
//    	d.getEmpleados().addAll(Arrays.asList());
//        Cliente c = new Cliente();
//        c.setId(80096119l);
//        c.setNombre("Julian Rojas");
//        Call call = new Call(c);
//        call.addObserver(d);
//        Empleado e = new Operador();
//        e.setId(43999888l);
//        e.setNombre("Elizabeth Rodriguez");
//        try {
//			call.atenderLlamada(e);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    }
}
