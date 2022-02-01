package foss.tfb.ulands.net.server;

import foss.tfb.ulands.net.GameConnection;

abstract public class Manager
{
    protected GameServer server;

    public Manager(GameServer server)
    {
        this.server = server;
    }

    public void act(GameConnection connection, Object object) {}
}
