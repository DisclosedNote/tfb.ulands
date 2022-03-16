package foss.tfb.ulands.stage;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import foss.tfb.ulands.net.validation.StaticValidator;

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
    public Long id;
    public Double x, y;
    public Integer width, height, rotation;

    public boolean validateFields(){
        boolean[] checkups = new boolean[]{
            StaticValidator.ofValue(id).validateID(),
            StaticValidator.ofValue(x).validatePosition(),
            StaticValidator.ofValue(y).validatePosition(),
            StaticValidator.ofValue(width).validateSize(),
            StaticValidator.ofValue(height).validateSize(),
            StaticValidator.ofValue(rotation).validateRotation(),
        };

        for(int i = 0; i < checkups.length; i++)
            if(!checkups[0])
                return false;

        return true;
    }


    /**
     * Server-side
     */

}
