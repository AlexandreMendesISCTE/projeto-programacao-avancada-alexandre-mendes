# Changelog

Todas as alterações relevantes deste projeto serão documentadas aqui.

O formato base segue Keep a Changelog e versionamento semântico adaptado para marcos do projeto académico.

## [1.0.0] - 2026-04-17
### Added
- README final com tutorial completo de utilização.
- KDoc para API pública principal.
- Documentação final de limitações e ADR de política de referências.

## [0.9.0] - 2026-04-17
### Added
- Endurecimento da API para entrega: validações e documentação consolidada.
- Registo de limitações conhecidas.

## [0.8.0] - 2026-04-17
### Added
- Sistema de plugins de serialização para string com `@JsonString`.
- Contrato público `JsonStringSerializer<T : Any>`.

## [0.7.0] - 2026-04-17
### Added
- Support para `@JsonProperty`, `@JsonIgnore` e `@Reference`.
- Leitura de annotations via reflection no pipeline de serialização.

## [0.6.0] - 2026-04-17
### Added
- Suporte a referências JSON com `$id` e `$ref`.
- Registry de identidade para gestão de referências durante uma serialização.

### Changed
- `ProJson` passou a gerir referências e ciclo de serialização com contexto interno.

## [0.1.0] - 2026-04-16
### Added
- Bootstrap inicial do projeto Kotlin com Gradle Wrapper.
- Configuração de testes e dependência de reflection.
- Documentação inicial do projeto (README, debt log e ADR inicial).
