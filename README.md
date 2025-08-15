# Monorepo Microservices

Este repositório contém dois microserviços organizados em um monorepo utilizando Maven:

- **eurekaserver**: Serviço de registro Eureka para descoberta de serviços.
- **msclientes**: Microserviço de clientes, registrado no Eureka.

## Estrutura

```
pom.xml                # Projeto pai (monorepo)
eurekaserver/          # Microserviço Eureka Server
msclientes/            # Microserviço Clientes
```

## Como construir e executar

1. **Build completo:**
   ```
   mvn clean install
   ```
   Isso irá compilar todos os módulos.

2. **Executar Eureka Server:**
   ```
   cd eurekaserver
   mvn spring-boot:run
   ```

3. **Executar msclientes:**
   ```
   cd msclientes
   mvn spring-boot:run
   ```

## Observações
- O pom.xml raiz centraliza configurações e dependências comuns.
- Cada microserviço possui seu próprio pom.xml, herdando do projeto pai.
- Recomenda-se utilizar Java 11.