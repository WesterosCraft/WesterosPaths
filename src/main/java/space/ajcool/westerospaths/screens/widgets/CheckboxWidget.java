package space.ajcool.westerospaths.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import space.ajcool.westerospaths.core.Client;

import java.util.function.Consumer;

public class CheckboxWidget extends PressableWidget
{
    private static final Identifier CHECKBOX = Identifier.ofVanilla("widget/checkbox");
    private static final Identifier CHECKBOX_HIGHLIGHTED = Identifier.ofVanilla("widget/checkbox_highlighted");
    private static final Identifier CHECKBOX_SELECTED = Identifier.ofVanilla("widget/checkbox_selected");
    private static final Identifier CHECKBOX_SELECTED_HIGHLIGHTED = Identifier.ofVanilla("widget/checkbox_selected_highlighted");
    private final Text text;
    private boolean checked;
    private boolean enabled;
    private Consumer<Boolean> onChange;

    public CheckboxWidget(int x, int y, int width, int height, Text text, boolean checked, boolean enabled, Consumer<Boolean> onChange)
    {
        super(x, y, width, height, null);
        this.text = text;
        this.checked = checked;
        this.onChange = onChange;
        this.enabled = enabled;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta)
    {
        int x = this.getX();
        int y = this.getY();
        TextRenderer textRenderer = Client.mc().textRenderer;

        if (!enabled)
        {
            MatrixStack matrices = context.getMatrices();
            matrices.push();
            matrices.translate(0, 0, 2);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.7f);
            context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0xFF48494A);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            matrices.pop();

            int textX = x - width - textRenderer.getWidth(text) + 10;
            int textY = y + (height - textRenderer.fontHeight) / 2;
            context.drawTextWithShadow(textRenderer, text, textX, textY, 0xFF48494A);

            return;
        }

        Identifier texture;
        if (checked)
        {
            texture = this.isHovered() ? CHECKBOX_SELECTED_HIGHLIGHTED : CHECKBOX_SELECTED;
        }
        else
        {
            texture = this.isHovered() ? CHECKBOX_HIGHLIGHTED : CHECKBOX;
        }
        context.drawGuiTexture(texture, x, y, width, height);


        int textX = x - width - textRenderer.getWidth(text) + 10;
        int textY = y + (height - textRenderer.fontHeight) / 2;
        context.drawTextWithShadow(textRenderer, text, textX, textY, 0xFFFFFF);
    }

    @Override
    public void onPress()
    {
        checked = !checked;
        if (onChange != null)
        {
            onChange.accept(checked);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder)
    {
        this.appendDefaultNarrations(builder);
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
        if (onChange != null)
        {
            onChange.accept(checked);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setOnChange(Consumer<Boolean> onChange)
    {
        this.onChange = onChange;
    }
}
