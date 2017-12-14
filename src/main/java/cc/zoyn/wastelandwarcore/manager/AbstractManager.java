package cc.zoyn.wastelandwarcore.manager;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * 抽象管理器
 *
 * @author Zoyn
 * @since 2017-12-10
 */
public abstract class AbstractManager<T> {

    private List<T> list = Lists.newArrayList();

    /**
     * 往List中增加元素
     *
     * @param t 元素对象
     */
    public void addElement(T t) {
        list.add(Validate.notNull(t));
    }

    /**
     * 删除List中某个元素
     *
     * @param t 元素对象
     */
    public void removeElement(T t) {
        Validate.notNull(t);

        if (list.contains(t)) {
            list.remove(t);
        }
    }

    /**
     * 获取当前管理器的List
     *
     * @return {@link List}
     */
    public List<T> getList() {
        return list;
    }

}
