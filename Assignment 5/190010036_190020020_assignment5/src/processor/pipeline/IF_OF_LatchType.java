package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	boolean isNop = false;
	int instruction;
	
	public IF_OF_LatchType()
	{
		OF_enable = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public boolean isNop() {
		return isNop;
	}

	public void setNop(boolean nop) { isNop = nop; }

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

}
