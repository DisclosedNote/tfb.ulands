package foss.tfb.ulands.net.validation;

public class LongValidator extends Validator<Long>
{
    public LongValidator(Long contents)
    {
        super(contents);
    }

    public LongValidator()
    {
        contents = 0L;
    }

    public boolean validateID()
    {
        return contents >= 1;
    }

}
