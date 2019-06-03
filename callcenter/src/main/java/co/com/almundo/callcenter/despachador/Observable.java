/**
 * 
 */
package co.com.almundo.callcenter.despachador;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author juliandrj
 *
 */
public abstract class Observable {

	private Set<Observer> observers;
	
	public Observable() {
		this.observers = new HashSet<Observer>();
	}
	
	public void addObserver(Observer observer) {
		this.observers.add(observer);
	}
	
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}
	
	public void notifyObservers(Dispatcheable obj) {
		Iterator<Observer> it = this.observers.iterator();
		while (it.hasNext()) {
			it.next().update(obj);
		}
	}
	
}
