package keystore.exceptions;

public class ResourceNotFoundException extends HttpException {

    public ResourceNotFoundException() {
        super(404, "Not found");
    }
}
