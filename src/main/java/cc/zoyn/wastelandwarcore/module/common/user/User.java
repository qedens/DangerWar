package cc.zoyn.wastelandwarcore.module.common.user;

import cc.zoyn.wastelandwarcore.module.common.chat.Channel;
import cc.zoyn.wastelandwarcore.module.common.talent.Talent;
import cc.zoyn.wastelandwarcore.module.town.Town;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 表示一个用户
 *
 * @author Zoyn
 * @since 2017-12-16
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    /**
     * 用户名
     */
    private String name;
    /**
     * 用户所在频道
     */
    private Channel channel;
    /**
     * 用户所在城镇
     */
    private Town town;
    /**
     * 用户天赋
     */
    private Talent talent;

}
