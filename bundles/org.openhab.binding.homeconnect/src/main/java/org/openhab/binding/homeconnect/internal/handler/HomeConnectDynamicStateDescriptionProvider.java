/**
 * Copyright (c) 2018-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.homeconnect.internal.handler;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.type.DynamicStateDescriptionProvider;
import org.eclipse.smarthome.core.types.StateDescription;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link HomeConnectDynamicStateDescriptionProvider} is responsible for handling dynamic thing values.
 *
 * @author Jonas Brüstel - Initial contribution
 */
@Component(service = { DynamicStateDescriptionProvider.class, HomeConnectDynamicStateDescriptionProvider.class })
@NonNullByDefault
public class HomeConnectDynamicStateDescriptionProvider implements DynamicStateDescriptionProvider {

    private final Logger logger = LoggerFactory.getLogger(HomeConnectDynamicStateDescriptionProvider.class);
    private final ConcurrentHashMap<String, StateDescription> stateDescriptions = new ConcurrentHashMap<>();

    @Override
    public @Nullable StateDescription getStateDescription(@NonNull Channel channel,
            @Nullable StateDescription originalStateDescription, @Nullable Locale locale) {

        if (stateDescriptions.containsKey(channel.getUID().getAsString())) {
            logger.trace("Return dynamic state description for channel-uid {}.", channel.getUID().getAsString());
            return stateDescriptions.get(channel.getUID().getAsString());
        }

        return originalStateDescription;
    }

    protected void putStateDescriptions(String channelUid, StateDescription stateDescription) {
        logger.debug("Adding state description. channel-uid:{} state-description:{}", channelUid, stateDescription);
        stateDescriptions.put(channelUid, stateDescription);
    }

    protected void removeStateDescriptions(String channelUid) {
        logger.debug("Removing state description for channel-uid {}.", channelUid);
        stateDescriptions.remove(channelUid);
    }

}
