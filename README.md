# ProJson

Biblioteca Kotlin para gerar JSON a partir de objetos, com foco em API clara, testes e evolução incremental por TDD.

## Estado do projeto

- Fase atual: Fase 1 - Standard JSON
- Módulo principal: `lib`
- Java alvo: 17

## Roadmap resumido

1. Modelo JSON em memória (`JsonObject`, `JsonArray`, `JsonValue`)
2. Writer textual JSON (válido, compacto)
3. Traversal da árvore JSON
4. `ProJson.toJson(...)` com reflection
5. Referências (`$id`, `$ref`) e customização por annotations
6. Plugins de serialização para string

## Construir JSON manualmente

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

## Serializar objetos com ProJson

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

## Build e testes

```bash
./gradlew test
./gradlew build
```

No Windows PowerShell:

```powershell
.\gradlew.bat test
.\gradlew.bat build
```

## Metodologia

- TDD em ciclos curtos
- Auditoria obrigatória antes de cada commit
- Commits no formato Conventional Commits
