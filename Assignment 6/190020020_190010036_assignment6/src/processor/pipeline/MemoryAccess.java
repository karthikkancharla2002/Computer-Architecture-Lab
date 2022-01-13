package processor.pipeline;

import configuration.Configuration;
import generic.*;
import processor.Clock;
import processor.Processor;
import processor.memorysystem.Cache;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	Cache L1dCache;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, Cache L1dCache)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.L1dCache = L1dCache;
	}

	public void performMA()
	{

		if(EX_MA_Latch.isNop()){
			EX_MA_Latch.setNop(false);
			MA_RW_Latch.setNop(true);
			MA_RW_Latch.setInstruction(null);
			return;
		}

		if(EX_MA_Latch.isMA_enable()){

			String opcode = EX_MA_Latch.getInstruction().substring(0,5);

			if (opcode.equals("10110")){ 	// Load
					int address = Integer.parseInt(EX_MA_Latch.aluResult, 2);		// get address in int
					int idResult = containingProcessor.getMainMemory().getWord(address);	// get value from address form memory
				if(L1dCache.isInCache(address)){
					Simulator.getEventQueue().addEvent(
							new MemoryReadEvent(
									Clock.getCurrentTime() + Configuration.L1d_latency,
									(Element) this,
									containingProcessor.getMainMemory(), address)
					);
				}else{
					L1dCache.addToCache(address);

					Simulator.getEventQueue().addEvent(
							new MemoryReadEvent(
									Clock.getCurrentTime() + Configuration.mainMemoryLatency,
									(Element) this,
									containingProcessor.getMainMemory(), address)
					);
				}


					MA_RW_Latch.setIdResult(idResult);


			}else if(opcode.equals("10111")){ 		// Store
					int address = Integer.parseInt(EX_MA_Latch.getAluResult(), 2);		// get address in int
					int rs1 = Integer.parseInt(EX_MA_Latch.getInstruction().substring(5,10),2);	// getting rs1
					int value_rs1 = containingProcessor.getRegisterFile().getValue(rs1);	// getting value in rs1
					containingProcessor.getMainMemory().setWord(address, value_rs1);			// setting value in memory
				Simulator.storeresp = Clock.getCurrentTime();
				Simulator.getEventQueue().addEvent(
						new MemoryWriteEvent(
								Clock.getCurrentTime() + Configuration.mainMemoryLatency,
								(Element) this,
								containingProcessor.getMainMemory(),
								address,
								value_rs1)
				);

				MA_RW_Latch.setIdResult(0);
			}

			MA_RW_Latch.setAluResult(EX_MA_Latch.aluResult);
			MA_RW_Latch.setInstruction(EX_MA_Latch.getInstruction());

			MA_RW_Latch.setRW_enable(true);

		}

	}

	@Override
	public void handleEvent(Event event) {

	}
}
