package foss.tfb.ulands.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import foss.tfb.ulands.UlandsTFBGame;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.net.client.GameClient;
import foss.tfb.ulands.ui.window.DefaultWindow;

import java.io.IOException;

public class ConnectingToServerScreen extends MenuScreen
{
    protected int port;
    protected String host;
    protected Label status;
    protected DefaultWindow window;

    protected Listener connectionListener;

    protected GameClient client;

    public ConnectingToServerScreen(UlandsTFBGame game, String host, int port)
    {
        super(game);
        this.host = host;
        this.port = port;
        init();
    }

    public void init()
    {
        client = game.getClient();

        Skin skin = UlandsTFBGame.getSkin();

        connectionListener = new Listener(){
            @Override
            public void connected(Connection connection)
            {
                ConnectingToServerScreen.this.connected(connection);
            }

            @Override
            public void disconnected(Connection connection)
            {
                ConnectingToServerScreen.this.disconnected(connection);
            }

            @Override
            public void received(Connection connection, Object o)
            {
                ConnectingToServerScreen.this.received(connection, o);
            }
        };

        status = new Label("Idle", skin, UlandsTFBGame.DEFAULT_FONT_STYLE);
        status.setAlignment(Align.center);
        status.setWrap(true);

        window = new DefaultWindow("Connecting...", skin, true, false){
            @Override
            protected void closeButton()
            {
                if(!closeButtonEnabled) return;

                final Button closeButton = new Button(this.getSkin(), DefaultWindow.CLOSE_BUTTON_STYLE);
                closeButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(game.mainMenuScreen); // TODO: parent system
                    }
                });
                getTitleTable()
                        .add(closeButton)
                        .size(32, 32)
                        .padRight(10)
                        .padTop(5);
            }
        };
        window.setWidth((float) (Gdx.graphics.getWidth() / 1.5));
        window.setHeight((float) (Gdx.graphics.getHeight() / 1.5));

        window.add(status);
        uiStage.addActor(window);

        resetGameScreen();
        runConnectionThread();
    }

    public void runConnectionThread()
    {
        statusLog("Connecting to " + host + ":" + port + "...");
        Gdx.app.postRunnable(() -> {
            try {
                client.removeListener(this.connectionListener);
                client.addListener(this.connectionListener);
                client.connect(host, port);
            } catch (IOException e) {
                statusError("Got exception: " + e.getLocalizedMessage());
            }
        });
    }

    public void resetGameScreen()
    {
        Gdx.app.postRunnable(() -> {
            try {
                if(game.gameScreen != null)
                    game.gameScreen.dispose();

                game.gameScreen = new GameScreen(game);
            } catch (Exception ignored) { }
        });
    }

    protected void connected(Connection connection)
    {
        statusLog("Connected, authorizing...");

        // TODO: client send username & password
        client.authorize("Player #" + (int)(Math.random() * 100), "no_password");
    }

    protected void disconnected(Connection connection)
    {
        statusError("Disconnected from server.");
    }

    protected void received(Connection connection, Object o)
    {
        if(o instanceof Network.AuthorizeStatus){
            Network.AuthorizeStatus authorizeStatus = (Network.AuthorizeStatus)o;

            if(authorizeStatus.authorized)
            {
                statusLog("Switching to game screen...");

                Gdx.app.postRunnable(() -> {
                    try {
                        // TODO: ask for assets
                        // TODO: wait until assets loaded
                        game.setScreen(game.gameScreen);
                    } catch (Exception ignored) { }
                });
            } else {
                statusError("Server said error on authorization!");
            }

        }
    }


    /* Status */
    protected void statusError(String message)
    {
        status.setColor(Color.RED);
        status.setText(message);
    }

    protected void statusLog(String message)
    {
        status.setColor(Color.YELLOW);
        status.setText(message);
    }

    /* Overrides */

    @Override
    public void dispose()
    {
        client.removeListener(this.connectionListener);
        super.dispose();
    }
}
