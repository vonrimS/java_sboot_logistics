package von.rims.logistics.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import von.rims.logistics.entity.RouteData;
import von.rims.logistics.service.FileUploadService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final ResourceLoader resourceLoader;
    private final RouteData routeData;

    private boolean fileUploaded = false;

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${custom.filename}")
    private String customFileName;

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
//            System.out.println(uploadDirPath);


            // Создаем директорию, если она не существует
            File directory = new File(uploadDirPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Получаем имя файла
//            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = customFileName;

            // Формируем путь для сохранения файла
            String filePath = uploadDirPath + File.separator + fileName;
            System.out.println(filePath);

            // Сохраняем файл на сервере
            Path targetLocation = Path.of(filePath).toAbsolutePath().normalize();
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Начинаем загрузку стрктуры данных из файла
//            System.out.println("...загрузка начата");

            loadRoutesFromFile(filePath);
            System.out.println(routeData.getRoutes());
//            loadRoutesFromFile(customFileName);

            // Отмечаем флаг что файл загружен
            fileUploaded = true;

            // Отчищаем кеш
            clearCache();

            // Возвращаем сообщение об успешной загрузке
            return "Файл " + fileName + " успешно загружен.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при загрузке файла.";
        }
    }

    public boolean isFileUploaded() {
        return fileUploaded;
    }

    // Очистка кеша
    @CacheEvict("directRouteCache")
    public void clearCache(){
        // Этот метод не имеет реальной логики
        // Основное назначение - сброс кеша
    }

    public void loadRoutesFromFile(String fileName) {
        try (
                InputStream inputStream = new FileInputStream(fileName);
//                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    int routeId = Integer.parseInt(parts[0]);
                    Set<Integer> routeStops = Arrays.stream(parts)
                            .skip(1) // пропускаем первое число (идентификатор маршрута)
                            .map(Integer::parseInt)
                            .collect(Collectors.toSet());
                    routeData.addRoute(routeId, routeStops);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
