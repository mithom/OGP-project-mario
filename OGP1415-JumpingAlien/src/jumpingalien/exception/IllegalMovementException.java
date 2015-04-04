package jumpingalien.exception;

import be.kuleuven.cs.som.annotate.*;

public class IllegalMovementException extends IllegalArgumentException{
	private final String msg;
	public IllegalMovementException(String text){
		msg = text;
	}
	private static final long serialVersionUID = -5365630128856068164L;
	
	@Basic @Immutable
	public String getMessage(){
		return msg;
	}
}
