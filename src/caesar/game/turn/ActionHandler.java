package caesar.game.turn;
import caesar.game.Game;

public interface ActionHandler {
	Response handle(Game game);
}