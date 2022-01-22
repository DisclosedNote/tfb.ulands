package foss.tfb.ulands.ui.window;

abstract public class WindowAction
{
    protected String title;

    public WindowAction(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    abstract public void doAction();
}
