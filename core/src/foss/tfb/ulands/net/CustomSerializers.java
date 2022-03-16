 package foss.tfb.ulands.net;

 import com.esotericsoftware.kryo.Kryo;
 import com.esotericsoftware.kryo.KryoException;
 import com.esotericsoftware.kryo.Serializer;
 import com.esotericsoftware.kryo.io.Input;
 import com.esotericsoftware.kryo.io.Output;
 import foss.tfb.ulands.stage.BaseObject;

public class CustomSerializers
{
    public static class BaseObjectSerializer extends Serializer<BaseObject>
    {
        {
            setAcceptsNull(true);
        }

        @Override
        public void write(Kryo kryo, Output output, BaseObject object)
        {
            try {
                output.writeLong(object.id, true);
                output.writeDouble(object.x);
                output.writeDouble(object.y);
                output.writeInt(object.width, true);
                output.writeInt(object.height, true);
                output.writeInt(object.rotation, true);
            } catch (KryoException e){
                e.printStackTrace();
            }
        }

        @Override
        public BaseObject read(Kryo kryo, Input input, Class<? extends BaseObject> type)
        {
            try {
                BaseObject result = new BaseObject();

                result.id           = input.readLong(true);
                result.x            = input.readDouble();
                result.y            = input.readDouble();
                result.width        = input.readInt(true);
                result.height       = input.readInt(true);
                result.rotation     = input.readInt(true);

                if(!result.validateFields())
                    throw new KryoException("Some fields are broken");

                return result;
            } catch (KryoException e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
