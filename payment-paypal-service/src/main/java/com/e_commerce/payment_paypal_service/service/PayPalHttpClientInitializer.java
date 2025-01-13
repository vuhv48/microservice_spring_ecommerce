package com.e_commerce.payment_paypal_service.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paypal.core.PayPalEnvironment;
import org.springframework.stereotype.Component;
import com.paypal.core.PayPalHttpClient;
import org.springframework.util.Assert;


@Component
public class PayPalHttpClientInitializer {
    public PayPalHttpClient createPayPalHttpClient(String additionalSettings) {
        Assert.notNull(additionalSettings, "The additionalSettings can not be null.");
        JsonObject settingsJson = JsonParser.parseString(additionalSettings).getAsJsonObject();
        String clientId = settingsJson.get("clientId").getAsString();
        String clientSecret = settingsJson.get("clientSecret").getAsString();
        String mode = settingsJson.get("mode").getAsString();
        if (mode.equals("sandbox")) {
            // Create PayPalHttpClient with the retrieved clientId and clientSecret
            return new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
        }
        return new PayPalHttpClient(new PayPalEnvironment.Live(clientId, clientSecret));

    }
}
