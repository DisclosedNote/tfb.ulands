package foss.tfb.ulands.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

abstract public class DefaultScreen implements Screen {
    public Stage stage = new Stage(new ScreenViewport());
}
