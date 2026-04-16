# Reflection serialization (fase 1)

Esta iteração introduz `ProJson.toJson(...)` para converter objetos Kotlin para o modelo JSON em memória.

## Regras suportadas

- `null` -> `JsonNull`
- `String`, `Number`, `Boolean` -> `JsonPrimitive`
- `Iterable` e arrays JVM -> `JsonArray`
- `Map` -> `JsonObject` sem propriedade `$type`
- classes/data classes -> `JsonObject` com propriedade `$type`

## Ordem de propriedades

A ordem de serialização prioriza os parâmetros do construtor primário e, depois, propriedades restantes de forma determinística.

## Estrutura interna

- `ProJson` orquestra o mapeamento para `JsonValue`.
- `ReflectionInspector` encapsula a leitura de propriedades e metadados de tipo.

Esta separação reduz acoplamento e prepara terreno para annotations e plugins nas próximas fases.
