package foss.tfb.ulands.ui.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import foss.tfb.ulands.UlandsTFBGame;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DefaultWindow extends Window {

    public static final String CLOSE_BUTTON_STYLE = "close-button";
    public static final String ENLARGE_BUTTON_STYLE = "enlarge-button";
    public static final String SUBMENU_BUTTON_STYLE = "actions-list-style";

    protected boolean closeButtonEnabled = true;
    protected boolean enlargeButtonEnabled = true;

    public boolean intendedToBeEnlarged = false;
    public float lastW, lastH = 0;
    public float lastX, lastY = 0;

    public DefaultWindow(String title, Skin skin, boolean closeButtonEnabled, boolean enlargeButtonEnabled) {
        super(title, skin);
        init(closeButtonEnabled, enlargeButtonEnabled);
    }

    public DefaultWindow(String title, Skin skin, String styleName, boolean closeButtonEnabled, boolean enlargeButtonEnabled) {
        super(title, skin, styleName);
        init(closeButtonEnabled, enlargeButtonEnabled);
    }

    public DefaultWindow(String title, WindowStyle style, boolean closeButtonEnabled, boolean enlargeButtonEnabled) {
        super(title, style);
        init(closeButtonEnabled, enlargeButtonEnabled);
    }

    BitmapFont sizeFont;
    int sizeInfoOffset = 25;
    protected ShapeRenderer shapeRenderer;

    protected void init(boolean closeButtonEnabled, boolean enlargeButtonEnabled){

        this.closeButtonEnabled = closeButtonEnabled;
        this.enlargeButtonEnabled = enlargeButtonEnabled;

        this.setResizeBorder(30);

        this.setMovable(true);
        this.setResizable(true);
        this.pad(22);
        this.padTop(48);
        this.setRotation(0);

        this.subMenuButton();
        this.enlargeButton();
        this.closeButton();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        sizeFont = getSkin().getFont(UlandsTFBGame.SMALL_FONT_NAME);
    }

    protected void closeButton()
    {
        if(!closeButtonEnabled) return;

        final Button closeButton = new Button(this.getSkin(), DefaultWindow.CLOSE_BUTTON_STYLE);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
            }
        });
        getTitleTable()
                .add(closeButton)
                .size(32, 32)
                .padRight(10)
                .padTop(5);
    }




    protected void enlargeButton()
    {
        if(!enlargeButtonEnabled) return;

        final Button enlargeButton = new Button(this.getSkin(), DefaultWindow.ENLARGE_BUTTON_STYLE);

        // TODO: enlarge button animation
        enlargeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Stage stage = getStage();

                if(!intendedToBeEnlarged){
                    lastW = getWidth();
                    lastH = getHeight();
                    lastX = getX();
                    lastY = getY();
                    setBounds(0, 0, stage.getWidth(),stage.getHeight());
                    intendedToBeEnlarged = !intendedToBeEnlarged;
                } else {
                    setBounds(lastX, lastY, lastW, lastH);
                    // intendedToBeEnlarged will already be false as size would change
                }

            }
        });

        getTitleTable()
                .add(enlargeButton)
                .size(32, 32)
                .padRight(10)
                .padTop(5);
    }

    protected void subMenuButton()
    {
        if(!enlargeButtonEnabled) return;

        // TODO: make dedicated ActionsListBox class
        final SelectBox<String> subMenuButton = new SelectBox<String>(this.getSkin(), DefaultWindow.SUBMENU_BUTTON_STYLE){
            @Override
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
        };

        final ArrayList<WindowAction> subMenuActions = subMenuActions();

        subMenuButton.getList().addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                // TODO: fix input lag
                int index = subMenuButton.getSelectedIndex();
                if(index < 0) return;
                subMenuActions.get(index).doAction();
            }
        });

        // TODO: optimization?
        Array<String> items = new Array<>();

        for(String title : subMenuActions.stream().map(item -> item.title).collect(Collectors.toList()))
            items.add(title);

        subMenuButton.setItems(items);


        getTitleTable()
                .add(subMenuButton)
                .size(32, 32)
                .padRight(10)
                .padTop(5);
    }

    protected ArrayList<WindowAction> subMenuActions()
    {
        ArrayList<WindowAction> actions = new ArrayList<>();
        final DefaultWindow w = DefaultWindow.this;

        actions.add(new WindowAction("Rotate r90d")
        {
            @Override
            public void doAction()
            {
                float r = w.getRotation();
                w.setOrigin(Align.center);
                w.setRotation(r - 90);
            }
        });

        actions.add(new WindowAction("Rotate l90d")
        {
            @Override
            public void doAction()
            {
                float r = w.getRotation();
                w.setOrigin(Align.center);
                w.setRotation(r + 90);
            }
        });

        return actions;
    }

    @Override
    protected void sizeChanged()
    {
        super.sizeChanged();
        intendedToBeEnlarged = false;
    }

    public boolean isCloseButtonEnabled()
    {
        return closeButtonEnabled;
    }

    public boolean isEnlargeButtonEnabled()
    {
        return enlargeButtonEnabled;
    }
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);

        sizeFont.draw(batch, (int)getWidth() + " x " + (int)getHeight(), getX() + sizeInfoOffset, getY() + sizeFont.getLineHeight() + sizeInfoOffset);
    }
}
