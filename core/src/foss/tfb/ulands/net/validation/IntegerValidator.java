package foss.tfb.ulands.net.validation;

public class IntegerValidator extends Validator<Integer>
{
    public IntegerValidator(Integer contents)
    {
        super(contents);
    }

    public IntegerValidator()
    {
        contents = 0;
    }

    public boolean validateRotation()
    {
        return (contents >= 0) && (contents <= 360);
    }

    public boolean validateSize()
    {
        return (contents >= 0);
    }

}
