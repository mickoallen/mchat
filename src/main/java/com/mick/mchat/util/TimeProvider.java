package com.mick.mchat.util;

import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Provider for time related things. Helps time related things get tested.s
 */
@Singleton
public class TimeProvider {
    public Timestamp getNowAsTimestamp(){
        return Timestamp.from(Instant.now());
    }
}
