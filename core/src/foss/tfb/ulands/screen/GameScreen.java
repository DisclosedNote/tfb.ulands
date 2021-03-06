package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import foss.tfb.ulands.UlandsTFBGame;
import foss.tfb.ulands.net.Network;
import foss.tfb.ulands.net.client.GameClient;
import foss.tfb.ulands.ui.ChatLogger;
import foss.tfb.ulands.ui.window.ConsoleWindow;
import foss.tfb.ulands.ui.window.DefaultWindow;

import java.util.ArrayList;

public class GameScreen extends DefaultScreen
{
    static float cameraMinWidth = 200,
            cameraMinHeight = 210,
            cameraMaxWidth = 28,
            cameraZoom = 0.4f,
            cameraZoomSpeed = 0.5f;

    protected Listener eventsListener;

    protected Stage gameStage;
    protected OrthographicCamera gameCamera;
    protected ExtendViewport gameViewport;
    protected SpriteBatch batch;

    protected ConsoleWindow chatWindow;
    protected DefaultWindow menuWindow;
    protected ChatLogger chatLogger;
    protected TextField messageField;
    //implements InputProcessor

    protected GameClient client;

    public GameScreen(UlandsTFBGame game)
    {
        super(game);

        client = game.getClient();

        batch = new SpriteBatch();
        gameCamera = new OrthographicCamera();
//        gameCamera.rotate(-3);
        gameViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera);
        gameStage = new Stage(gameViewport);

        Skin skin = UlandsTFBGame.getSkin();

        // test
        DefaultWindow window = new DefaultWindow("Graphics: "+Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight(), skin, false, false);
        window.setBounds(0, 0, 500, 200);
        gameStage.addActor(window);

        initializeUI();



        eventsListener = new Listener(){
            @Override
            public void connected(Connection connection)
            {
                GameScreen.this.connected(connection);
            }

            @Override
            public void disconnected(Connection connection)
            {
                GameScreen.this.disconnected(connection);
            }

            @Override
            public void received(Connection connection, Object o)
            {
                GameScreen.this.received(connection, o);
            }

            @Override
            public void idle(Connection connection)
            {
                GameScreen.this.idle(connection);
            }
        };

        client.addListener(eventsListener);
    }

    public void initializeUI()
    {
        Skin skin = UlandsTFBGame.getSkin();

        /* Chat window */

        chatWindow = new ConsoleWindow("Chat", skin, false, false);
        chatWindow.setClickListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                GameScreen.this.onMessageSent(event, x, y);
            }
        });
        uiStage.addActor(chatWindow);

        chatLogger = chatWindow.getChatLogger();
        messageField = chatWindow.getMessageField();

        /* Menu window */

        menuWindow = new DefaultWindow("Menu",
                skin, false, false);
        menuWindow.setBounds(100, 100, 275, 495);

        Table table = new Table(skin);

        Label title = new Label("Title Screen", skin, UlandsTFBGame.DEFAULT_FONT_STYLE);
        title.setAlignment(Align.center);

        ArrayList<MainMenuAction> actions = new ArrayList<>();

        actions.add(new MainMenuAction("Back to main menu", "Disconnect from server and open main menu"){
            @Override
            public void doAction()
            {
                client.disconnect();
                game.setScreen(game.mainMenuScreen);
            }
        });

        table.row().colspan(3).expandX().fillX();
        table.add(title).fillX();
        for(final MainMenuAction action : actions)
        {
            ImageTextButton actionButton = new ImageTextButton(action.title, skin);
            actionButton.setWidth(160);
            actionButton.setHeight(60);
            actionButton.addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    action.doAction();
                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            });
            table.row().colspan(3).expandX().fillX();
            table.add(actionButton).height(actionButton.getHeight()).width(actionButton.getWidth()).fillX();
        }

        final ScrollPane menuScroller = new ScrollPane(table, skin);


        menuWindow.add(menuScroller);
        uiStage.addActor(menuWindow);

    }


    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0.3f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.act();
        gameStage.draw();

        uiStage.act();
        uiStage.draw();
    }

   @Override
    public void resize(int width, int height)
    {
        gameViewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        uiStage.getViewport().update(width, height, true);
        chatLogger.add("Graphics: " + Gdx.graphics.getWidth() + " x " + Gdx.graphics.getHeight());
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {
        client.removeListener(eventsListener);
        super.dispose();
    }

    /* UI */

    protected boolean onMessageSent(InputEvent event, float x, float y)
    {
        client.getChatManager().send(messageField.getText());
        messageField.setText("");
        return true;
    }

    /* Network */

    public void connected(Connection c)
    {

    }

    public void disconnected(Connection c)
    {

    }

    public void received(Connection c, Object o)
    {
        if(o instanceof Network.ChatMessagePackage)
        {
            Network.ChatMessagePackage message = (Network.ChatMessagePackage)o;
            chatLogger.add(message.text);
            System.out.println(message.text);
            return;
        }
    }

    public void idle(Connection c)
    {

    }

    abstract static class MainMenuAction
    {
        protected String title;
        protected String tooltip;

        public MainMenuAction(String title, String tooltip)
        {
            this.title = title;
            this.tooltip = tooltip;
        }

        abstract public void doAction();

        public String getTitle()
        {
            return title;
        }

        public String getTooltip()
        {
            return tooltip;
        }
    }

}
