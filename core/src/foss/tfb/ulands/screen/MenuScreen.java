package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import foss.tfb.ulands.UlandsTFBGame;

abstract public class MenuScreen extends DefaultScreen
{
    public MenuScreen(UlandsTFBGame game) {
        super(game);
    }

    @Override
    public void resize(int width, int height)
    {
        // Do not center the camera to keep sharp render
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }
}
