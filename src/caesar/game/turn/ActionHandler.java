package caesar.game.turn;

import caesar.game.Game;
import caesar.game.response.Response;
import org.jetbrains.annotations.NotNull;

public interface ActionHandler {
	@NotNull Response handle(Game game);
}