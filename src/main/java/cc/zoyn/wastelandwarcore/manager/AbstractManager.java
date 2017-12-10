package cc.zoyn.wastelandwarcore.manager;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * 抽象管理器
 *
 * @author Zoyn
 * @since 2017-12-10
 */
@Data
public abstract class AbstractManager<T> {

    private List<T> list = Lists.newArrayList();

    public void addElement(T t) {
        list.add(Validate.notNull(t));
    }

    public void removeElement(T t) {
        Validate.notNull(t);

        if (list.contains(t)) {
            list.remove(t);
        }
    }

}
