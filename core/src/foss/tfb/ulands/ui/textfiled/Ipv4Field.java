package foss.tfb.ulands.ui.textfiled;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Ipv4Field extends TextField
{
    public Ipv4Field(String text, Skin skin)
    {
        super(text, skin);
        init();
    }

    public Ipv4Field(String text, Skin skin, String styleName)
    {
        super(text, skin, styleName);
        init();
    }

    public Ipv4Field(String text, TextFieldStyle style)
    {
        super(text, style);
        init();
    }

    protected void init()
    {
        // TODO: make more complicated
        this.setTextFieldFilter((textField, c) -> (c >= 48 && c <= 57) || c == 46);
    }

}
