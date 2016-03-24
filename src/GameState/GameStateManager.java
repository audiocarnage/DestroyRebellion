package GameState;

public class GameStateManager {

	private GameState currentState;
	
	public GameStateManager() {
	}
	
	public void setState(GameState state) {
		currentState = state;
	}
	
	public GameState getState() {
		return currentState;
	}
}