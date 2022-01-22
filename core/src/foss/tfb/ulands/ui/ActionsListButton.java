package foss.tfb.ulands.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ActionsListButton<T> extends SelectBox<T>
{

    public ActionsListButton(Skin skin)
    {
        super(skin);
    }

    public ActionsListButton(Skin skin, String styleName)
    {
        super(skin, styleName);
    }

    public ActionsListButton(SelectBoxStyle style)
    {
        super(style);
    }

    SelectBoxStyle style;
    public void draw (Batch batch, float parentAlpha) {
        validate();

        Drawable background = getBackgroundDrawable();

        Color color = getColor();
        float x = getX(), y = getY();
        float width = getWidth(), height = getHeight();

        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        if (background != null)
            background.draw(batch, x, y, width, height);



    }

}
