# Technical Debt Log

## Aberto

### DEBT-001 - Gestão de locks em diretórios build no Windows/OneDrive
- Contexto: execuções consecutivas do Gradle podem falhar ao limpar `lib/build` por lock temporário.
- Impacto: instabilidade pontual na auditoria (`build`/`test`).
- Mitigação atual: executar com `--no-daemon` e limpar diretório antes da auditoria quando necessário.
- Prioridade: Média.
- Plano de resolução: investigar configuração de build/cache para reduzir conflitos de lock.

### DEBT-002 - Estratégia de referências no primeiro encontro
- Contexto: quando um objeto referenciado aparece pela primeira vez numa propriedade `@Reference`, pode ser serializado inline com `$id` antes de ser reutilizado via `$ref`.
- Impacto: pode introduzir variação de forma conforme a ordem de travessia.
- Mitigação atual: cobertura de testes e documentação explícita da política.
- Prioridade: Baixa.
- Plano de resolução: avaliar política configurável de materialização de referências.

## Fechado

- DEBT-000 - Bootstrap inicial sem arquitetura formal, resolvido com ADR-0001 e organização em camadas.
