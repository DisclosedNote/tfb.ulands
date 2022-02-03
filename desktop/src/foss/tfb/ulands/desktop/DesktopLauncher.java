package foss.tfb.ulands.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import foss.tfb.ulands.UlandsTFBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Ultimate Lands: The Forced Behaviour";
		config.addIcon("desktop-launcher-logo-128.png", Files.FileType.Internal);
		config.addIcon("desktop-launcher-logo-32.png", Files.FileType.Internal);
		config.addIcon("desktop-launcher-logo-16.png", Files.FileType.Internal);
		new LwjglApplication(new UlandsTFBGame(), config);
	}
}
