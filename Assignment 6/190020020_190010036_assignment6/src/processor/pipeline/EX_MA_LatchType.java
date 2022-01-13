package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	boolean isNop = false;
	String  instruction;
	String op2,aluResult;

	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public boolean isNop() {
		return isNop;
	}

	public void setNop(boolean nop) { isNop = nop; }

	public String getop2() {
		return op2;
	}

	public void setop2(String op2) {
		this.op2 = op2;
	}

	public String  getInstruction() {
		return instruction;
	}

	public void setInstruction(String  instruction) {
		this.instruction = instruction;
	}

	public String getAluResult() {
		return aluResult;
	}

	public void setAluResult(String aluResult) {
		this.aluResult = aluResult;
	}
}
