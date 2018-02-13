package cc.zoyn.wastelandwarcore.manager;

import java.util.List;

/**
 * 将一个管理器设计成可保存的管理器
 *
 * @author Zoyn
 * @since 2017-12-23
 */
public interface SavableManager<T> {

    void saveElement(T t);

    default void saveElements(List<T> elements) {
        elements.forEach(this::saveElement);
    }

}
