package ru.javawebinar.webapp.exceptions;

/**
 * GKislin
 * 09.10.2015.
 */
public class WebAppException extends RuntimeException {
    private final String uuid;
    private ExceptionType type;

    public WebAppException(ExceptionType type, String uuid, Exception e) {
        super(type.getMessage(), e);
        this.type = type;
        this.uuid = uuid;
    }


    public WebAppException(ExceptionType type, String uuid) {
        super(type.getMessage());
        this.type = type;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public ExceptionType getType() {
        return type;
    }
}
