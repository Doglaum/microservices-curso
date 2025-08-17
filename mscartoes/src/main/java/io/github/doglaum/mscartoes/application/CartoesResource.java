package io.github.doglaum.mscartoes.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cartoes")
public class CartoesResource {

    public String status() {
        return "ok";
    }

}
