package foss.tfb.ulands.screen;

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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta)
    {

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
