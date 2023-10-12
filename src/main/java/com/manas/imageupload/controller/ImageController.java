package com.manas.imageupload.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manas.imageupload.Model.Image;
import com.manas.imageupload.repository.ImageRepository;

/**
 * ImageController
 */
@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @PostMapping
    public Map<String, String> imageUpload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        System.out.println("Enter to api");
        var map = new HashMap<String, String>();

        if (multipartFile.isEmpty()) {
            System.out.println("File Not Found.");
            return map;
        }

        var name = multipartFile.getOriginalFilename();
        var imageData = multipartFile.getBytes();

        var fileOutputStream = new FileOutputStream("images/" + name);

        var image = new Image();
        image.setImagePath("images/" + name);
        imageRepository.save(image);
        fileOutputStream.write(imageData);
        fileOutputStream.close();
        map.put("imagepath", "images/" + name);
        return map;

    }

    // @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    // // uces = MediaType.
    // public byte[] getImage(@RequestParam("path") String path) throws Exception {
    // var fileInputStream = new FileInputStream(path);
    // if (fileInputStream.available() > 0) {
    // return fileInputStream.readAllBytes();
    // }
    // return null;
    // }

    // Directly
    // @GetMapping(path = "/{basepath}/{name}", produces =
    // MediaType.IMAGE_JPEG_VALUE)
    // // uces = MediaType.
    // public byte[] getImage(@PathVariable("basepath") String path,
    // @PathVariable("name") String name) throws Exception {
    // var fileInputStream = new FileInputStream(path + "/" + name);
    // if (fileInputStream.available() > 0) {
    // return fileInputStream.readAllBytes();
    // }
    // return null;
    // }

    @GetMapping(path = "/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    // uces = MediaType.
    public byte[] getImage(@PathVariable("path") String path) throws Exception {
        path = new String(Base64.getDecoder().decode(path));
        var fileInputStream = new FileInputStream(path);
        if (fileInputStream.available() > 0) {
            return fileInputStream.readAllBytes();
        }
        return null;
    }

    @GetMapping("/allimage")
    public List<Image> images() {
        return imageRepository.findAll();
    }

    @GetMapping
    public String greetMessage() {
        return "Hello world";
    }

}