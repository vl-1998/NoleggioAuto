package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	//struttura principale di una coda PRIORITARIA, fatta di eventi di simulazione
	private PriorityQueue <Event> queue = new PriorityQueue <>();
	
	//la coda restituirà altri 3 tipi di variabili che sono:
	// - PARAMETRI DI SIMULAZIONE che dovranno essere impostabili dall'esterno, quindi avranno bisogno di setter
	//imposto valori di default, in modo che se dovessero esserci errori, la simulazione parte con un valore sensato
	private int NC=10; //numero totale di auto
	private Duration T_NC= Duration.of(10, ChronoUnit.MINUTES); //intervallo tra i clienti
	
	//imposto i parametri che mi indicano quanto dura la mia giornata di lavoro
	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(17, 00);
	
	
	// - MODELLO DEL MONDO
	//numero di auto ancora disponibili
	private int nAuto; //auto disponibili nel deposito (da 0 a NC)
	
	
	// - VALORI DA CALCOLARE
	//il simulatore dovra restituirli all'esterno, quindi avremo bisogno di getter
	private int clienti;
	private int clientiInsoddisfatti;
	

	//METODI PER IMPOSTARE I PARAMETRI
	public void setNumCars (int N) {
		this.NC=N;
	}
	
	public void setClientFrequency(Duration D) {
		this.T_NC=D;
	}
	
	//METODI PER RESTITUIRE I RISULTATI
	public int getClienti() {
		return clienti;
	}

	public int getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}
	
	//SIMULAZIONE VERA E PROPRIA
	//avvia la simulazione e la tiene in vita gestendo la lista degli eventi
	public void run() {
		//composta quasi sempre da due parti
		//preparazione iniziale in cui devo preparare le variabili del mondo e della cosa degli eventi, predisponendo
		//gli eventi che devono essere simulati
		this.nAuto=this.NC; //la mattina ho tutte le auto in deposito
		this.clienti=0;
		this.clientiInsoddisfatti=0;
		
		//inizializzo la coda degli eventi che dovra contenere tanti eventi di tipo "arrivo cliente" tutti cadenzati
		//ogni 10 minuti.
		//creo gli eventi creando un loop
		this.queue.clear(); //per sicurezza, ipotizzando che il metodo possa essere chiamato piu volte
		LocalTime oraArrivoCliente = this.oraApertura; //ipotizzo che il primo cliente arrivi all'ora di apertur
		//faccio un ciclo in cui vado ad aggiungere un nuovo cliente finche l'ora di arrivo del cliente è 
		//prima dell'ora di chiusura
		do {
			Event e = new Event(oraArrivoCliente, EventType.NEW_CLIENT); //alle ore TOT arriva un nuovo cliente
			this.queue.add(e);
			
			oraArrivoCliente = oraArrivoCliente.plus(this.T_NC);
		} while (oraArrivoCliente.isBefore(this.oraChiusura));
		
		//ESECUZIONE DEL CICLO DI SIMULAZIONE
		//estrae ogni volta un evento dalla coda
		//se la coda non è vuota ho ancora degli eventi da simulare e da estrarre dalla coda
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll(); //estraggo l'evento in ordine di tempo
			//System.out.println(e);

			processEvent(e);
		}	
	}
	
	private void processEvent (Event e) {
		//a seconda del tipo di evento che ricevo potro fare cose diverse
		//decido cosa fare nei singoli casi
		switch(e.getType()) {
		case NEW_CLIENT:
			// due possibilità, l'auto puo esserci o meno
			if (this.nAuto>0) {
				//cliente servito, auto noleggiata
				
				
				// - Aggiorno il modello del mondo;
				this.nAuto--; //l'auto è stata noleggiata
				
				
				// - Aggiorno i risultati;
				this.clienti++;
				
				
				// - Insersco eventualmente nuovi eventi
				//genero l'evento di restituzione dell'auto, la complicazione è quando restituire l'auto
				//calcolo un intervallo di tempo casuale di 1h, 2h o 3h
				double num = Math.random(); //numero tra [0 e 1) 
				//tra (0 e 1/3)=1h tra (1/3 e 2/3)=2h  tra (2/3 e 1)=3h
				Duration travel;
				if (num<1.0/3.0)
					travel = Duration.of(1, ChronoUnit.HOURS);
				else if (num<2.0/3.0)
					travel = Duration.of(2, ChronoUnit.HOURS);
				else
					travel = Duration.of(3, ChronoUnit.HOURS);
				
				//System.out.println(travel);
				//fatto questo dovro semplicemente creare un nuovo evento che ha come marcatura temporale 
				//il tempo dell'evento corrente, piu il tempo effettivo di noleggio calcolato e come evento
				//la restituzione dell'auto
				Event nuovo = new Event (e.getTime().plus(travel), EventType.CAR_RETURNED);
				this.queue.add(nuovo);
				
			} else {
				//cliente insoddisfatto
				//continuo ad avere 0 auto, non faccio nulla
				this.clienti++;
				this.clientiInsoddisfatti++;
			}
			break;
			
		case CAR_RETURNED:
			//quando torna un auto la metto in deposito
			this.nAuto++;
			break;
		}
		
	}

}
