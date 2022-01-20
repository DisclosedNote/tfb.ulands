package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import foss.tfb.ulands.UlandsTFBGame;
import foss.tfb.ulands.ui.Window;

public class MainMenuScreen extends MenuScreen
{
    public MainMenuScreen(UlandsTFBGame game)
    {
        super(game);
        this.init();
    }

    Window window;

    public void init()
    {
        Skin skin = UlandsTFBGame.getSkin();
        window = new Window("YO!", skin);

        window.setWidth((float) (Gdx.graphics.getWidth() / 1.5));
        window.setHeight((float) (Gdx.graphics.getHeight() / 1.5));

        Table table = new Table(skin);

        Label title = new Label("Title Screen", skin, UlandsTFBGame.DEFAULT_FONT);
        title.setAlignment(Align.center);
        title.setWidth(Gdx.graphics.getWidth());

        ImageTextButton playButton = new ImageTextButton("Play!", skin);
        playButton.setWidth(100);
        playButton.setHeight(100);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                game.setScreen(new GameScreen(game));
//                System.out.println("yes!");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        table.row().colspan(3).expandX().fillX();
        table.add(title).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(playButton).height(playButton.getHeight()).width(playButton.getWidth()).fillX();

        window.add(table);
        stage.addActor(window);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose()
    {
        super.dispose();
        stage.dispose();
    }
}
