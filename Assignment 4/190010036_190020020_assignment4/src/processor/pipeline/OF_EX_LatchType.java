package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	boolean isNop = false;
	int branchTarget;
	String  instruction;
	String op1, op2, imm;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public boolean isNop() {
		return isNop;
	}

	public void setNop(boolean nop) { isNop = nop; }

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public String  getInstruction() {
		return instruction;
	}

	public void setInstruction(String  instruction) {
		this.instruction = instruction;
	}

	public String getop1() {
		return op1;
	}

	public void setop1(String op1) {
		this.op1 = op1;
	}

	public String getop2() {
		return op2;
	}

	public void setop2(String op2) {
		this.op2 = op2;
	}

	public String getimm() {
		return imm;
	}

	public void setimm(String imm) {
		this.imm = imm;
	}

	public int getBranchTarget() {
		return branchTarget;
	}

	public void setBranchTarget(int branchTarget) {
		this.branchTarget = branchTarget;
	}


}
