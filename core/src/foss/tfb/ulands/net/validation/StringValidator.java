package foss.tfb.ulands.net.validation;

public class StringValidator extends Validator
{
    final public int USERNAME_LENGTH = 256;
    final public int MESSAGE_LENGTH = 2048;

    protected String contents;

    public StringValidator(String contents)
    {
        this.contents = contents;
    }

    public boolean validateUsername()
    {
        if(contents == null)
            return false;

        if(contents.length() > USERNAME_LENGTH || contents.length() <= 0) {
            return false;
        }

        String trim = contents.trim();
        if(trim.length() > USERNAME_LENGTH || trim.length() <= 0) {
            return false;
        }

        return true;
    }

    public boolean validateText()
    {
        return validateText(MESSAGE_LENGTH);
    }

    public boolean validateText(int max)
    {
        if(contents == null)
            return false;

        if(contents.length() > max || contents.length() <= 0) {
            return false;
        }

        String trim = contents.trim();
        if(trim.length() > max || trim.length() <= 0) {
            return false;
        }

        return true;
    }


}
