package foss.tfb.ulands.ui.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pools;
import foss.tfb.ulands.ui.ChatLogger;

import java.util.ArrayList;

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
    protected ClickListener eventListener;

    protected void init()
    {
        Skin skin = getSkin();

        setSize(650, 250);

        sendButton = new ImageTextButton("Send", skin);
        sendButton.setSize(80, 40);

        messageField = new TextField("", skin);
        messageField.setTextFieldListener((textField, key) -> {
            ClickListener el = getEventListener();
            if ((key == '\r' || key == '\n') && el != null){
                el.clicked(Pools.obtain(InputEvent.class), 0, 0);
            }
        });
        chatLogger = new ChatLogger(skin);

        row().colspan(2);
        add(chatLogger).prefWidth(Float.MAX_VALUE).prefHeight(Float.MAX_VALUE);
        row();
        add(messageField).prefWidth(Float.MAX_VALUE);
        add(sendButton).width(sendButton.getWidth()).height(sendButton.getHeight());

        setMinWidth(300);
    }

    @Override
    protected ArrayList<WindowAction> subMenuActions() {
        ArrayList<WindowAction> actions = super.subMenuActions();

        actions.add(new WindowAction("Clear contents")
        {
            @Override
            public void doAction()
            {
                chatLogger.getContents().clear();
            }
        });

        return actions;
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

    public ClickListener getEventListener()
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
