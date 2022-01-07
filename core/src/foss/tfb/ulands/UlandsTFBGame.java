package foss.tfb.ulands;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import foss.tfb.ulands.screen.DefaultScreen;
import foss.tfb.ulands.screen.MainMenuScreen;

public class UlandsTFBGame extends Game {

	final public static String DEFAULT_FONT = "default";
	public static Skin skin;



	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("skin/skin.json"));
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		DefaultScreen screen = (DefaultScreen) this.getScreen();
		screen.stage.getViewport().setScreenSize(width, height);
	}
}
