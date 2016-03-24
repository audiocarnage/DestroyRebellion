package GameState;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import Main.Game;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Menu {

	private Font title = new Font("DialogInput", Font.BOLD, 64);
	private Font menuFont = new Font("SansSerif", Font.BOLD, 48);
	private Font commands = new Font("SansSerif", Font.BOLD, 24);
	private Color commandsColor = new Color(0, 255, 50);
	private static final GameState[] options = {GameState.PLAY, GameState.QUIT};
	private int currentOption = 0;
	private static BufferedImage menuImage;
	private static BufferedImage darthVaderImage;
	
	static {
		try {
			menuImage = ImageIO.read(Menu.class.getClassLoader().getResourceAsStream("gfx/VenatorClassDestroyer.png"));
			darthVaderImage = ImageIO.read(Menu.class.getClassLoader().getResourceAsStream("gfx/DarthVader.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		BufferedImage after = darthVaderImage;
		double scale = 1;
		if (Game.getScreenWidth() < 1920) {
			scale = 0.67d;
			BufferedImage before = darthVaderImage;
			int w = before.getWidth();
			int h = before.getHeight();
			after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scale, scale);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(before, after);
		}
		g.drawImage(after, (int)(Game.getScreenWidth() - (after.getWidth() * scale)), 0, null);
		g.drawImage(menuImage, 10, 10, null);
		
		g.setFont(title);
		g.setColor(Color.WHITE);
		g.drawString(Game.getTitle(), (int)(Game.getScreenWidth() * 0.35), 100);
		
		g.setFont(menuFont);
		for (int i=0; i < options.length; i++) {
			if (i == currentOption) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i].toString(), (int)(menuImage.getWidth() + 50), (int)(menuImage.getHeight() * 0.5) + (i * 75));
		}
		
		g.setFont(commands);
		g.setColor(commandsColor);
		g.drawString("Commands:", 10, menuImage.getHeight());
		g.drawString("Primary weapon 'Space'  |  Secondary weapon 'J'  |  Menu 'Esc'", 10, menuImage.getHeight() + 40);
	}
	
	public void moveUp() {
		currentOption--;
		if (currentOption == -1) {
			currentOption = options.length - 1;
		}
	}
	
	public void moveDown() {
		currentOption++;
		if (currentOption == options.length) {
			currentOption = 0;
		}
	}
	
	public GameState selectOption() {
		return options[currentOption];
	}
}