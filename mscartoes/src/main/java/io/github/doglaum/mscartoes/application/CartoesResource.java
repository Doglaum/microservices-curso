package io.github.doglaum.mscartoes.application;

import io.github.doglaum.mscartoes.application.domain.Cartao;
import io.github.doglaum.mscartoes.application.domain.representation.CartaoSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CartaoSaveRequest request) {
        var cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public List<Cartao> getCartoesRendaMenorIgual(@RequestParam Long renda) {
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return cartaoService.getCartoesRendaMenorIgual(renda);
    }

}
