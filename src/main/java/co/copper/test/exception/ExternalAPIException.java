package co.copper.test.exception;

public class ExternalAPIException extends RuntimeException {

    private String apiName;

    public ExternalAPIException(String apiName) {
        super(String.format("Failed fetching data from the external api: %s", apiName));
    }

}