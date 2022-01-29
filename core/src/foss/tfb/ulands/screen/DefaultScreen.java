package foss.tfb.ulands.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import foss.tfb.ulands.UlandsTFBGame;

abstract public class DefaultScreen implements Screen
{
    protected Stage uiStage = new Stage(new ScreenViewport());
    protected UlandsTFBGame game;

    public DefaultScreen(UlandsTFBGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        this.game.getInputMultiplexer().addProcessor(this.uiStage);
    }

    @Override
    public void hide()
    {
        this.game.getInputMultiplexer().removeProcessor(this.uiStage);
    }

    @Override
    public void dispose()
    {
        this.game.getInputMultiplexer().removeProcessor(this.uiStage);
        uiStage.dispose();
    }

    public Stage getUIStage() {
        return uiStage;
    }
}