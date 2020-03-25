package com.github.ffcfalcos.logger.collector;

public enum LogType {
    TRACE,
    TRACE_BEFORE,
    TRACE_AFTER,
    TRACE_AROUND,
    TRACE_AFTER_THROWING,
    TRACE_AFTER_RETURNING,
    UNKNOWN,
    REST_REQUEST,
    SOAP_REQUEST,
    WEB_SERVICE,
    DATABASE_REQUEST
}
