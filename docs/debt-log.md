# Technical Debt Log

## Aberto

### DEBT-001 - Gestão de locks em diretórios build no Windows/OneDrive
- Contexto: execuções consecutivas do Gradle podem falhar ao limpar `lib/build` por lock temporário.
- Impacto: instabilidade pontual na auditoria (`build`/`test`).
- Mitigação atual: executar com `--no-daemon` e limpar diretório antes da auditoria quando necessário.
- Prioridade: Média.
- Plano de resolução: investigar configuração de build/cache para reduzir conflitos de lock.

## Fechado

- Nenhum item fechado até ao momento.
