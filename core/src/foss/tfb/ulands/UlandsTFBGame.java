package foss.tfb.ulands;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import foss.tfb.ulands.assets.AssetManager;
import foss.tfb.ulands.net.client.GameClient;
import foss.tfb.ulands.net.server.GameServer;
import foss.tfb.ulands.screen.DefaultScreen;
import foss.tfb.ulands.screen.MainMenuScreen;

import java.io.IOException;

public class UlandsTFBGame extends Game {

	final public static String DEFAULT_FONT = "default";
	protected static Skin skin;
	protected InputMultiplexer multiplexer;

	public static AssetManager assetManager;

	protected GameServer server = new GameServer();
	protected GameClient client = new GameClient();

	@Override
	public void create () {
		multiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(multiplexer);

		skin = new Skin(Gdx.files.internal("skin/skin.json"));
		this.setScreen(new MainMenuScreen(this));

		assetManager = new AssetManager();
		assetManager.finishLoading();
	}

	public InputMultiplexer getInputMultiplexer() {
		return multiplexer;
	}

	@Override
	public void render ()
	{
		super.render();
	}
	
	@Override
	public void dispose () {
		this.getScreen().dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		DefaultScreen screen = (DefaultScreen) this.getScreen();
		screen.getStage().getViewport().setScreenSize(width, height);
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
