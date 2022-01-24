package foss.tfb.ulands.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatLogger extends Table
{

    protected ArrayList<Table> messages;
    protected int maximum = 250;

    public ChatLogger(Skin skin)
    {
        super(skin);
        init();
    }

    public void init()
    {
        this.messages = new ArrayList<>();

    }

    public void pop()
    {
        // TODO
    }

    public void add(String message)
    {
        Table instance = new Table(this.getSkin());
        instance.align(Align.left);
//        instance.setDebug(true);
//        instance.

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Label dateLabel = new Label("[" + dateFormat.format(date) + "]", getSkin());
        dateLabel.setColor(Color.YELLOW);
//        dateLabel.setWrap(true);

        Label label = new Label(message, this.getSkin());
//        label.setWrap(true);
//        instance.defaults().width(100);
//        instance.add(dateLabel).width(dateLabel.getWidth()).uniform().padRight(10);
//        instance.add(label).uniform().fill();

        // FINALLY!!! got an answer of how to use table system
        instance.add(dateLabel).padRight(10);
        instance.add(label).uniform();

        this.row();
        this.add(instance).expand().fill().row();
    }

    public ArrayList<Table> getMessages()
    {
        return messages;
    }

    public void setMessages(ArrayList<Table> messages)
    {
        this.messages = messages;
    }

    /**
     *
     * @return maximum
     */
    public int getMaximum()
    {
        return maximum;
    }

    /**
     * negative = infinite
     * @param maximum max messages count
     */
    public void setMaximum(int maximum)
    {
        this.maximum = maximum;
    }
}
