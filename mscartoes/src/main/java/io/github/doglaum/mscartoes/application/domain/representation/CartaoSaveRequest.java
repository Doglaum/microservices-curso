package io.github.doglaum.mscartoes.application.domain.representation;

import io.github.doglaum.mscartoes.application.domain.BandeiraCartao;
import io.github.doglaum.mscartoes.application.domain.Cartao;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeiraCartao;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Cartao toModel() {
        return new Cartao(nome, bandeiraCartao, renda, limiteBasico);
    }
}
