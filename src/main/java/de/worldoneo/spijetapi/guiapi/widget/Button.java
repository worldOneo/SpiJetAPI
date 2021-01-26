package de.worldoneo.spijetapi.guiapi.widget;

import de.worldoneo.spijetapi.guiapi.widgets.AbstractButton;
import de.worldoneo.spijetapi.utils.ItemStackBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Accessors(chain = true)
@Getter
@Setter
public class Button extends AbstractButton {
    /**
     * The {@link Material} to use as button material
     *
     * @param material Set the {@link Material} to use for the button
     * @return the {@link Material} to used for the button
     */
    private Material material = Material.STONE;

    /**
     * The text to use as button title
     *
     * @param title set the name of the button
     * @return get the name of the button
     */
    private String title = "Button by GUIAPI";

    /**
     * @param amount the amount of items to render
     * @return the amount of items to render
     */
    private int amount = 1;

    /**
     * Create a simple GUI-Button
     *
     * @param inventoryClickEventConsumer the function which is called when the Button is clicked
     */
    public Button(Consumer<InventoryClickEvent> inventoryClickEventConsumer) {
        super(inventoryClickEventConsumer);
    }

    /**
     * Renders the button to an ItemStack
     *
     * @return returns the rendered ItemStack
     */
    @Override
    public ItemStack render() {
        return new ItemStackBuilder(material, amount, title).build();
    }
}
