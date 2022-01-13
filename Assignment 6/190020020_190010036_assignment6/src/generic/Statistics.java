package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles;
	static float IPC;
	static float hitrateL1i;
	static int noOfAccessL1i;
	static float hitrateL1d;
	static int noOfAccessL1d;



	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("IPC: " + IPC);

			writer.println(("L1i No of Access: "+ noOfAccessL1i));
			writer.println(("L1i Hitrate: "+ hitrateL1i));

			writer.println(("L1d No of Access: "+ noOfAccessL1d));
			writer.println(("L1d Hitrate: "+ hitrateL1d));
			
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public static int getNumberOfCycles() {
		return Statistics.numberOfCycles;
	}
	public static int getNumberOfInstructions() {
		return Statistics.numberOfInstructions;
	}
	public static void setIPC() {
		Statistics.IPC = (float)numberOfInstructions/(float)numberOfCycles;
	}

	public static void setHitrateL1i(float hitrate) {
        Statistics.hitrateL1i = hitrate;
    }

    // set noOfAccess
	public static void setNoOfAccessL1i(int noOfAccess) {
        Statistics.noOfAccessL1i = noOfAccess;
    }

	public static void setHitrateL1d(float hitrate) {
		Statistics.hitrateL1d = hitrate;
	}

	// set noOfAccess
	public static void setNoOfAccessL1d(int noOfAccess) {
		Statistics.noOfAccessL1d = noOfAccess;
	}

}
