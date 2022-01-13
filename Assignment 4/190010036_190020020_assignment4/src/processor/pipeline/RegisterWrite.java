package processor.pipeline;

import generic.Simulator;
import generic.Statistics;
import processor.Processor;

public class RegisterWrite {
    Processor containingProcessor;
    MA_RW_LatchType MA_RW_Latch;
    IF_EnableLatchType IF_EnableLatch;

    public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
        this.containingProcessor = containingProcessor;
        this.MA_RW_Latch = mA_RW_Latch;
        this.IF_EnableLatch = iF_EnableLatch;
    }


    public void performRW() {

        if(MA_RW_Latch.isNop()){
            MA_RW_Latch.setNop(false);
            return;
        }



        if (MA_RW_Latch.isRW_enable()) {


            String inst = MA_RW_Latch.getInstruction();
            String opcode = inst.substring(0, 5);

            if(opcode.equals("11101")){
                Simulator.setSimulationComplete(true); // Setting for 'end'
                return;
            }

            Boolean isWb = Integer.parseInt(opcode, 2) <= 22;
            if (isWb) {
                int opcode_int = Integer.parseInt(opcode, 2);
                int A;
                if ((opcode_int <= 21 && opcode_int % 2 == 1) || opcode_int == 22) {		// FOR R2I type and Load
                    A = Integer.parseInt(inst.substring(10, 15), 2);        // getting rd
                } else {
                    A = Integer.parseInt(inst.substring(15, 20), 2);        // getting rd
                }

                int D;
                if (opcode.equals("10110")) {
                    D = MA_RW_Latch.getIdResult();
                } else {
                    D = (int) Long.parseLong(MA_RW_Latch.getAluResult(), 2);
                }



                containingProcessor.getRegisterFile().setValue(A, D);    // setting value in register

            }


        }
    }

}
