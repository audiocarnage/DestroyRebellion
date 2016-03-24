package Main;

import java.util.List;
import java.util.ArrayList;
import Levels.*;

public class LevelManager {
	
	private Level currentLevel;
	private List<Level> levels = new ArrayList<Level>();
	
	public LevelManager(Game game) {
		levels.add(new Level01(game));
		levels.add(new Level02(game));
		levels.add(new Level03(game));
		start();
	}
	
	private void start() {
		currentLevel = levels.get(0);
		currentLevel.load();
	}
	
	public boolean hasNext() {
		if (currentLevel.getNumber() < levels.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	private void next() {
		if (hasNext()) {
			currentLevel = levels.get(currentLevel.getNumber());
			currentLevel.load();
		}
	}
	
	public Level getLevel() {
		return currentLevel;
	}
	
	public void update(double timeSinceLastFrame) {
		currentLevel.update(timeSinceLastFrame);
		if (currentLevel.isLevelFinished()) {
			next();
		}
	}
}