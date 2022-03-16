package foss.tfb.ulands.net.validation;

public class StaticValidator
{

    private static final IntegerValidator integerValidator  = new IntegerValidator();
    private static final DoubleValidator doubleValidator    = new DoubleValidator();
    private static final StringValidator stringValidator    = new StringValidator();
    private static final LongValidator longValidator        = new LongValidator();

    public static IntegerValidator ofValue(Integer v){
        integerValidator.contents = v;
        return integerValidator;
    }

    public static DoubleValidator ofValue(Double v){
        doubleValidator.contents = v;
        return doubleValidator;
    }

    public static StringValidator ofValue(String v){
        stringValidator.contents = v;
        return stringValidator;
    }

    public static LongValidator ofValue(Long v){
        longValidator.contents = v;
        return longValidator;
    }

}
