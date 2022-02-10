package foss.tfb.ulands.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatLogger extends ScrollPane
{

    protected ArrayList<Table> messages;
    protected int maximum = 250;

    protected Table contents;
    protected Skin skin;

    protected boolean skrollAfterAdd = true;

    public ChatLogger(Skin skin)
    {
        super(null, skin);
        init(skin);
    }

    public void init(Skin skin)
    {
        this.skin = skin;

        messages = new ArrayList<>();

        contents = new Table();
        contents.align(Align.top | Align.left);

        setFadeScrollBars(false);
        setActor(contents);
    }

    public void pop()
    {
        // TODO
    }

    public void add(String message)
    {
        Table instance = new Table(skin);
        instance.align(Align.left);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Label dateLabel = new Label("[" + dateFormat.format(date) + "]", skin);
        dateLabel.setColor(Color.YELLOW);

        Label label = new Label(message, skin);

        instance.add(dateLabel).padRight(10);
        instance.add(label);

        contents.row();
        contents.add(instance).expandX().fill().row();

        scrollToBottom();
    }

    protected void scrollToBottom()
    {
        if(skrollAfterAdd && getScrollPercentY() * 100 > 90)
            Gdx.app.postRunnable(() -> setScrollPercentY(1f));
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

    public Table getContents()
    {
        return contents;
    }

    public boolean isSkrollAfterAdd()
    {
        return skrollAfterAdd;
    }

    public void setSkrollAfterAdd(boolean skrollAfterAdd)
    {
        this.skrollAfterAdd = skrollAfterAdd;
    }
}
