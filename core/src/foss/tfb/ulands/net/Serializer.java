package foss.tfb.ulands.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.ImmutableSerializer;
import foss.tfb.ulands.stage.Player;

public class Serializer
{
    public static class PlayerSerializer extends ImmutableSerializer<Player>
    {
        {
            setAcceptsNull(true);
        }

        @Override
        public void write(Kryo kryo, Output output, Player object)
        {
            // TODO
        }

        @Override
        public Player read(Kryo kryo, Input input, Class<? extends Player> type)
        {
            // TODO
            return null;
        }
    }
}
