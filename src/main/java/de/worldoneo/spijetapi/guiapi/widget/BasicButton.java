package de.worldoneo.spijetapi.guiapi.widget;

import de.worldoneo.spijetapi.guiapi.widgets.AbstractButton;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Accessors(chain = true)
public class BasicButton<T extends Cancellable> extends AbstractButton<T> {
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
    public BasicButton(Consumer<T> clickEventConsumer) {
        super(clickEventConsumer);
    }

    @Override
    public ItemStack render() {
        return getDisplayItem();
    }
}
