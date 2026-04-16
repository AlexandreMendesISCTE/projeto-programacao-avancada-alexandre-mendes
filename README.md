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
