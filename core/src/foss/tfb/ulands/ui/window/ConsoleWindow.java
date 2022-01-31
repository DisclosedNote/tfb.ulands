package foss.tfb.ulands.ui.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

    protected void init()
    {
        Skin skin = getSkin();

        sendButton = new ImageTextButton("Send", skin);
        sendButton.setSize(80, 40);

        messageField = new TextField("", skin);

        chatLogger = new ChatLogger(skin);
        chatLogger.setSize(200, 200);
        final ScrollPane logsScroller = new ScrollPane(chatLogger, skin);
        logsScroller.setFadeScrollBars(false);
//        logsScroller.setFillParent(true);

        /*
        table = new Table(skin);

        table.setPosition(0,0);
        table.setDebug(true);
//        table.setFillParent(true);

        table.pad(0);

        table.row().colspan(2).expand().fill();
        table.add(logsScroller).height(100).uniform().expandX().fillX();
        table.row().expand().fill();
        table.add(messageField).height(messageField.getHeight()).expand().fill().uniform();
        table.add(sendButton).width(sendButton.getWidth()).height(50).expand().fill().uniform();
//        table.add(logsScroller).height(logsScroller.getHeight()).width(logsScroller.getWidth()).fillX();

        add(table);
         */

//        setDebug(true);
        row().colspan(2);
        add(logsScroller).prefWidth(Float.MAX_VALUE).prefHeight(Float.MAX_VALUE);
        row();
        add(messageField).prefWidth(Float.MAX_VALUE);
        add(sendButton).width(sendButton.getWidth()).height(sendButton.getHeight());
    }

    @Override
    protected void sizeChanged()
    {
        super.sizeChanged();
        Gdx.app.postRunnable(() -> {
//            table.setBounds(getPadLeft(),getPadBottom(), getWidth() - getPadLeft() - getPadRight(), getHeight() - getPadBottom() - getPadTop());
        });
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
}
