package api.clicks.controllers;

import api.clicks.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/image")
    public ResponseEntity<Object> imageTest(@RequestParam("id") String id,
                                            @RequestParam("file") MultipartFile file){

        try{
            imageService.imageStore(file, Long.parseLong(id));
        }catch (IOException e){
            return new ResponseEntity<>("Error en archivo", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/download/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable("name") String name) {
        Path targetPath = Paths.get("./images/"
                +name)
                .normalize();
        try {
            Resource resource = new UrlResource(targetPath.toUri());
            System.out.println(targetPath);
            System.out.println(resource);
            if (resource.exists()){
                String contentType = Files.probeContentType(targetPath);
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}