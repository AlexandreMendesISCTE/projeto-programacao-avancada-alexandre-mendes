# ProJson

Biblioteca Kotlin para gerar JSON a partir de objetos, incluindo suporte a referências (`$id`/`$ref`), annotations de customização e plugins de serialização para string.

## Entrega

- Estado: concluído para entrega académica
- Módulo principal: `lib`
- Java alvo: 17
- Branch principal: `master`

## Funcionalidades principais

1. Modelo JSON em memória com API de leitura/escrita.
2. Writer textual JSON compacto e válido.
3. Traversal da árvore via Visitor e utilitários funcionais.
4. `ProJson.toJson(...)` com reflection.
5. Referências com `$id`/`$ref` controladas por annotation.
6. Customização por annotations (`@JsonProperty`, `@JsonIgnore`, `@Reference`).
7. Plugins via `@JsonString` para serialização em texto.

## Quickstart

### Build e testes

```bash
./gradlew test
./gradlew build
```

No Windows PowerShell:

```powershell
.\gradlew.bat test
.\gradlew.bat build
```

### Artefacto JAR

Após `build`, o JAR fica em:

- `lib/build/libs/`

## Tutorial

### 1) Construir JSON manualmente

### Exemplo com JsonObject

```kotlin
val json = JsonObject().apply {
	setProperty("name", "ProJson")
	setProperty("year", 2026)
	setProperty("stable", false)
}

println(json)
// {"name":"ProJson","year":2026,"stable":false}
```

### Exemplo com JsonArray

```kotlin
val array = JsonArray()
	.add("a")
	.add(null)
	.add(JsonObject().apply { setProperty("id", 1) })

println(array)
// ["a",null,{"id":1}]
```

### 2) Serializar objetos com ProJson

```kotlin
data class DateDto(val day: Int, val month: Int, val year: Int)

val json = ProJson().toJson(DateDto(31, 4, 2026)) as JsonObject
println(json)
// {"$type":"DateDto","day":31,"month":4,"year":2026}
```

```kotlin
val listJson = ProJson().toJson(listOf("a", null, "b")) as JsonArray
println(listJson)
// ["a",null,"b"]
```

```kotlin
val mapJson = ProJson().toJson(mapOf("x" to 1, "y" to true)) as JsonObject
println(mapJson)
// {"x":1,"y":true}
```

### 3) Referências com $id e $ref

```kotlin
class Task(
	val description: String,
	@Reference val dependencies: List<Task>
)

val t1 = Task("T1", emptyList())
val t2 = Task("T2", listOf(t1))
val json = ProJson().toJson(listOf(t1, t2))
println(json)
```

Com a propriedade anotada com `@Reference`, dependências podem ser materializadas como `$ref` quando já existem no documento.

### 4) Customizar nomes e ignorar campos

```kotlin
class Task(
	@JsonProperty("desc") val description: String,
	@JsonIgnore val internalDeadline: String?,
	@JsonProperty("deps") @Reference val dependencies: List<Task>
)
```

### 5) Plugins de serialização para string

```kotlin
@JsonString(DateAsText::class)
data class DateValue(val day: Int, val month: Int, val year: Int)

class DateAsText : JsonStringSerializer<DateValue> {
	override fun serialize(value: DateValue): String =
		"%02d/%02d/%04d".format(value.day, value.month, value.year)
}

val json = ProJson().toJson(listOf(DateValue(30, 2, 2026)))
println(json)
// ["30/02/2026"]
```

## API pública

- `ProJson`
- `JsonObject`
- `JsonArray`
- `JsonReference`
- `JsonValue`, `JsonPrimitive`, `JsonNull`
- `JsonStringSerializer<T : Any>`
- `@Reference`, `@JsonProperty`, `@JsonIgnore`, `@JsonString`

## Documentação complementar

- `docs/modelo-json.md`
- `docs/traversal.md`
- `docs/reflection-serialization.md`
- `docs/references.md`
- `docs/annotations.md`
- `docs/plugins.md`
- `docs/known-limitations.md`
- `docs/adr/ADR-0001-arquitetura.md`
- `docs/adr/ADR-0002-reference-policy.md`

## Notas finais

- O projeto segue TDD com auditoria antes de commits.
- O foco da entrega é geração de JSON; parsing não está incluído.

## Metodologia

- TDD em ciclos curtos
- Auditoria obrigatória antes de cada commit
- Commits no formato Conventional Commits
