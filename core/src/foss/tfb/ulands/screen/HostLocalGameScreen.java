package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import foss.tfb.ulands.UlandsTFBGame;
import foss.tfb.ulands.net.server.GameServer;
import foss.tfb.ulands.ui.ChatLogger;
import foss.tfb.ulands.ui.textfiled.Ipv4Field;
import foss.tfb.ulands.ui.textfiled.PortField;
import foss.tfb.ulands.ui.window.DefaultWindow;

public class HostLocalGameScreen extends MenuScreen
{
    public HostLocalGameScreen(UlandsTFBGame game)
    {
        super(game);
        init();
    }

    protected DefaultWindow window;
    protected DefaultWindow logsWindow;

    protected Ipv4Field ip;
    protected PortField port;
    protected ChatLogger chatLogger;

    protected ImageTextButton hostButton;

    protected Listener activityListener;

    protected void init()
    {

        activityListener = new Listener(){
            @Override
            public void connected(Connection connection)
            {
                chatLogger.add("Peer connected: " + connection.getRemoteAddressTCP().toString());
            }

            @Override
            public void disconnected(Connection connection)
            {
                chatLogger.add("Peer disconnected: " + connection.getRemoteAddressTCP().toString());
            }

            @Override
            public void received(Connection connection, Object o)
            {
                chatLogger.add("Data received from " + connection.getRemoteAddressTCP().toString());
            }

            @Override
            public void idle(Connection connection)
            {
//                chatLogger.add("Idle");
            }
        };

        Skin skin = UlandsTFBGame.getSkin();
        window = new DefaultWindow("Host settings", skin, false, false);


        window.setWidth((float) (Gdx.graphics.getWidth() / 1.5));
        window.setHeight((float) (Gdx.graphics.getHeight() / 1.5));

        Table table = new Table(skin);

        hostButton = new ImageTextButton("Host", skin);
        hostButton.setWidth(160);
        hostButton.setHeight(60);
        hostButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                host();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        ip = new Ipv4Field("127.0.0.1", skin);

        port = new PortField("19784", skin);




//        table.setDebug(true);
        table.row().colspan(3).expandX().fillX();
        table.add(ip).height(ip.getHeight()).width(ip.getWidth()).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(port).height(port.getHeight()).width(port.getWidth()).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(hostButton).height(hostButton.getHeight()).width(hostButton.getWidth()).fillX();
//        table.row().colspan(3).expandX().fillX();


        final ScrollPane scroller = new ScrollPane(table, skin);
        scroller.setFadeScrollBars(false);

        window.add(scroller);
        stage.addActor(window);


        /* Logs */

        logsWindow = new DefaultWindow("Logs", skin, false, false);
        logsWindow.setBounds(100, 100, 300, 300);

        chatLogger = new ChatLogger(skin);
        final ScrollPane logsScroller = new ScrollPane(chatLogger, skin);

        logsWindow.add(logsScroller);
        stage.addActor(logsWindow);


    }

    @Override
    public void dispose()
    {
        super.dispose();
        GameServer gameServer = this.game.getLocalGameServer();
        gameServer.stop();
        gameServer.removeListener(activityListener);
    }

    protected void host()
    {

        this.game.startLocalGameServer(ip.getText(), port.getPort());

        GameServer gameServer = this.game.getLocalGameServer();
        gameServer.addListener(activityListener);

        chatLogger.add("Hosting at " + ip.getText() + " (port " + port.getPort() + ")");

        hostButton.setDisabled(true);
    }

}
