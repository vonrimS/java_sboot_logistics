package von.rims.logistics.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import von.rims.logistics.entity.RouteData;
import von.rims.logistics.service.FileUploadService;
import von.rims.logistics.service.RouteService;

import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteData routeData;
    private final FileUploadService fileUploadService;

    public boolean hasDirectRoute(int x, int y) {
        // Проверяем наличие файла перед вызовом
        if (!fileUploadService.isFileUploaded()) {
            // Возвращаем false
            return false;
        }

        System.out.println(routeData.getRoutes());

        return routeData.hasDirectRoute(x, y);
    }
}
