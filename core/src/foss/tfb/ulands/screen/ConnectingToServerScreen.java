package foss.tfb.ulands.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import foss.tfb.ulands.UlandsTFBGame;
import foss.tfb.ulands.ui.window.DefaultWindow;

import java.io.IOException;

public class ConnectingToServerScreen extends MenuScreen
{
    protected int port;
    protected String host;
    protected Label status;
    protected DefaultWindow window;

    public ConnectingToServerScreen(UlandsTFBGame game, String host, int port)
    {
        super(game);
        this.host = host;
        this.port = port;
        init();
    }


    public void init()
    {
        Skin skin = UlandsTFBGame.getSkin();

        status = new Label("Idle", skin, UlandsTFBGame.DEFAULT_FONT);
        status.setAlignment(Align.center);
        status.setWrap(true);

        window = new DefaultWindow("Connecting...", skin, false, false){
            @Override
            protected void sizeChanged()
            {
                super.sizeChanged();
                ConnectingToServerScreen.this.status.setBounds(0,0,
                        this.getWidth() - this.getPadRight() - this.getPadLeft(),
                        this.getHeight() - this.getPadTop() - this.getPadBottom()
                );
            }
        };
        window.setWidth((float) (Gdx.graphics.getWidth() / 1.5));
        window.setHeight((float) (Gdx.graphics.getHeight() / 1.5));




        window.add(status);
        stage.addActor(window);

        runConnectionThread();
    }

    public void runConnectionThread()
    {
        Gdx.app.postRunnable(() -> {
            try {
                status.setColor(Color.YELLOW);
                status.setText("Connecting to " + host + ":" + port + "...");
                status.pack();
                game.getClient().connect(host, port);
            } catch (IOException e) {
                status.setColor(Color.RED);
                status.setText("Got exception: " + e.getLocalizedMessage());
                status.pack();
            }
        });
    }

}
