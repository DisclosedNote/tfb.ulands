package foss.tfb.ulands.ui.window;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import foss.tfb.ulands.ui.ChatLogger;

public class ConsoleWindow extends DefaultWindow
{
    public ConsoleWindow(String title, Skin skin, boolean closeButtonEnabled, boolean enlargeButtonEnabled)
    {
        super(title, skin, closeButtonEnabled, enlargeButtonEnabled);
        init();
    }

    public ConsoleWindow(String title, Skin skin, String styleName, boolean closeButtonEnabled, boolean enlargeButtonEnabled)
    {
        super(title, skin, styleName, closeButtonEnabled, enlargeButtonEnabled);
        init();
    }

    public ConsoleWindow(String title, WindowStyle style, boolean closeButtonEnabled, boolean enlargeButtonEnabled)
    {
        super(title, style, closeButtonEnabled, enlargeButtonEnabled);
        init();
    }

    protected ChatLogger chatLogger;
    protected ImageTextButton sendButton;
    protected TextField messageField;
    protected Table table;
    protected EventListener eventListener;

    protected void init()
    {
        Skin skin = getSkin();

        setSize(650, 250);

        sendButton = new ImageTextButton("Send", skin);
        sendButton.setSize(80, 40);

        messageField = new TextField("", skin);
        messageField.setTextFieldListener((textField, key) -> {
            EventListener el = getEventListener();
            if ((key == '\r' || key == '\n') && el != null){
                //sendButton.fire(new InputEvent());
                // TODO: call sendButton onClick event
            }
        });
        chatLogger = new ChatLogger(skin);

        row().colspan(2);
        add(chatLogger).prefWidth(Float.MAX_VALUE).prefHeight(Float.MAX_VALUE);
        row();
        add(messageField).minWidth(300).prefWidth(Float.MAX_VALUE);
        add(sendButton).width(sendButton.getWidth()).height(sendButton.getHeight());
    }

    public ChatLogger getChatLogger()
    {
        return chatLogger;
    }

    public ImageTextButton getSendButton()
    {
        return sendButton;
    }

    public TextField getMessageField()
    {
        return messageField;
    }

    public Table getTable()
    {
        return table;
    }

    public EventListener getEventListener()
    {
        return eventListener;
    }

    public void setClickListener(ClickListener eventListener)
    {
        if(this.eventListener != null)
            sendButton.removeListener(this.eventListener);

        this.eventListener = eventListener;

        sendButton.addListener(eventListener);
    }
}
