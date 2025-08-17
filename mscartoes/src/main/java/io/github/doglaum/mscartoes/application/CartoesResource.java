package io.github.doglaum.mscartoes.application;

import io.github.doglaum.mscartoes.application.domain.Cartao;
import io.github.doglaum.mscartoes.application.domain.ClienteCartao;
import io.github.doglaum.mscartoes.application.domain.representation.CartaoSaveRequest;
import io.github.doglaum.mscartoes.application.domain.representation.CartoesPorClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

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
    public ResponseEntity<List<Cartao>> getCartoesRendaMenorIgual(@RequestParam Long renda) {
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesPorCliente(@RequestParam String cpf) {
        List<ClienteCartao> clienteCartaoList = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> cartoesPorClienteList = clienteCartaoList.stream()
                .map(CartoesPorClienteResponse::fromModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartoesPorClienteList);
    }

}