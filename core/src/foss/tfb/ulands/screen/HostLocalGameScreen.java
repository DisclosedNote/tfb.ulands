package foss.tfb.ulands.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import foss.tfb.ulands.UlandsTFBGame;
import foss.tfb.ulands.ui.textfiled.Ipv4Field;
import foss.tfb.ulands.ui.textfiled.PortField;
import foss.tfb.ulands.ui.window.DefaultWindow;

public class HostLocalGameScreen extends MenuScreen
{
    public HostLocalGameScreen(UlandsTFBGame game)
    {
        super(game);
        init();
    }

    protected DefaultWindow window;

    protected void init()
    {
        Skin skin = UlandsTFBGame.getSkin();
        window = new DefaultWindow("Host settings", skin, false, false);


        window.setWidth((float) (Gdx.graphics.getWidth() / 1.5));
        window.setHeight((float) (Gdx.graphics.getHeight() / 1.5));

        Table table = new Table(skin);

        ImageTextButton actionButton = new ImageTextButton("Host", skin);
        actionButton.setWidth(160);
        actionButton.setHeight(60);
        actionButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                host();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Ipv4Field ip = new Ipv4Field("127.0.0.1", skin);

        PortField port = new PortField("19784", skin);


        table.row().colspan(3).expandX().fillX();
        table.add(ip).height(ip.getHeight()).width(ip.getWidth()).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(port).height(port.getHeight()).width(port.getWidth()).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(actionButton).height(actionButton.getHeight()).width(actionButton.getWidth()).fillX();

        final ScrollPane scroller = new ScrollPane(table, skin);
        scroller.setFadeScrollBars(false);

        window.add(scroller);
        stage.addActor(window);

    }

    protected void host()
    {

    }

}
