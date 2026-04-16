# Known limitations

## 1. Gestão de ficheiros em OneDrive

Em ambientes Windows com OneDrive ativo, podem ocorrer locks transitórios em `lib/build` durante execuções consecutivas do Gradle.

Mitigação atual:

- usar `--no-daemon`;
- limpar `lib/build` antes de pipelines locais mais longos.

## 2. Estratégia de referência em primeiro encontro

Quando um valor anotado com `@Reference` ainda não foi materializado no output, a serialização pode produzir primeiro o objeto com `$id` e só depois usar `$ref` em encontros seguintes.

## 3. Parsing JSON não incluído

O foco da biblioteca é geração de JSON. Parsing de texto JSON para o modelo em memória não faz parte desta entrega.
