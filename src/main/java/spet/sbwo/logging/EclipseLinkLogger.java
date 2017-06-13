package spet.sbwo.logging;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EclipseLinkLogger extends AbstractSessionLog {

    @Override
    public void log(SessionLogEntry entry) {
        Logger logger = LoggerFactory.getLogger(EclipseLinkLogger.class);
        switch (entry.getLevel()) {
            case SessionLog.SEVERE:
                logger.error(entry.getMessage());
                break;
            case SessionLog.WARNING:
                logger.warn(entry.getMessage());
                break;
            case SessionLog.INFO:
                logger.info(entry.getMessage());
                break;
            default:
                logger.debug(entry.getMessage());
                break;
        }
    }

}
