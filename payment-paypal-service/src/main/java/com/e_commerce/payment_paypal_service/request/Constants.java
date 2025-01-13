package com.e_commerce.payment_paypal_service.request;


public class Constants {
    public final class ErrorCode {
        public static final String SIGN_IN_REQUIRED = "SIGN_IN_REQUIRED";
        public static final String FORBIDDEN = "FORBIDDEN";

        // Private constructor to prevent instantiation
        private ErrorCode() {
        }
    }

    public final class Message {
        public static final String PAYMENT_FAIL_MESSAGE = "PAYMENT_FAIL_MESSAGE";
        public static final String PAYMENT_SUCCESS_MESSAGE = "PAYMENT_SUCCESS_MESSAGE";

        // Private constructor to prevent instantiation
        private Message() {
        }
    }

    public final class Yas {
        public static final String BRAND_NAME = "Yas";

        private Yas() {
        }
    }
}

