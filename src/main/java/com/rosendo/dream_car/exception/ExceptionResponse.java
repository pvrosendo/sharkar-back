package com.rosendo.dream_car.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
