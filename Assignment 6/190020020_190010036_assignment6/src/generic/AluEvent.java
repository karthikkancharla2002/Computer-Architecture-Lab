package generic;


public class AluEvent extends Event {



	public AluEvent(long eventTime, Element requestingElement, Element processingElement, String opcode,int a, int b) {
		super(eventTime, EventType.AluEvent, requestingElement, processingElement);

	}



}
