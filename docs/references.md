# JSON references

A fase de referências introduz suporte a `$id` e `$ref` para representar grafos e partilha de objetos.

## Regras principais

- `JsonReference` representa um nó `{ "$ref": "..." }`.
- Objetos com identidade recebem `$id`.
- Propriedades anotadas com `@Reference` passam a serializar os valores por referência.
- Objetos de valor (ex.: data classes sem referência explícita) permanecem embebidos sem `$id`.

## Exemplo

```kotlin
class Task(
    val description: String,
    @Reference val dependencies: List<Task>
)
```

Se uma dependência já foi serializada, o output usa `$ref` em vez de repetir o objeto inteiro.

## Nota de desenho

A decisão de referência ocorre no mapeamento (`ProJson`) e não no writer textual.
