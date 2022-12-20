package nl.belastingdienst.barordersystem.FileUploadResponse;

public class FileUploadResponse {

    String fileName;
    String contentType;
    String url;

    public FileUploadResponse(String fileName, String contentType, String url) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url = url;
    }
}
