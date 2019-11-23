package caesar.game.turn;

import caesar.ui.Message;

class Response {
	
	Message message;
	ResponseType type;
	
	Response(Message message, ResponseType type) {
		
		this.message = message;
		this.type = type;
	}
	
	Response(ResponseType type) {
		this.type = type;
	}
	
	Response() {}
}
