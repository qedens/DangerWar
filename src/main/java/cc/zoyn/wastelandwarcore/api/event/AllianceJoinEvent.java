package cc.zoyn.wastelandwarcore.api.event;

import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.module.common.user.User;

public class AllianceJoinEvent extends AllianceEvent {
    public AllianceJoinEvent(Alliance alliance, User ally) {
        super(alliance, ally);
    }
}
