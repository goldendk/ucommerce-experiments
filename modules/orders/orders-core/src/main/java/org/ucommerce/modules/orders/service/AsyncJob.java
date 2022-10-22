package org.ucommerce.modules.orders.service;
//FIXME: Move to shared module.

import org.ucommerce.shared.kernel.UAudit;
import org.ucommerce.shared.kernel.UAuditData;

import java.time.Instant;
import java.util.Collection;

public class AsyncJob  {

    private String uuid;
    private String type;
    private Instant lastRun;

    /**
     * A bespoke object containing the data context to inject to any handler matching this AsyncJob. It is the reponsibility of
     * the implementor that a marshaller has been registered to handle the context data.
     */
    private AsyncData getContextData;

    private Collection<AsyncAttempt> attempts;

    private UAudit audit;




}
