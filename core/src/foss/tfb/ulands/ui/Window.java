package foss.tfb.ulands.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Window extends com.badlogic.gdx.scenes.scene2d.ui.Window {
    public Window(String title, Skin skin) {
        super(title, skin);
        init();
    }

    public Window(String title, Skin skin, String styleName) {
        super(title, skin, styleName);
        init();
    }

    public Window(String title, WindowStyle style) {
        super(title, style);
        init();
    }

    private void init(){
//        this.pad(30);
        this.setMovable(true);
        this.setResizable(true);
        this.pad(30);
        this.padTop(60);
//        this.set
    }



}
