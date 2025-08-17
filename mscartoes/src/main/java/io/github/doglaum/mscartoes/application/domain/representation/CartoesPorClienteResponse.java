package io.github.doglaum.mscartoes.application.domain.representation;

import io.github.doglaum.mscartoes.application.domain.Cartao;
import io.github.doglaum.mscartoes.application.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limite;

    public static CartoesPorClienteResponse fromModel(ClienteCartao cartao) {
        return new CartoesPorClienteResponse(
                cartao.getCpf(),
                cartao.getCartao().getBandeiraCartao().toString(),
                cartao.getLimite()
        );
    }
}
