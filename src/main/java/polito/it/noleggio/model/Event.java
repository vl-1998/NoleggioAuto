package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event> {
	//per rappresentare il singolo evento di simulazione, sempre presenti almeno 2 campi:
	// - tempo (Local Time, non ci interessa la data perche e una singola giornata)
	// - tipo di evento

	//classe enumerazioni per inserire insieme di costanti passte dall'utente
	public enum EventType {
		//elenco di costanti che possono essere definite
		NEW_CLIENT, CAR_RETURNED //tutte maiuscole perche è la convenzione java per indicare le costanti
	}
	
	//definisco il tipo, gli attributi della classe evento, due attributi che avremo sempre
	private LocalTime time;
	private EventType type;
	
	//dobbiamo definire il criterio di ordinamento di questi elementi, perchè finiranno in  una coda prioritaria
	//quindi è necessario che siano ordinati
	
	
	/**
	 * @param time
	 * @param type
	 */
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	
	//vanno messi in ordine di tempo
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}
	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	
	//non definisco equals o hash code, perchè mi risulta complicato avere un equals, perchè due clienti possono
	//arrivare nello stesso momento
	
	
	
	
}
