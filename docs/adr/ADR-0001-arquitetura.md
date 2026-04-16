# ADR-0001 - Arquitetura inicial da biblioteca ProJson

- Estado: Aceite
- Data: 2026-04-16

## Contexto

O projeto precisa de evoluir em TDD, com API pública estável e separação clara entre modelo JSON, serialização textual e reflection.

## Decisão

Organizar a biblioteca nas seguintes áreas:

- `api`: classes públicas (`ProJson`, `JsonObject`, `JsonArray`, `JsonReference`, annotations)
- `model`: tipos internos da árvore JSON (`JsonValue`, `JsonPrimitive`, `JsonNull`)
- `serialization`: writer textual e escaping
- `reflection`: inspeção de propriedades, nomes e leitura de annotations
- `internal`: suporte técnico não público (registry, ids, helpers)

## Consequências

### Positivas
- Menor acoplamento entre fases de implementação.
- Facilidade para testar componentes isoladamente.
- API pública mais previsível para o utilizador.

### Negativas
- Mais ficheiros e alguma sobrecarga inicial de estrutura.
- Necessidade de disciplina para não vazar internals para a API pública.
