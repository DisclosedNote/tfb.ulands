package foss.tfb.ulands.net;

import com.esotericsoftware.kryonet.Connection;
import foss.tfb.ulands.stage.Player;

public class GameConnection extends Connection
{
    protected Player associatedPlayer;

    public Player getAssociatedPlayer()
    {
        return associatedPlayer;
    }

    public void setAssociatedPlayer(Player associatedPlayer)
    {
        this.associatedPlayer = associatedPlayer;
    }
}
