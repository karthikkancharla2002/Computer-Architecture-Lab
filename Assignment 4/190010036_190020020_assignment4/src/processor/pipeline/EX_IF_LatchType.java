package processor.pipeline;

public class EX_IF_LatchType {
	boolean IF_enable=false;
	int branchPC;

	public EX_IF_LatchType()
	{
		IF_enable = false;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}

	public int getBranchPC() {
		return branchPC;
	}

	public void setBranchPC(int branchPC) {
		this.branchPC = branchPC;
	}
}
