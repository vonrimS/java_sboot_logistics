package von.rims.logistics.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadFile(MultipartFile file);

    boolean isFileUploaded();

}
