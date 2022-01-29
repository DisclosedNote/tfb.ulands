package foss.tfb.ulands;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import foss.tfb.ulands.net.client.GameClient;
import foss.tfb.ulands.net.server.GameServer;
import foss.tfb.ulands.screen.*;

import java.io.IOException;

public class UlandsTFBGame extends Game {

	final public static String DEFAULT_FONT_STYLE = "default";
	final public static String SMALL_FONT_NAME = "roboto-11-regular";
	protected static Skin skin;
	protected InputMultiplexer multiplexer;

	protected GameServer server = new GameServer();
	protected GameClient client = new GameClient();

	public GameScreen gameScreen;
	public MainMenuScreen mainMenuScreen;
	public SettingsScreen settingsScreen;
	public HostLocalGameScreen hostLocalGameScreen;

	@Override
	public void create () {
		multiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(multiplexer);

		skin = new Skin(Gdx.files.internal("skin/skin.json"));

		createScreens();

		this.setScreen(mainMenuScreen);
	}

	public void createScreens()
	{
		hostLocalGameScreen = new HostLocalGameScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		settingsScreen = new SettingsScreen(this);
	}


	public InputMultiplexer getInputMultiplexer() {
		return multiplexer;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		server.stop();
		this.getScreen().dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		DefaultScreen screen = (DefaultScreen) this.getScreen();
		screen.getUIStage().getViewport().setScreenSize(width, height);
	}

	public static Skin getSkin() {
		return skin;
	}

	public void startLocalGameServer(String ip, int port)
	{
		if(this.server != null && this.server.isStarted()){
			this.server.stop();
			this.server = null;
		}

		this.server = new GameServer();

		try {
			this.server.setIPPort(ip, port);
			this.server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GameClient getClient(){
		return this.client;
	}

	public GameServer getLocalGameServer()
	{
		return this.server;
	}

}
