package space.ajcool.westerospaths.screens.widgets;

/**
 * An exception thrown when text validation fails.
 */
public class TextValidationError extends Exception
{
    public TextValidationError(String message)
    {
        super(message);
    }
}
