/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Trustly Group AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.trustly.api.commons;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.trustly.api.data.notification.Notification;
import com.trustly.api.data.notification.NotificationParameters;
import com.trustly.api.data.notification.NotificationData;

public class NotificationDeserializer implements JsonDeserializer<Notification> {

    private final Gson gson;

    // Map of subclasses
    private final Map<String, Class<? extends NotificationData>> dataTypes;

    public NotificationDeserializer() {
        gson = new Gson();
        dataTypes = new HashMap<>();
    }

    // Registers a notificationData subclass
    public void registerDataType(final String method, final Class<? extends NotificationData> dataTypeClass) {
        dataTypes.put(method, dataTypeClass);
    }

    @Override
    public Notification deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject request = jsonElement.getAsJsonObject();
        final JsonObject params = request.get("params").getAsJsonObject();

        final JsonObject data = params.get("data").getAsJsonObject();

        // Read the field named "Request.method"
        final JsonElement commandTypeElement = request.get("method");

        // Query the dataTypes map to instance the right subclass
        final Class<? extends NotificationData> commandInstanceClass = dataTypes.get(commandTypeElement.getAsString());

        final NotificationData dataObject = gson.fromJson(data, commandInstanceClass);
        final NotificationParameters paramsObject = gson.fromJson(params, NotificationParameters.class);
        final Notification requestObject = gson.fromJson(request, Notification.class);

        if (data.has("attributes")) {
            if (data.get("attributes").isJsonNull()) {
                dataObject.setAttributes(new HashMap<>());
            }
        }

        paramsObject.setData(dataObject);
        requestObject.setParams(paramsObject);
        return requestObject;
    }
}
