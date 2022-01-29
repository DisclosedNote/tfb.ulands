package foss.tfb.ulands.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Assets
{
    protected final AssetManager manager = new AssetManager();
    protected final ArrayList<AssetDescriptor<Texture>> textures = new ArrayList<>();
    protected final ArrayList<AssetDescriptor<Sound>> sounds = new ArrayList<>();
    protected final ArrayList<AssetDescriptor<Music>> music = new ArrayList<>();

    public Assets()
    {

    }

    // TODO

    protected void queue(AssetDescriptor descriptor)
    {
        manager.load(descriptor);
    }

    public void queueTexture(AssetDescriptor<Texture> descriptor)
    {
        this.textures.add(descriptor);
        this.queue(descriptor);
    }

    public void queueSound(AssetDescriptor<Sound> descriptor)
    {
        this.sounds.add(descriptor);
        this.queue(descriptor);
    }

    public void queueMusic(AssetDescriptor<Music> descriptor)
    {
        this.music.add(descriptor);
        this.queue(descriptor);
    }

    public synchronized boolean update()
    {
        return this.manager.update();
    }

    public boolean update(int millis)
    {
        return this.manager.update(millis);
    }

}
