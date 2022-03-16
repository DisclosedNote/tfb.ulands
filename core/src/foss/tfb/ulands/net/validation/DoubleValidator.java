package foss.tfb.ulands.net.validation;

public class DoubleValidator extends Validator<Double>
{
    public DoubleValidator(Double contents)
    {
        super(contents);
    }

    public DoubleValidator()
    {
        contents = 0d;
    }

    public boolean validatePosition()
    {
        // TODO: any checkups?
        return true;
    }

}
