package io.lumify.bigtable.model.systemNotification;

import com.altamiracorp.bigtable.model.FlushFlag;
import com.altamiracorp.bigtable.model.ModelSession;
import com.altamiracorp.bigtable.model.user.ModelUserContext;
import com.google.inject.Inject;
import io.lumify.bigtable.model.systemNotification.model.SystemNotificationRowKey;
import io.lumify.core.model.systemNotification.SystemNotification;
import io.lumify.core.model.systemNotification.SystemNotificationRepository;
import io.lumify.core.model.systemNotification.SystemNotificationSeverity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BigTableSystemNotificationRepository extends SystemNotificationRepository {
    private ModelUserContext modelUserContext;
    private io.lumify.bigtable.model.systemNotification.model.SystemNotificationRepository repository;

    @Inject
    public BigTableSystemNotificationRepository(ModelUserContext modelUserContext, ModelSession modelSession) {
        this.modelUserContext = modelUserContext;
        repository = new io.lumify.bigtable.model.systemNotification.model.SystemNotificationRepository(modelSession);
    }

    @Override
    public List<SystemNotification> getActiveNotifications() {
        Date now = new Date();
        List<SystemNotification> activeNotifications = new ArrayList<SystemNotification>();
        for (SystemNotification notification : repository.findAll(modelUserContext)) {
            if (notification.getStartDate().after(now)) {
                if (notification.getEndDate() == null || notification.getEndDate().after(now)) {
                    activeNotifications.add(notification);
                }
            }
        }
        return activeNotifications;
    }

    @Override
    public List<SystemNotification> getFutureNotifications(Date maxDate) {
        Date now = new Date();
        List<SystemNotification> futureNotifications = new ArrayList<SystemNotification>();
        for (SystemNotification notification : repository.findAll(modelUserContext)) {
            if (notification.getStartDate().after(now) && notification.getStartDate().before(maxDate)) {
                futureNotifications.add(notification);
            }
        }
        return futureNotifications;
    }

    @Override
    public BigTableSystemNotification createNotification(SystemNotificationSeverity severity, String title, String message, Date startDate, Date endDate) {
        if (startDate == null) {
            startDate = new Date();
        }
        String rowKey = Long.toString(startDate.getTime()) + ":" + UUID.randomUUID().toString();
        BigTableSystemNotification notification = new BigTableSystemNotification(new SystemNotificationRowKey(rowKey));
        notification.setSeverity(severity);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setStartDate(startDate);
        notification.setEndDate(endDate);
        repository.save(notification, FlushFlag.FLUSH);
        return notification;
    }

    @Override
    public SystemNotification updateNotification(SystemNotification notification) {
        repository.save((BigTableSystemNotification) notification, FlushFlag.FLUSH);
        return notification;
    }

    @Override
    public void endNotification(SystemNotification notification) {
        repository.delete(((BigTableSystemNotification) notification).getRowKey());
    }
}