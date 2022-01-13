package generic;

import processor.Clock;
import processor.Processor;

import java.io.*;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
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
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getIFUnit().performIF();
			Clock.incrementClock();

			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions() + 1);
			Statistics.setNumberOfCycles(Statistics.getNumberOfCycles() + 1);

		}


	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
