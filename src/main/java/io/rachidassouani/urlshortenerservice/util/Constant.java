package io.rachidassouani.urlshortenerservice.util;

public class Constant {
    public static final String urlExpiredErrorMessage = "Url Expired. Please try generating a fresh one.";
    public static final String urlExpiredStatusMessage = "200";

    public static final String shortUrlNotValidErrorMessage = "Invalid Url";
    public static final String shortUrlNotValidStatusMessage = "400";

    public static final String shortUrlNotExistOrExpiredErrorMessage = "Url does not exist or it might have expired";
    public static final String shortUrlNotExistOrExpiredStatusMessage = "400";

    public static final String errorProcessingRequestMessage = "There was an error processing your request. please try again.";
    public static final String errorProcessingRequestStatus = "404";

}
