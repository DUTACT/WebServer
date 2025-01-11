package com.dutact.web.common.notification.subscription.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSubscriptionRepository extends JpaRepository<AccountSubscription, String>,
        JpaSpecificationExecutor<AccountSubscription> {
}
