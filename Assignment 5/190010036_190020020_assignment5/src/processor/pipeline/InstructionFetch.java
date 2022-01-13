package processor.pipeline;

import configuration.Configuration;
import generic.*;
import processor.Clock;
import processor.Processor;

public class InstructionFetch implements Element {

    Processor containingProcessor;
    IF_EnableLatchType IF_EnableLatch;
    IF_OF_LatchType IF_OF_Latch;
    EX_IF_LatchType EX_IF_Latch;

    public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch) {
        this.containingProcessor = containingProcessor;
        this.IF_EnableLatch = iF_EnableLatch;
        this.IF_OF_Latch = iF_OF_Latch;
        this.EX_IF_Latch = eX_IF_Latch;
    }

    public void performIF() {
        if (EX_IF_Latch.isIF_enable()) {


            containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.getBranchPC());
            int newInstruction = containingProcessor.getMainMemory().getWord(EX_IF_Latch.getBranchPC());

            Simulator.ins_count++;
            Simulator.getEventQueue().addEvent(
                    new MemoryReadEvent(
                            Clock.getCurrentTime() + Configuration.mainMemoryLatency,
                            this,
                            (Element) containingProcessor.getMainMemory(),
                            EX_IF_Latch.getBranchPC())
            );

            if (newInstruction == 0) {
                IF_OF_Latch.setNop(true);

                return;
            }
            if (IF_OF_Latch.getInstruction() == -402653184) { //for end
                containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.getBranchPC());
            } else {
                containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.getBranchPC() + 1);
                IF_OF_Latch.setInstruction(newInstruction);
            }


            IF_OF_Latch.setInstruction(newInstruction);

            EX_IF_Latch.setIF_enable(false);

            IF_OF_Latch.setOF_enable(true);
        } else if (IF_EnableLatch.isIF_enable()) {


            int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
            int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);

            Simulator.ins_count++;
            Simulator.getEventQueue().addEvent(
                    new MemoryReadEvent(
                            Clock.getCurrentTime() + Configuration.mainMemoryLatency,
                            this,
                            (Element) containingProcessor.getMainMemory(),
                            currentPC)
            );


            if (newInstruction == 0) {
                IF_OF_Latch.setNop(true);

                return;
            }
            if (IF_OF_Latch.getInstruction() == -402653184) { //for end we pass end again, and don't get any new inst or increment pc
                containingProcessor.getRegisterFile().setProgramCounter(currentPC);
            } else {
                containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
                IF_OF_Latch.setInstruction(newInstruction);
            }

            EX_IF_Latch.setIF_enable(false);

            IF_OF_Latch.setOF_enable(true);
        }
        IF_OF_Latch.setOF_enable(true);
    }

    @Override
    public void handleEvent(Event event) {

    }
}


