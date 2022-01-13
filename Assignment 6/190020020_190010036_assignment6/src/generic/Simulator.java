package generic;

import processor.Clock;
import processor.Processor;
import processor.memorysystem.Cache;

import java.io.*;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static EventQueue eventQueue;
	public static long storeresp;
	public static int ins_count;
	public static boolean isBusy = false;



	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		eventQueue = new EventQueue();
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);

		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{	DataInputStream fi = null;
		try {
			FileInputStream file = new FileInputStream(assemblyProgramFile);
			 fi = new DataInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int i = 0;
		try {
			int value = fi.readInt();
			processor.getRegisterFile().setProgramCounter(value);

		while (fi.available() > 0){
			value = fi.readInt();

			processor.getMainMemory().setWord(i, value);

			i+=1;

		}

		} catch (IOException e) {
			e.printStackTrace();
		}

		processor.getRegisterFile().setValue(0,0);
		processor.getRegisterFile().setValue(1,65535);
		processor.getRegisterFile().setValue(2,65535);




	}
	public static EventQueue getEventQueue() {
		return eventQueue ;
	}

	public static void simulate()
	{
		int cycles = 0;

		while(Simulator.simulationComplete == false) {
			if(eventQueue.queue.isEmpty() == true){
				isBusy = false;
			}else{
				isBusy = true;
			}
			if(!isBusy){
				processor.getRWUnit().performRW();
				processor.getMAUnit().performMA();
				processor.getEXUnit().performEX();
			}
			eventQueue.processEvents();
			if(!isBusy){
				processor.getOFUnit().performOF();
				processor.getIFUnit().performIF();
			}
			Clock.incrementClock();
			cycles += 1;
		}

		// TODO
		// set statistics

		Statistics.setNumberOfCycles(cycles);
		Statistics.setNumberOfInstructions(ins_count);
		Statistics.setIPC();
		Statistics.setNoOfAccessL1i(processor.getL1iCache().getNoOfAccess());
		Statistics.setNoOfAccessL1d(processor.getL1dCache().getNoOfAccess());


		if(processor.getL1iCache().getNoOfAccess() != 0){
			Statistics.setHitrateL1i((float) ((processor.getL1iCache().getNoOfHit()*1.0)/processor.getL1iCache().getNoOfAccess()));
		}
		if(processor.getL1dCache().getNoOfAccess() != 0){

			Statistics.setHitrateL1d((float) ((processor.getL1dCache().getNoOfHit()*1.0)/processor.getL1dCache().getNoOfAccess()));

		}

	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
