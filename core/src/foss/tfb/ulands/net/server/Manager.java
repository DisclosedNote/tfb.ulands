package foss.tfb.ulands.net.server;

abstract public class Manager
{
    protected GameServer server;

    public Manager(GameServer server)
    {
        this.server = server;
    }
}
