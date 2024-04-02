package com.pvxdv.loggerspringaop.error;

import java.util.Date;

public record ErrorMessage(Date timestamp, String message, String requestId) {

}