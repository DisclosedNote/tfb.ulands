package foss.tfb.ulands.net.validation;

public class Validator<T>
{
    protected T contents;

    public Validator(T contents)
    {
        this.contents = contents;
    }

    public Validator()
    {
    }

    public void setContents(T contents)
    {
        this.contents = contents;
    }
}
