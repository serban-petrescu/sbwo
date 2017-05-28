package spet.sbwo.control.scheduler.manager;

import java.util.List;

import spet.sbwo.control.scheduler.model.ScheduleChannel;

public interface IScheduleManager {

    void start();

    List<ScheduleChannel> channels();
}
