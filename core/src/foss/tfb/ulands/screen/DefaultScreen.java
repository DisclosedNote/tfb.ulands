package foss.tfb.ulands.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import foss.tfb.ulands.UlandsTFBGame;

abstract public class DefaultScreen implements Screen {
    protected Stage stage = new Stage(new ScreenViewport());
    protected UlandsTFBGame game;

    public DefaultScreen(UlandsTFBGame game) {
        this.game = game;
    }

    public Stage getStage() {
        return stage;
    }
}
