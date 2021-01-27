package de.worldoneo.spijetapi.guiapi.widget;

import de.worldoneo.spijetapi.guiapi.gui.ClickContext;
import de.worldoneo.spijetapi.guiapi.widgets.AbstractButton;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Accessors(chain = true)
public class BasicButton extends AbstractButton {
    /**
     * The ItemStack which will be displayed as button.
     *
     * @param displayItem The ItemStack to use as the button.
     * @return the ItemStack used as the button.
     */
    @Getter
    @Setter
    private ItemStack displayItem;


    /**
     * Create a simple GUI-Button
     *
     * @param clickEventConsumer the function which is called when the Button is clicked
     */
    public BasicButton(Consumer<ClickContext> clickEventConsumer) {
        super(clickEventConsumer);
    }

    @Override
    public ItemStack render() {
        return getDisplayItem();
    }
}
