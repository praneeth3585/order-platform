package com.praneeth.order_platform.repository;

import com.praneeth.order_platform.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}