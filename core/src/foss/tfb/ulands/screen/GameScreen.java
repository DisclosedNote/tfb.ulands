package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import foss.tfb.ulands.UlandsTFBGame;

public class GameScreen extends DefaultScreen
{
    static float cameraMinWidth = 16, cameraMaxWidth = 28, cameraHeight = 16, cameraZoom = 0.4f, cameraZoomSpeed = 0.5f;

    public GameScreen(UlandsTFBGame game)
    {
        super(game);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
//        viewport = new ExtendViewport(cameraMinWidth, cameraHeight, cameraMaxWidth, cameraHeight, camera);
    }

    OrthographicCamera camera;
    SpriteBatch batch;
//    ExtendViewport viewport;

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
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
