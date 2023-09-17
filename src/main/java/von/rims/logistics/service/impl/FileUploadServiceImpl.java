package von.rims.logistics.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import von.rims.logistics.service.FileUploadService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final ResourceLoader resourceLoader;


    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "Выберите файл для загрузки.";
        }

        try {
            // Получаем ресурс по относительному пути
            Resource resource = resourceLoader.getResource(uploadDir);

            // Получаем абсолютный путь к директории
            String uploadDirPath = resource.getFile().getAbsolutePath();


            // Создаем директорию, если она не существует
            File directory = new File(uploadDirPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Получаем имя файла
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Формируем путь для сохранения файла
            String filePath = uploadDirPath + File.separator + fileName;
//            System.out.println(filePath);

            // Сохраняем файл на сервере
            Path targetLocation = Path.of(filePath).toAbsolutePath().normalize();
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Возвращаем сообщение об успешной загрузке
            return "Файл " + fileName + " успешно загружен.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при загрузке файла.";
        }

    }
}
