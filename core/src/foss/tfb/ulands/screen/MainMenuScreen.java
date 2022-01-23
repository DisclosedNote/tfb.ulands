package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import foss.tfb.ulands.UlandsTFBGame;
import foss.tfb.ulands.ui.window.DefaultWindow;

import java.util.ArrayList;

public class MainMenuScreen extends MenuScreen
{
    protected DefaultWindow window;

    public MainMenuScreen(UlandsTFBGame game)
    {
        super(game);
        this.init();
    }

    public void init()
    {
        Skin skin = UlandsTFBGame.getSkin();
        window = new DefaultWindow("Title Screen", skin, false, true);

        window.setWidth((float) (Gdx.graphics.getWidth() / 1.5));
        window.setHeight((float) (Gdx.graphics.getHeight() / 1.5));

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
