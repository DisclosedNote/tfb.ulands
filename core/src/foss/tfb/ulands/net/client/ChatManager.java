package foss.tfb.ulands.net.client;

import foss.tfb.ulands.net.Network;

public class ChatManager extends Manager
{
    public ChatManager(GameClient client)
    {
        super(client);
    }

    public int send(String message)
    {
        Network.ChatMessage chatMessage = new Network.ChatMessage();
        chatMessage.text = message;
        return client.send(chatMessage);
    }


}
