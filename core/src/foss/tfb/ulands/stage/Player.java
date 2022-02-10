package foss.tfb.ulands.stage;

import foss.tfb.ulands.net.GameConnection;

public class Player extends Character
{
    protected GameConnection connection;
    protected String username = "Unauthorized Player";
    protected boolean authorized = false;

    public Player(GameConnection connection)
    {
        this.connection = connection;
    }

    public Player()
    {
        connection = new GameConnection();
        connection.setAssociatedPlayer(this);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public boolean isAuthorized()
    {
        return authorized;
    }

    public void setAuthorized(boolean authorized)
    {
        this.authorized = authorized;
    }

    public GameConnection getConnection()
    {
        return connection;
    }
}
