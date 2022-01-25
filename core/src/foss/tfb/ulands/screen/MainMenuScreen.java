package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import foss.tfb.ulands.UlandsTFBGame;
import foss.tfb.ulands.ui.textfiled.Ipv4Field;
import foss.tfb.ulands.ui.textfiled.PortField;
import foss.tfb.ulands.ui.window.DefaultWindow;

import java.util.ArrayList;

public class MainMenuScreen extends MenuScreen
{
    protected DefaultWindow window;
    protected DefaultWindow multiplayerWindow;

    public MainMenuScreen(UlandsTFBGame game)
    {
        super(game);
        this.init();
    }

    public void init()
    {
        Skin skin = UlandsTFBGame.getSkin();

        initMultiplayerWindow(skin);

        window = new DefaultWindow("Title Screen", skin, false, true);

        window.setWidth((float) (Gdx.graphics.getWidth()));
        window.setHeight((float) (Gdx.graphics.getHeight()));

        Table table = new Table(skin);

        Label title = new Label("Title Screen", skin, UlandsTFBGame.DEFAULT_FONT);
        title.setAlignment(Align.center);

        ArrayList<MainMenuAction> actions = new ArrayList<>();

        actions.add(new MainMenuAction("Singleplayer", "Play offline (localhost game)"){
            @Override
            public void doAction()
            {
                game.setScreen(new HostLocalGameScreen(game));
            }
        });

        actions.add(new MainMenuAction("Multiplayer", "Open server list, find available games online"){
            @Override
            public void doAction()
            {
                multiplayerWindow.setVisible(true);
                multiplayerWindow.toFront();
            }
        });

        actions.add(new MainMenuAction("Extensions", "Open available game extensions"){
            @Override
            public void doAction()
            {

            }
        });

        actions.add(new MainMenuAction("Settings", "Open preferences menu"){
            @Override
            public void doAction()
            {

            }
        });

        actions.add(new MainMenuAction("Exit", "Exit to desktop"){
            @Override
            public void doAction()
            {
                Gdx.app.exit();
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

        final ScrollPane scroller = new ScrollPane(table, skin);
        scroller.setFadeScrollBars(false);

        window.add(scroller);
        stage.addActor(window);
    }

    public void initMultiplayerWindow(Skin skin)
    {
        multiplayerWindow = new DefaultWindow("Multiplayer", skin, true, true);
        multiplayerWindow.setVisible(false);

        multiplayerWindow.setWidth((float) (Gdx.graphics.getWidth() / 1.5));
        multiplayerWindow.setHeight((float) (Gdx.graphics.getHeight() / 1.5));

        Table table = new Table(skin);

        final Ipv4Field ip = new Ipv4Field("127.0.0.1", skin);
        final PortField port = new PortField("19784", skin);

        ImageTextButton actionButton = new ImageTextButton("Join", skin);
        actionButton.setWidth(160);
        actionButton.setHeight(60);
        actionButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ConnectingToServerScreen(game, ip.getIP(), port.getPort()));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });




//        table.row().colspan(3).expandX().fillX();
        table.row().expandX().fillX();
        table.add(ip).height(ip.getHeight()).width(ip.getWidth()).fillX();
        table.row().expandX().fillX();
        table.add(port).height(port.getHeight()).width(port.getWidth()).fillX();
        table.row().expandX().fillX();
        table.add(actionButton).height(actionButton.getHeight()).width(actionButton.getWidth()).fillX();

        final ScrollPane scroller = new ScrollPane(table, skin);
        scroller.setFadeScrollBars(true);

        multiplayerWindow.add(scroller);
        stage.addActor(multiplayerWindow);
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        multiplayerWindow.setPosition(width / 2f, height / 2f, Align.center);
        window.setPosition(width / 2f, height / 2f, Align.center);
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
