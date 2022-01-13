package processor.pipeline;

import generic.Statistics;
import processor.Processor;
import generic.Simulator;

public class OperandFetch {
    Processor containingProcessor;
    IF_OF_LatchType IF_OF_Latch;
    OF_EX_LatchType OF_EX_Latch;
    EX_MA_LatchType EX_MA_Latch;
    MA_RW_LatchType MA_RW_Latch;
    IF_EnableLatchType IF_EnableLatch;

    public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType ex_ma_latch, MA_RW_LatchType ma_rw_latch, IF_EnableLatchType if_enablelatch) {
        this.containingProcessor = containingProcessor;
        this.IF_OF_Latch = iF_OF_Latch;
        this.OF_EX_Latch = oF_EX_Latch;
        this.EX_MA_Latch = ex_ma_latch;
        this.MA_RW_Latch = ma_rw_latch;
        this.IF_EnableLatch = if_enablelatch;

    }

    static String twoscomplement(StringBuffer str) {
        int n = str.length();

        // Traverse the string to get first '1' from
        // the last of string
        int i;
        for (i = n - 1; i >= 0; i--)
            if (str.charAt(i) == '1')
                break;

        // If there exists no '1' concat 1 at the
        // starting of string
        if (i == -1)
            return "1" + str;

        // Continue traversal after the position of
        // first '1'
        for (int k = i - 1; k >= 0; k--) {
            //Just flip the values
            if (str.charAt(k) == '1')
                str.replace(k, k + 1, "0");
            else
                str.replace(k, k + 1, "1");
        }

        // return the modified string
        return str.toString();
    }

    public String Extender(String str) {            // Converts to 32 bit string

        if (str.charAt(0) == '0') {
            while (str.length() < 32) {
                str = '0' + str;
            }
        } else if (str.charAt(0) == '1') {
            while (str.length() < 32) {
                str = '1' + str;
            }
        }
        return str;

    }

    public String intToStr(int value) {
        String binary_str = "";
        if (value >= 0) {
            binary_str = "0" + Integer.toString(value, 2);
        } else {
            String rrr = Integer.toString(value, 2).substring(1);
            binary_str = "1" + twoscomplement(new StringBuffer(rrr));
        }
        return binary_str;
    }

    public int strToint(String str) {

        if (str.charAt(0) == '0') {
            return Integer.parseInt(str, 2);
        } else if (str.charAt(0) == '1') {
            String two_c = twoscomplement(new StringBuffer(str));
            return -1 * Integer.parseInt(two_c, 2);
        } else {
            return 0;
        }

    }

    public boolean data_interlock(String inst_str_OF) {

        int rs1 = -1, rs2 = -1;

        String opcode = inst_str_OF.substring(0, 5);
        int opcode_int = Integer.parseInt(opcode, 2);


        if (opcode_int <= 21 && opcode_int % 2 == 1) {        // FOR R2I type and Store
            rs1 = Integer.parseInt(inst_str_OF.substring(5, 10), 2);        // getting rs1 R2I-Type
        } else if (opcode_int <= 21 && opcode_int % 2 == 0) {
            rs1 = Integer.parseInt(inst_str_OF.substring(5, 10), 2);        // getting rs1 R3 Type
            rs2 = Integer.parseInt(inst_str_OF.substring(10, 15), 2);        // getting rs2 R3 Type
        } else if (opcode_int >= 22 && opcode_int != 24 && opcode_int <= 28) {        // R2I type for ld,st, branch stats
            rs1 = Integer.parseInt(inst_str_OF.substring(5, 10), 2);        // getting rs1 R2I-Type
            rs2 = Integer.parseInt(inst_str_OF.substring(10, 15), 2);        // getting rd R2I-Type
        }


        String inst_str = OF_EX_Latch.getInstruction();
        if (inst_str != null) {
            opcode = inst_str.substring(0, 5);
            opcode_int = Integer.parseInt(opcode, 2);
            Boolean isWb = opcode_int <= 22;
            if (isWb) {
                int rd;
                if ((opcode_int <= 21 && opcode_int % 2 == 1) || opcode_int == 22) {        // FOR R2I type and Load
                    rd = Integer.parseInt(inst_str.substring(10, 15), 2);        // getting rd
                } else {
                    rd = Integer.parseInt(inst_str.substring(15, 20), 2);        // getting rd
                }

                if (rs1 == rd || rs2 == rd) {
                    IF_EnableLatch.setIF_enable(false);
                    OF_EX_Latch.setNop(true);
                    OF_EX_Latch.setInstruction(null);
                    return true;
                }
            }
        }


        inst_str = EX_MA_Latch.getInstruction();
        if (inst_str != null) {
            opcode = inst_str.substring(0, 5);
            opcode_int = Integer.parseInt(opcode, 2);
            Boolean isWb = opcode_int <= 22;
            if (isWb) {
                int rd;
                if ((opcode_int <= 21 && opcode_int % 2 == 1) || opcode_int == 22) {        // FOR R2I type and Load
                    rd = Integer.parseInt(inst_str.substring(10, 15), 2);        // getting rd
                } else {
                    rd = Integer.parseInt(inst_str.substring(15, 20), 2);        // getting rd
                }

                if (rs1 == rd || rs2 == rd) {
                    IF_EnableLatch.setIF_enable(false);
                    OF_EX_Latch.setNop(true);
                    OF_EX_Latch.setInstruction(null);
                    return true;
                }
            }
        }


        inst_str = MA_RW_Latch.getInstruction();
        if (inst_str != null) {
            opcode = inst_str.substring(0, 5);
            opcode_int = Integer.parseInt(opcode, 2);
            Boolean isWb = opcode_int <= 22;
            if (isWb) {
                int rd;
                if ((opcode_int <= 21 && opcode_int % 2 == 1) || opcode_int == 22) {        // FOR R2I type and Load
                    rd = Integer.parseInt(inst_str.substring(10, 15), 2);        // getting rd
                } else {
                    rd = Integer.parseInt(inst_str.substring(15, 20), 2);        // getting rd
                }

                if (rs1 == rd || rs2 == rd) {
                    IF_EnableLatch.setIF_enable(false);
                    OF_EX_Latch.setNop(true);
                    OF_EX_Latch.setInstruction(null);
                    return true;
                }
            }

        }


        IF_EnableLatch.setIF_enable(true);
        OF_EX_Latch.setNop(false);
        return false;
    }

    public void performOF() {
        if (IF_OF_Latch.isNop()) {
            IF_OF_Latch.setNop(false);
            OF_EX_Latch.setNop(true);
            OF_EX_Latch.setInstruction(null);
            return;
        }

        if (IF_OF_Latch.isOF_enable()) {


            String op2;
            String op1, imm;

            int inst = IF_OF_Latch.getInstruction();

            String inst_str = Extender(intToStr(inst));


            String opcode = inst_str.substring(0, 5);


            int PC = containingProcessor.getRegisterFile().getProgramCounter();
            int offset = strToint(inst_str.substring(15));    // By Default, we check for R2I Type
            if (opcode.equals("11000")) {     // jmp is RI Type
                offset = strToint(inst_str.substring(10));
            }

            imm = inst_str.substring(15);
            imm = Extender(imm);

            op2 = intToStr(containingProcessor.getRegisterFile().getValue(Integer.parseInt(inst_str.substring(10, 15), 2)));

            op1 = intToStr(containingProcessor.getRegisterFile().getValue(Integer.parseInt(inst_str.substring(5, 10), 2)));


            if (opcode.equals("11000")) { //for jump RI type
                op2 = intToStr(containingProcessor.getRegisterFile().getValue(Integer.parseInt(inst_str.substring(5, 10), 2)));
            }

            op1 = Extender(op1);
            op2 = Extender(op2);
            int branchTarget = PC + offset - 1;

            if (data_interlock(inst_str)) {
                System.out.println("Data interlock");
                return;
            }

            OF_EX_Latch.setInstruction(inst_str);
            OF_EX_Latch.setop1(op1);
            OF_EX_Latch.setop2(op2);
            OF_EX_Latch.setimm(imm);
            OF_EX_Latch.setBranchTarget(branchTarget);

            OF_EX_Latch.setEX_enable(true);
        }
    }

}
