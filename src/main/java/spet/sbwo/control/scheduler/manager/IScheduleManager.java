package spet.sbwo.control.scheduler.manager;

import spet.sbwo.control.scheduler.model.ScheduleChannel;

import java.util.List;

public interface IScheduleManager {

    void start();

    List<ScheduleChannel> channels();
}
