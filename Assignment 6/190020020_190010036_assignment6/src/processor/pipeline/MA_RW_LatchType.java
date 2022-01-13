package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable=false;
	boolean isNop = false;
	int idResult;
	String aluResult;
	String instruction;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public boolean isNop() {
		return isNop;
	}

	public void setNop(boolean nop) { isNop = nop; }

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

	public int getIdResult() {
		return idResult;
	}

	public void setIdResult(int idResult) {
		this.idResult = idResult;
	}
}
