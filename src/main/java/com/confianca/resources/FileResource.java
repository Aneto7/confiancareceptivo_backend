package com.confianca.resources;

import com.confianca.dto.FileDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/uploadteste")
public class FileResource {

    @PostMapping(path = "/images")
    public void uploadFile(@RequestBody  FileDTO fileDTO) {
        System.out.println(fileDTO);
    }
}
