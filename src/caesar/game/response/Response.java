package caesar.game.response;

import caesar.game.turn.TurnType;
import org.jetbrains.annotations.NotNull;

public class Response {
	
	private String message;
	private ResponseAction action;
	private TurnType nextTurn;
	private final ResponseType type;
	
	public <T> Response(@NotNull T message, ResponseType type) {
		this.message = message.toString();
		this.type = type;
	}
	
	public Response(ResponseType type, TurnType turnType) {
		this.type = type;
		this.nextTurn = turnType;
	}
	
	public <T> Response(
		@NotNull T message,
		ResponseType type,
		TurnType turnType
	) {
		
		this.message = message.toString();
		this.type = type;
		this.nextTurn = turnType;
	}
	
	public <T> Response(
		@NotNull T message,
		ResponseType type,
		ResponseAction action
	) {
		
		this.message = message.toString();
		this.action = action;
		this.type = type;
	}
	
	public <T> Response(
		@NotNull T message,
		ResponseType type,
		ResponseAction action,
		TurnType turnType
	) {
		
		this.message = message.toString();
		this.action = action;
		this.type = type;
		this.nextTurn = turnType;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public ResponseType getType() {
		return this.type;
	}
	
	public TurnType getNextTurn() {
		return this.nextTurn;
	}
	
	public void initAction() {
		this.action.init();
	}
	
	public boolean isSuccessful() {
		return this.type == ResponseType.SUCCESS;
	}
	
	public boolean hasMessage() {
		return this.message != null;
	}
	
	public boolean hasNextTurn() {
		return this.nextTurn != null;
	}
	
	public boolean hasAction() {
		return this.action != null;
	}
}