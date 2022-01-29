package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import foss.tfb.ulands.UlandsTFBGame;

public class SettingsScreen extends MenuScreen
{
    ShapeRenderer shapeRenderer;
    public SettingsScreen(UlandsTFBGame game)
    {
        super(game);
        init();
    }
    public void init()
    {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
        draw();
    }
    public void draw()
    {
        this.uiStage.draw();
        shapeRenderer.begin();
        shapeRenderer.setColor(new Color(111,111,111,0.5f));
        shapeRenderer.rect(uiStage.getWidth()/2-100, uiStage.getHeight()/2-100,200,200);
        shapeRenderer.end();
    }
}
