package io.github.doglaum.msavaliadorcredito.application;

import feign.FeignException;
import io.github.doglaum.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.doglaum.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import io.github.doglaum.msavaliadorcredito.domain.model.*;
import io.github.doglaum.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.doglaum.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obtemSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesPorCliente(cpf);
            return SituacaoCliente
                    .builder()
                    .dadosCliente(dadosClienteResponse.getBody())
                    .cartoesCliente(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.getCartoesRendaMenorIgual(renda);
            List<Cartao> cartoes = cartoesResponse.getBody();
            DadosCliente dadosCliente = dadosClienteResponse.getBody();
            return new RetornoAvaliacaoCliente(getCartoesAprovados(cartoes, dadosCliente));
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    private List<CartaoAprovado> getCartoesAprovados(List<Cartao> cartoes, DadosCliente dadosCliente) {
        return cartoes.stream().map(cartao -> {
            return new CartaoAprovado(
                    cartao.getNome(),
                    cartao.getBandeiraCartao(),
                    getLimiteAprovado(BigDecimal.valueOf(dadosCliente.getIdade()), cartao.getLimiteBasico())
            );
        }).collect(Collectors.toList());
    }

    private BigDecimal getLimiteAprovado(BigDecimal idade, BigDecimal limiteBasico) {
        var fator = idade.divide(BigDecimal.valueOf(10));
        return fator.multiply(limiteBasico);
    }
}