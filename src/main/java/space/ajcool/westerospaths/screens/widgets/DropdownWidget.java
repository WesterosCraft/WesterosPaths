package space.ajcool.westerospaths.screens.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import space.ajcool.westerospaths.core.Client;

import static net.minecraft.util.Identifier.ofVanilla;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DropdownWidget<T> extends ClickableWidget
{
    private static final Identifier BUTTON_TEXTURE = ofVanilla("widget/button");
    private static final Identifier BUTTON_HIGHLIGHTED_TEXTURE = ofVanilla("widget/button_highlighted");
    private static final Identifier BUTTON_DISABLED_TEXTURE = ofVanilla("widget/button_disabled");

    private final int originalWidth;
    private final int originalHeight;
    private List<T> options;
    private Function<T, Text> optionDisplay;
    private T selected;
    private Consumer<T> onSelect;
    private boolean allowNull;
    private boolean expanded;
    private int maxVisibleOptions;
    private int scrollOffset = 0;

    public DropdownWidget(
            int x,
            int y,
            int width,
            int height,
            Text title,
            List<T> options,
            Function<T, Text> optionDisplay,
            @Nullable T selected,
            Consumer<T> onSelect,
            boolean allowNull,
            boolean expanded,
            int maxVisibleOptions
    )
    {
        super(x, y, width, height, title);
        this.originalWidth = width;
        this.originalHeight = height;
        this.options = options;
        this.optionDisplay = optionDisplay;
        this.selected = selected;
        this.onSelect = onSelect;
        this.allowNull = allowNull;
        this.expanded = expanded;
        this.maxVisibleOptions = maxVisibleOptions;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta)
    {
        // First, draw the button itself.
        renderButton(context, mouseX, mouseY, delta);
        Text title = this.getMessage();
        if (title != null)
        {
            TextRenderer textRenderer = Client.mc().textRenderer;
            int titleY = getY() - (textRenderer.fontHeight / 2) - 8;
            context.drawTextWithShadow(textRenderer, title, getX(), titleY, 0xFFFFFF);
        }

        // If expanded, draw the dropdown list.
        if (expanded)
        {
            MatrixStack matrices = context.getMatrices();
            matrices.push();
            matrices.translate(0, 0, 100); // Raise the dropdown above other elements.

            int x = getX();
            int baseY = getY() + originalHeight;

            // Build a combined list of items. When allowNull is true we add a null entry
            // (which we later display as "None").
            List<T> allItems = new ArrayList<>();
            if (allowNull)
            {
                allItems.add(null);
            }
            allItems.addAll(options);

            int totalItems = allItems.size();
            int visibleCount = Math.min(totalItems, maxVisibleOptions);
            this.height = originalHeight + visibleCount * originalHeight;

            // Render each visible option, starting from scrollOffset.
            for (int i = 0; i < visibleCount; i++)
            {
                int actualIndex = scrollOffset + i;
                if (actualIndex >= totalItems) break;
                T item = allItems.get(actualIndex);
                int y = baseY + i * originalHeight;
                boolean hovered = mouseX >= x && mouseX <= x + originalWidth &&
                        mouseY >= y && mouseY <= y + originalHeight;
                boolean isSelected = (item == null && selected == null)
                        || (item != null && item.equals(selected));
                renderItem(context, x, y, item, isSelected, hovered);
            }
            matrices.pop();
        }
    }

    private void renderButton(DrawContext context, int mouseX, int mouseY, float delta)
    {
        TextRenderer textRenderer = Client.mc().textRenderer;
        int x = getX();
        int y = getY();

        boolean hovered = mouseX >= x && mouseX <= x + originalWidth &&
                mouseY >= y && mouseY <= y + originalHeight;
        Identifier texture = hovered ? BUTTON_HIGHLIGHTED_TEXTURE : BUTTON_TEXTURE;
        renderBox(context, x, y, selected, textRenderer, originalWidth, originalHeight, texture);

        String arrow = expanded ? "▲" : "▼";
        int arrowX = x + originalWidth - textRenderer.getWidth(arrow) - 4;
        int arrowY = y + (originalHeight - textRenderer.fontHeight) / 2;
        context.drawTextWithShadow(textRenderer, Text.literal(arrow), arrowX, arrowY, 0xFFFFFF);
    }

    /**
     * Renders an individual option in the dropdown.
     */
    private void renderItem(DrawContext context, int x, int y, T item, boolean selected, boolean hovered)
    {
        TextRenderer textRenderer = Client.mc().textRenderer;
        int width = getWidth();

        Identifier texture;
        if (hovered)
        {
            texture = BUTTON_HIGHLIGHTED_TEXTURE;
        }
        else if (selected)
        {
            texture = BUTTON_TEXTURE;
        }
        else
        {
            texture = BUTTON_DISABLED_TEXTURE;
        }
        renderBox(context, x, y, item, textRenderer, width, originalHeight, texture);
    }

    /**
     * Renders a box with text. If item is null, "None" is displayed.
     */
    private void renderBox(DrawContext context, int x, int y, T item, TextRenderer textRenderer,
                           int width, int height, Identifier texture)
    {
        context.drawGuiTexture(texture, x, y, width, height);

        Text display = (item == null) ? Text.literal("None") : optionDisplay.apply(item);
        int textX = x + 4;
        int textY = y + (height - textRenderer.fontHeight) / 2;
        context.drawTextWithShadow(textRenderer, display, textX, textY, 0xFFFFFF);
    }

    /**
     * Toggles expansion. When expanded, clicking inside the list selects an item;
     * clicking outside collapses the list.
     */
    @Override
    public void onClick(double mouseX, double mouseY)
    {
        if (!expanded)
        {
            expanded = true;
            scrollOffset = 0;
            List<T> allItems = new ArrayList<>();
            if (allowNull)
            {
                allItems.add(null);
            }
            allItems.addAll(options);
            int totalItems = allItems.size();
            int visibleCount = Math.min(totalItems, maxVisibleOptions);
            this.height = originalHeight + visibleCount * originalHeight;
        }
        else
        {
            int dropdownTop = getY() + originalHeight;
            List<T> allItems = new ArrayList<>();
            if (allowNull)
            {
                allItems.add(null);
            }
            allItems.addAll(options);
            int totalItems = allItems.size();
            int visibleCount = Math.min(totalItems, maxVisibleOptions);
            int dropdownBottom = dropdownTop + visibleCount * originalHeight;

            // If the click is outside the dropdown list, simply collapse
            if (mouseY < dropdownTop || mouseY > dropdownBottom)
            {
                expanded = false;
                this.height = originalHeight;
                return;
            }

            // Determine which visible item was clicked
            int clickedIndex = (int) ((mouseY - dropdownTop) / originalHeight);
            int actualIndex = scrollOffset + clickedIndex;
            if (actualIndex < totalItems)
            {
                T item = allItems.get(actualIndex);
                selected = item;
                if (onSelect != null)
                {
                    onSelect.accept(item);
                }
            }
            expanded = false;
            this.height = originalHeight;
        }
        super.onClick(mouseX, mouseY);
    }

    /**
     * When the dropdown is expanded and there are more items than can be shown,
     * the user can scroll using the mouse wheel.
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount)
    {
        if (expanded)
        {
            List<T> allItems = new ArrayList<>();
            if (allowNull)
            {
                allItems.add(null);
            }
            allItems.addAll(options);
            int totalItems = allItems.size();
            if (totalItems > maxVisibleOptions)
            {
                // Adjust scrollOffset
                scrollOffset -= (int) verticalAmount;
                scrollOffset = Math.max(0, scrollOffset);
                scrollOffset = Math.min(scrollOffset, totalItems - maxVisibleOptions);
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder)
    {
        appendDefaultNarrations(builder);
    }

    public List<T> getOptions()
    {
        return options;
    }

    public void setOptions(List<T> options)
    {
        this.options = options;
    }

    public void setOptionDisplay(Function<T, Text> optionDisplay)
    {
        this.optionDisplay = optionDisplay;
    }

    public @Nullable T getSelected()
    {
        return selected;
    }

    public void setSelected(@Nullable T selected)
    {
        if (!options.contains(selected))
        {
            this.selected = null;
        }
        else
        {
            this.selected = selected;
        }
    }

    public void setOnSelect(Consumer<T> onSelect)
    {
        this.onSelect = onSelect;
    }

    public void setAllowNull(boolean allowNull)
    {
        this.allowNull = allowNull;
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }

    public boolean isExpanded()
    {
        return expanded;
    }
}