package polito.it.noleggio.model;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class TestSimulator {

	public static void main(String args[]) {
		//istanzia una classe simulator
		Simulator sim = new Simulator();
		
		//imposta i parametri di simulazione
		sim.setNumCars(10) ;
		sim.setClientFrequency(Duration.of(10, ChronoUnit.MINUTES)) ;//javatime che indica gli intervalli di tempo
		
		sim.run() ; //chiama un metodo affinche il simulatore faccia il suo lavoro
					//simulera la giornata di lavoro del noleggio
		
		int totClients = sim.getClienti() ; //quanti clienti sono arrivati
		int dissatisfied = sim.getClientiInsoddisfatti() ;//quanti clienti ho soddisfatto
		
		System.out.format("Arrived %d clients, %d were dissatisfied\n", 
				totClients, dissatisfied);
	}
	
}
