package foss.tfb.ulands.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import foss.tfb.ulands.UlandsTFBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Ultimate Lands: The Forced Behaviour");
		config.setWindowIcon(Files.FileType.Internal,
			"desktop-launcher-logo-128.png",
			"desktop-launcher-logo-32.png",
			"desktop-launcher-logo-16.png"
		);
		config.setWindowSizeLimits(640, 480, Integer.MAX_VALUE, Integer.MAX_VALUE);
		new Lwjgl3Application(new UlandsTFBGame(), config);
	}
}
