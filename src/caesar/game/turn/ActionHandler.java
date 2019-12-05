package caesar.game.turn;
import caesar.game.Game;
import caesar.game.response.Response;

public interface ActionHandler {
	Response handle(Game game);
}