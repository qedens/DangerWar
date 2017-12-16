package cc.zoyn.wastelandwarcore.module.common.chat;

import cc.zoyn.wastelandwarcore.module.common.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 频道
 *
 * @author Zoyn
 * @since 2017-12-16
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Channel {

    private String name;
    private List<User> userList;

}
