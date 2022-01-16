package foss.tfb.ulands.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import foss.tfb.ulands.UlandsTFBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Ultimate Lands: The Forced Behaviour";
		new LwjglApplication(new UlandsTFBGame(), config);
	}
}
