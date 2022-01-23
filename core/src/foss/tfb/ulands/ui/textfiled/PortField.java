package foss.tfb.ulands.ui.textfiled;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class PortField extends TextField
{
    public PortField(String text, Skin skin)
    {
        super(text, skin);
        init();
    }

    public PortField(String text, Skin skin, String styleName)
    {
        super(text, skin, styleName);
        init();
    }

    public PortField(String text, TextFieldStyle style)
    {
        super(text, style);
        init();
    }

    protected void init()
    {
        // TODO: fix on paste checkup
        this.setTextFieldFilter((textField, c) -> {
            int asInt = 0;
            try {
                asInt = Integer.parseInt(getText() + c);
            } catch(NumberFormatException ignored){ }

            return (c >= 48 && c <= 57) && (asInt >= 0 && asInt <= 65535);
        });
    }



}
