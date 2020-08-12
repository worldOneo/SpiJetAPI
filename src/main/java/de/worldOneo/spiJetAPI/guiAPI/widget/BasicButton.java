package de.worldOneo.spiJetAPI.guiAPI.widget;

import de.worldOneo.spiJetAPI.guiAPI.widgets.AbstractButton;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.inventory.InventoryClickEvent;
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
     * @param inventoryClickEventConsumer the function which is called when the Button is clicked
     */
    public BasicButton(Consumer<InventoryClickEvent> inventoryClickEventConsumer) {
        super(inventoryClickEventConsumer);
    }

    @Override
    public ItemStack render() {
        return getDisplayItem();
    }
}
