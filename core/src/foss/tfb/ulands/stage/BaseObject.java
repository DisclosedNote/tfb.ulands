package foss.tfb.ulands.stage;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BaseObject
{
    /**
     * Client-side only
     */
    protected Sprite sprite; // TODO: sync x,y,width,height
    protected Animation still;

    /**
     * Shared-side
     */
    protected Long id;
    protected Integer x, y, width, height, rotation;


    /**
     * Server-side
     */

}
