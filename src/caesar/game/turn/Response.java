package caesar.game.turn;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Response {
	
	private String message;
	private ResponseType type;
	
	public <T> Response(@NotNull T message, ResponseType type) {
		
		this.message = "\n" + message.toString();
		this.type = type;
	}
	
	@Contract(pure = true)
	public Response(ResponseType type) {
		this.type = type;
	}
	
	@Contract(pure = true)
	public Response() {}
	
	String getMessage() {
		return message;
	}
	
	public <T> void setMessage(@NotNull T message) {
		this.message = "\n" + message.toString();
	}
	
	public void setType(ResponseType type) {
		this.type = type;
	}
	
	boolean isSuccessful() {
		return this.type == ResponseType.SUCCESS;
	}
	
	boolean hasMessage() {
		return this.message != null;
	}
}
