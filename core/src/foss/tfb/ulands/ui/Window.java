package foss.tfb.ulands.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Window extends com.badlogic.gdx.scenes.scene2d.ui.Window {

    public Window(String title, Skin skin) {
        super(title, skin);
        init();
    }

    public Window(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
        init();
    }

    public Window(String title, WindowStyle style) {
        super(title, style);
        init();
    }

    ShapeRenderer shapeRenderer;
    private void init(){
        this.setResizeBorder(30);

        this.setMovable(true);
        this.setResizable(true);
        this.pad(22);
        this.padTop(48);
        this.setRotation(0);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }



    int test = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        shapeRenderer.begin();
//        shapeRenderer.rect(this.getX() + padLeft, this.getY() + padRight, width - padLeft - padRight, height - padTop - padBottom);
//        shapeRenderer.rect(this.getX(), this.getY(), width, height);
//        shapeRenderer.
//        shapeRenderer.end();
    }
}
