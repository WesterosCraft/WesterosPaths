package space.ajcool.westerospaths.screens.widgets;

import net.minecraft.text.Text;
import space.ajcool.westerospaths.core.Client;

public class TextWidget extends net.minecraft.client.gui.widget.TextWidget
{
    public TextWidget(int x, int y, int width, int height, Text message)
    {
        super(x, y, width, height, message, Client.mc().textRenderer);
    }

    public void setText(Text message)
    {
        this.setMessage(message);
    }
}
