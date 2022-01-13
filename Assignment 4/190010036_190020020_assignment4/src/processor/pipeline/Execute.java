package processor.pipeline;

import generic.Statistics;
import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_OF_LatchType IF_OF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch, IF_OF_LatchType if_of_latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_OF_Latch = if_of_latch;
	}

	public String Extender(String str){			// Converts to 32 bit string

		if(str.substring(0,1).equals("0")){
			while(str.length() < 32){
				str =  '0'+ str ;
			}
		}else if(str.substring(0,1).equals("1")){
			while(str.length() < 32){
				str =  '1'+ str ;
			}
		}
		return str;

	}
	static String twoscomplement(StringBuffer str)
	{
		int n = str.length();

		// Traverse the string to get first '1' from
		// the last of string
		int i;
		for (i = n-1 ; i >= 0 ; i--)
			if (str.charAt(i) == '1')
				break;

		// If there exists no '1' concat 1 at the
		// starting of string
		if (i == -1)
			return "1" + str;

		// Continue traversal after the position of
		// first '1'
		for (int k = i-1 ; k >= 0; k--)
		{
			//Just flip the values
			if (str.charAt(k) == '1')
				str.replace(k, k+1, "0");
			else
				str.replace(k, k+1, "1");
		}

		// return the modified string
		return str.toString();
	}

	public String intToStr(int value){
		String binary_str = "";
		if(value >= 0){
			binary_str = "0"+ Integer.toString(value,2);
		}else{
			binary_str = "1"+ Integer.toString((value*-1),2);

		}
		return binary_str;
	}

	public int strToint(String str){

		if(str.substring(0,1).equals("0")){
			return Integer.parseInt(str,2);
		}else if(str.substring(0,1).equals("1")){
			String two_c = twoscomplement(new StringBuffer(str));
			return -1* Integer.parseInt(two_c,2);
		}else {
			return 0;
		}

	}

	public void performEX() {

		if(OF_EX_Latch.isNop()){
			EX_MA_Latch.setNop(true);
			OF_EX_Latch.setNop(false);
			EX_MA_Latch.setInstruction(null);
			return;
		}


		if (OF_EX_Latch.isEX_enable()) {

			String aluResult = null;
			int A = strToint(OF_EX_Latch.getop1());
			int B;
			Boolean isBranchTaken = false;
			String A_Binary;
			A_Binary = OF_EX_Latch.getop1();
			String inst_str = OF_EX_Latch.getInstruction();
			String opcode_str = null;

			opcode_str = inst_str.substring(0, 5);

			int opcode = Integer.parseInt(opcode_str, 2);

			// Is Immediate Check
			if ((opcode <= 21 && opcode % 2 == 1) || opcode == 22) {		// FOR R2I type and Load
				B = strToint(OF_EX_Latch.getimm());
			} else {
				B = strToint(OF_EX_Latch.getop2());
			}

			// ALU Starts

			switch (opcode_str) {
				case "00000":    // add
				case "00001":    // addi
					aluResult = intToStr(A + B);
					break;

				case "00010":    //sub
				case "00011":    //subi
					aluResult = intToStr(A - B);
					break;
				case "00100":    //mult
				case "00101":    //multi
					aluResult = intToStr(A * B);
					break;
				case "00110":    //div
				case "00111":    //divi
					aluResult = intToStr(A / B);
					containingProcessor.getRegisterFile().setValue(31, A % B);        // Set Reminder in x31
					break;
				case "01000":    //and
				case "01001":    //addi
					aluResult = intToStr(A & B);
					break;
				case "01010":    //or
				case "01011":    //ori
					aluResult = intToStr(A | B);

					break;
				case "01100":    //xor
				case "01101":    //xori
					aluResult = intToStr(A ^ B);
					break;
				case "01110":    //slt
				case "01111":    //slti

					if (A < B) {
						aluResult = Extender("01");
					} else {
						aluResult = Extender("00");
					}
					break;
				case "10000":    //sll
				case "10001":    //slli
					aluResult = intToStr(A << B);
					break;
				case "10010":    //srl
				case "10011":    //srli
					aluResult = intToStr(A >>> B);
					break;
				case "10100":    //sra
				case "10101":    //srai
					aluResult = "";
					for (int i = 0; i < B; i++) {
						aluResult += A_Binary.charAt(0);
					}
					aluResult = aluResult + A_Binary.substring(0, 32 - B);
					break;
				case "10110": 	// load
					aluResult = intToStr(A + B);
					break;

				case "11000":    //jmp
					isBranchTaken = true;
					aluResult = Extender("0");
					break;
				case "11001":    //beq
					aluResult = Extender("0");
					if (A == B) {
						isBranchTaken = true;
					}
					break;
				case "11010":    //bne
					aluResult = Extender("0");
					if (A != B) {
						isBranchTaken = true;
					}
					break;
				case "11011":    //blt
					aluResult = Extender("0");
					if (A < B) {
						isBranchTaken = true;
					}
					break;
				case "11100":    //bgt
					aluResult = Extender("0");
					if (A > B) {
						isBranchTaken = true;
					}
					break;


				default:
					aluResult = Extender("0");
			}

			if(opcode_str.equals("10111")){		// store rd + imm (op2 + imm)
				aluResult = intToStr((Integer.parseInt(OF_EX_Latch.getimm(), 2) + Integer.parseInt(OF_EX_Latch.getop2(), 2)));
			}

			aluResult = Extender(aluResult);

			if (isBranchTaken) {
				System.out.println("Control Hazard");
				IF_OF_Latch.setNop(true);


				EX_IF_Latch.setIF_enable(true);
				EX_IF_Latch.setBranchPC(OF_EX_Latch.branchTarget);
				EX_MA_Latch.setMA_enable(true);
				EX_MA_Latch.setop2(OF_EX_Latch.op2);
				EX_MA_Latch.setAluResult(aluResult);
				EX_MA_Latch.setInstruction(OF_EX_Latch.instruction);
			} else {

				EX_MA_Latch.setMA_enable(true);
				EX_MA_Latch.setop2(OF_EX_Latch.op2);
				EX_MA_Latch.setAluResult(aluResult);
				EX_MA_Latch.setInstruction(OF_EX_Latch.instruction);
			}


		}

	}

}
