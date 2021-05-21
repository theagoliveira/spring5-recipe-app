package guru.springframework.spring5recipeapp.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public void saveFile(Long recipeId, MultipartFile file) {}

}
