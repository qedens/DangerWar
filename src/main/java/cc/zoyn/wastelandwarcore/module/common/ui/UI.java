package cc.zoyn.wastelandwarcore.module.common.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.Inventory;

/**
 * @author Zoyn
 * @since 2017-12-09
 */
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UI {

    private Inventory inventory;
    private UIHandler listener;

}
