package foss.tfb.ulands.stage;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;

public class Object
{
    public Vector2 position = new Vector2(0,0);
    public Vector2 size = new Vector2(1,1);
    public Quaternion rotation;

    public Object()
    {
        init();
    }

    // On create
    public void init()
    {

    }

    // Every frame
    public void render()
    {

    }
}
