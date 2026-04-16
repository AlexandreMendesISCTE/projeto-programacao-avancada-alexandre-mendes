# Annotations de customização

A API suporta customização do output JSON via annotations.

## Annotations disponíveis

- `@JsonProperty("name")`: renomeia o identificador da propriedade no JSON.
- `@JsonIgnore`: remove a propriedade do output.
- `@Reference`: serializa a propriedade por referência (`$ref`) quando aplicável.

## Exemplo

```kotlin
class Task(
    @JsonProperty("desc") val description: String,
    @JsonIgnore val deadline: String?,
    @JsonProperty("deps") @Reference val dependencies: List<Task>
)
```

Output esperado:

```json
{"$type":"Task","$id":"id-1","desc":"T1","deps":[]}
```
