package spet.sbwo.control.scheduler;

import java.util.List;

public interface IScheduleManager {

	void start();

	List<ScheduleChannel> channels();
}
