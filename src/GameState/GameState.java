package GameState;

public enum GameState {

	PLAY("Play"), RESTART("Restart"), 
	MENU("Menu"), HELP("Help"), QUIT("Quit");
	private String name = "";
	
	private GameState(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}