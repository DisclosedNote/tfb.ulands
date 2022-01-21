package foss.tfb.ulands.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DefaultWindow extends Window {

    public static final String CLOSE_BUTTON_STYLE = "close-button";
    public static final String ENLARGE_BUTTON_STYLE = "enlarge-button";

    protected boolean closeButtonEnabled = true;
    protected boolean enlargeButtonEnabled = true;

    public DefaultWindow(String title, Skin skin) {
        super(title, skin);
        init();
    }

    public DefaultWindow(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
        init();
    }

    public DefaultWindow(String title, WindowStyle style) {
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

        this.enlargeButton();
        this.closeButton();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    public void closeButton()
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

    public void enlargeButton()
    {
        if(!enlargeButtonEnabled) return;

        final Button enlargeButton = new Button(this.getSkin(), DefaultWindow.ENLARGE_BUTTON_STYLE);
        // TODO: enlarge button animation
        enlargeButton.addListener(new ClickListener() {

            // TODO: move to DefaultWindow class
            private boolean intendedToBeEnlarged = false;
            private float lastW, lastH = 0;
            private float lastX, lastY = 0;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Stage stage = getStage();

                if(!intendedToBeEnlarged){
                    lastW = getWidth();
                    lastH = getHeight();
                    lastX = getX();
                    lastY = getY();
                    setBounds(0, 0, stage.getWidth(),stage.getHeight());
                } else {
                    setBounds(lastX, lastY, lastW, lastH);
                }

                intendedToBeEnlarged = !intendedToBeEnlarged;
            }
        });

        getTitleTable()
                .add(enlargeButton)
                .size(32, 32)
                .padRight(10)
                .padTop(5);
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

    public boolean isCloseButtonEnabled()
    {
        return closeButtonEnabled;
    }

    public void setCloseButtonEnabled(boolean closeButtonEnabled)
    {
        this.closeButtonEnabled = closeButtonEnabled;
    }

    public boolean isEnlargeButtonEnabled()
    {
        return enlargeButtonEnabled;
    }

    public void setEnlargeButtonEnabled(boolean enlargeButtonEnabled)
    {
        this.enlargeButtonEnabled = enlargeButtonEnabled;
    }
}
