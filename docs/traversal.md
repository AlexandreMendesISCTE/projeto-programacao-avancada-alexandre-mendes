# Traversal da árvore JSON

A biblioteca expõe uma API de traversal baseada em Visitor através de extension functions sobre `JsonValue`.

## API

- `accept(visitor: (JsonValue) -> Unit)`
- `filterNodes(predicate: (JsonValue) -> Boolean): List<JsonValue>`
- `countNodes(predicate: (JsonValue) -> Boolean = { true }): Int`
- `findReferences(): List<JsonReference>`
- `mapPrimitives(transform: (JsonPrimitive) -> JsonValue): JsonValue`

## Decisão de desenho

Foi adotada travessia em profundidade (pre-order):

1. visitar o nó atual;
2. visitar recursivamente os filhos.

Isto simplifica a API para clientes e evita que cada consumidor reimplemente navegação por `JsonObject`/`JsonArray`.

## Benefícios

- Reduz duplicação de lógica de traversal.
- Mantém o cliente desacoplado da estrutura interna dos nós.
- Facilita extração de subconjuntos (ex.: referências) e métricas de árvore.

## Nota

As operações de transformação retornam nova estrutura para evitar mutações implícitas do objeto original.
