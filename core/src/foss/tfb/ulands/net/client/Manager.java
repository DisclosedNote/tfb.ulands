package foss.tfb.ulands.net.client;

abstract public class Manager
{
    protected GameClient client;

    public Manager(GameClient client)
    {
        this.client = client;
    }
}
