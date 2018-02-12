package cc.zoyn.wastelandwarcore.exception;

import org.bukkit.block.Block;

/**
 * 当认错城镇核心方块时抛出.
 */
public class InvalidTownCoreException extends Throwable {
    private final Block block;
    public InvalidTownCoreException(Block block) {
        this.block = block;
    }

    /**
     * 返回方块
     * @return 方块
     */
    public Block getBlock() {
        return block;
    }
}
