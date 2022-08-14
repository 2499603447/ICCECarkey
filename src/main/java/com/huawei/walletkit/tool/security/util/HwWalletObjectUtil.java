/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.huawei.walletkit.tool.security.util;

import com.alibaba.fastjson.JSONObject;
import com.huawei.walletkit.tool.security.manager.active.Constants;
import com.huawei.walletkit.tool.security.model.hwobject.*;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;

/**
 * HwWalletObject utility class.
 *
 * @since 2019-12-4
 */
public class HwWalletObjectUtil {
    private static final String TAG = "HwWalletObjectUtil";

    private static final String STATE_ACTIVE = "active";

    private static final String STATE_INACTIVE = "inactive";

    private static final String STATE_COMPLETED = "completed";

    private static final String STATE_EXPIRED = "expired";

    private static final List<String> STATE_TYPE_LIST =
        new ArrayList<>(Arrays.asList(STATE_ACTIVE, STATE_INACTIVE, STATE_COMPLETED, STATE_EXPIRED));

    /**
     * Validate a model.
     *
     * @param model the pass model.
     */
    public static void validateModel(HwWalletObject model) {
        if (model != null) {
            checkRequiredParams(model.getPassTypeIdentifier(), "passTypeIdentifier", 64);
            checkRequiredParams(model.getPassStyleIdentifier(), "passStyleIdentifier", 64);
            checkRequiredParams(model.getOrganizationName(), "organizationName", 64);
            checkRequiredParams(model.getPassVersion(), "passVersion", 64);
        } else {
            throw new IllegalArgumentException("The model is empty");
        }
    }

    /**
     * Validate an instance.
     *
     * @param instance the instance.
     */
    public static void validateInstance(HwWalletObject instance) {
        if (instance != null) {
            checkBaseInfo(instance);
            checkFields(instance.getFields());
            checkLinkDevicePass(instance.getLinkDevicePass(), instance.getSerialNumber());
        } else {
            throw new IllegalArgumentException("The instance is empty.");
        }
    }

    /**
     * Check a required attribute.
     *
     * @param jsonObject the object to be checked.
     * @param attrName the attribute's name.
     * @param maxLen the attribute's maximum length.
     */
    private static void checkRequiredParams(JSONObject jsonObject, String attrName, int maxLen) {
        if (!jsonObject.containsKey(attrName) || !(jsonObject.get(attrName) instanceof String)) {
            throw new IllegalArgumentException(attrName + " is missing.");
        }
        if (jsonObject.getString(attrName).length() > maxLen) {
            throw new IllegalArgumentException(attrName + " exceeds maximum length.");
        }
    }

    /**
     * Check a required attribute.
     *
     * @param value the object to be checked.
     * @param attrName the attribute's name.
     * @param maxLen the attribute's maximum length.
     */
    private static void checkRequiredParams(String value,  String attrName, int maxLen) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(attrName + " is missing.");
        }
        if (value.length() > maxLen) {
            throw new IllegalArgumentException(attrName + " exceeds maximum length.");
        }
    }

    /**
     * Check if the Status of an instance is legal.
     *
     * @param instance the instance.
     */
    private static void validateStatusDate(JSONObject instance) {
        JSONObject fields = instance.getJSONObject("fields");
        if (fields == null || fields.getJSONObject("status") == null) {
            return;
        }
        String state = fields.getJSONObject("status").getString("state");
        String effectTime = fields.getJSONObject("status").getString("effectTime");
        String expireTime = fields.getJSONObject("status").getString("expireTime");
        if (!StringUtils.isEmpty(state)) {
            if (!STATE_TYPE_LIST.contains(state.toLowerCase(Locale.getDefault()))) {
                throw new IllegalArgumentException("state is invalid.");
            }
        }

        if ((StringUtils.isEmpty(effectTime) && !StringUtils.isEmpty(expireTime))
            || (!StringUtils.isEmpty(effectTime) && StringUtils.isEmpty(expireTime))) {
            throw new IllegalArgumentException("effectTime and expireTime should be both exist or not exist.");
        }

        if (StringUtils.isEmpty(expireTime)) {
            return;
        }

        Date statusEffectTime;
        Date statusExpireTime;

        statusEffectTime = getLocalTimeByUtcString(effectTime);
        statusExpireTime = getLocalTimeByUtcString(expireTime);

        if ((statusExpireTime.before(new Date())) || (statusExpireTime.before(statusEffectTime))) {
            throw new IllegalArgumentException("expireTime must be later than effectTime and current time.");
        }
    }

    /**
     * Check if the Status of an instance is legal.
     *
     * @param status the status.
     */
    private static void validateStatusDate(Status status) {
        if (status == null) {
            return;
        }
        String state = status.getState();
        String effectTime =  status.getEffectTime();
        String expireTime =  status.getExpireTime();
        if (!StringUtils.isEmpty(state)) {
            if (!STATE_TYPE_LIST.contains(state.toLowerCase(Locale.getDefault()))) {
                throw new IllegalArgumentException("state is invalid.");
            }
        }

        if ((StringUtils.isEmpty(effectTime) && !StringUtils.isEmpty(expireTime))
                || (!StringUtils.isEmpty(effectTime) && StringUtils.isEmpty(expireTime))) {
            throw new IllegalArgumentException("effectTime and expireTime should be both exist or not exist.");
        }

        if (StringUtils.isEmpty(expireTime)) {
            return;
        }

        Date statusEffectTime;
        Date statusExpireTime;

        statusEffectTime = getLocalTimeByUtcString(effectTime);
        statusExpireTime = getLocalTimeByUtcString(expireTime);

        if ((statusExpireTime.before(new Date())) || (statusExpireTime.before(statusEffectTime))) {
            throw new IllegalArgumentException("expireTime must be later than effectTime and current time.");
        }
    }

    /**
     * Convert UTC to local time.
     *
     * @param utcTimeString the UTC string.
     * @return the local time in Date type.
     */
    private static Date getLocalTimeByUtcString(String utcTimeString) {
        Date date = null;

        if (utcTimeString != null) {
            try {
                date = Date.from(Instant.parse(utcTimeString));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid time format. Error: " + e.getMessage());
            }
        }
        return date;
    }

    private static void checkBaseInfo(HwWalletObject instance) {
        String passTypeIdentifier = instance.getPassStyleIdentifier();
        String passStyleIdentifier = instance.getPassStyleIdentifier();
        String organizationPassId = instance.getOrganizationPassId();
        String serialNumber = instance.getSerialNumber();
        checkRequiredParams(passTypeIdentifier, "passTypeIdentifier", 64);
        checkRequiredParams(passStyleIdentifier, "passStyleIdentifier", 64);
        checkRequiredParams(organizationPassId, "organizationPassId", 64);
        checkRequiredParams(serialNumber, "serialNumber", 64);
        if (!passTypeIdentifier.startsWith(Constants.PASS_TYPE_PREFIX)) {
            throw new IllegalArgumentException("passTypeIdentifier:" + passTypeIdentifier + " is illegal.");
        }
        if (!organizationPassId.equals(serialNumber)) {
            throw new IllegalArgumentException("organizationPassId and serialNumber must be same.");
        }
    }

    private static void checkFields(Fields fields) {
        if (fields == null || fields.getStatus() == null) {
            return;
        }
        checkCommonFields(fields.getCommonFields());
        validateStatusDate(fields.getStatus());
    }

    private static void checkCommonFields(List<ValueObject> commonFields) {
        if (commonFields == null || commonFields.isEmpty()) {
            return;
        }
        Set<String> mustFieldsSet = new HashSet<>();
        mustFieldsSet.add("bleTargetActivity");
        mustFieldsSet.add("bleFeature");
        mustFieldsSet.add("bleMacAddress");
        mustFieldsSet.add("readerMatchValue");
        mustFieldsSet.add("ownerPassTypeIdentifier");
        mustFieldsSet.add("bleServiceUuid");
        mustFieldsSet.add("bleTargetPackage");
        mustFieldsSet.add("bleTargetService");
        mustFieldsSet.add("vehicleId");
        mustFieldsSet.add("carModel");

        for (ValueObject object : commonFields) {
            String key = object.getKey();
            String value = object.getValue();
            if ("bleTargetActivity".equals(key) || "bleTargetPackage".equals(key) || "bleTargetService".equals(key)
                || "vehicleId".equals(key) || "carModel".equals(key)) {
                if (value == null || value.isEmpty()) {
                    throw new IllegalArgumentException("bleTargetActivity, bleTargetPackage, bleTargetService, vehicleId, carModel is empty");
                }
                mustFieldsSet.remove(key);
            }
            if ("bleFeature".equals(key)) {
                if (!"hwpass.carkey.ble".equals(value)) {
                    throw new IllegalArgumentException("bleFeature must be hwpass.carkey.ble");
                }
                mustFieldsSet.remove(key);
            }
            if ("bleMacAddress".equals(key)) {
                if (value == null || !value.matches("([A-F0-9]{2}:){5}[A-F0-9]{2}")) {
                    throw new IllegalArgumentException("bleMacAddress is illegal, must 6 bytes which contains A-F or 0-9 and split by ':'");
                }
                mustFieldsSet.remove(key);
            }
            if ("readerMatchValue".equals(key)) {
                if (value == null || !value.matches("([A-F0-9]{2}:){5}[A-F0-9]{2}")) {
                    throw new IllegalArgumentException("readerMatchValue must smaller than 20 bytes and contains A-F or 0-9");
                }
                mustFieldsSet.remove(key);
            }


            if ("ownerPassTypeIdentifier".equals(key)) {
                if (!"hwpass.stdcarkey.std".equals(value)) {
                    throw new IllegalArgumentException("bleFeature must be hwpass.stdcarkey.std");
                }
                mustFieldsSet.remove(key);
            }

            if ("bleServiceUuid".equals(key)) {
                if (value == null || !value.matches("([A-F0-9]{2}:){5}[A-F0-9]{2}")) {
                    throw new IllegalArgumentException("bleServiceUuid is illegal, must 32 bytes which contains A-F or 0-9 and split by ':'");
                }
                mustFieldsSet.remove(key);
            }
        }
        if (!mustFieldsSet.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String key : mustFieldsSet) {
                builder.append(key+ ",");
            }
            throw new IllegalArgumentException("must contains commonFields:" + builder);
        }
    }

    private static void checkLinkDevicePass(LinkDevicePass linkDevicePass, String serialNumber) {
        if (linkDevicePass == null) {
            throw new IllegalArgumentException("lineDevice is null.");
        }

        if (!"1".equals(linkDevicePass.getNfcType())) {
            throw new IllegalArgumentException("nfcType must be equals to 1.");
        }

        if (!serialNumber.equals(linkDevicePass.getSerialNumber())) {
            throw new IllegalArgumentException("serialNumber in linkDevicePass must equals to outside's serialNumber");
        }

        if (!Constants.SERVER_PUBLIC_KEY.equals(linkDevicePass.getSpPublickey())) {
            throw new IllegalArgumentException("spPublicKey in linkDevicePass must equals to 'SERVER_PUBLIC_KEY' in Constants");
        }
    }
}
