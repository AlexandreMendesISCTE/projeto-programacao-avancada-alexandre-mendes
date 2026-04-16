# ADR-0002 - Política de referências

- Estado: Aceite
- Data: 2026-04-17

## Contexto

A biblioteca precisa representar grafos sem expor UUIDs ao cliente. O enunciado exige transparência de referências e suporte a `$id`/`$ref`.

## Decisão

- referências são decididas durante o mapeamento em `ProJson`;
- propriedades com `@Reference` acionam serialização orientada a referência;
- objetos com identidade recebem `$id`;
- objetos de valor não referenciados mantêm serialização embebida.

## Consequências

### Positivas
- separação clara entre modelo, mapeamento e writer;
- API de uso simples para cliente;
- suporte a grafos sem alterar código de domínio.

### Negativas
- ordem de travessia pode influenciar se um valor referenciado surge primeiro inline ou por `$ref`.
