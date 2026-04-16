# Modelo JSON em memória

Esta fase introduz o modelo base da biblioteca para representar e manipular JSON em memória.

## Tipos principais

- `JsonValue`: tipo base de todos os nós
- `JsonObject`: objeto mutável com propriedades ordenadas por inserção
- `JsonArray`: array mutável com elementos ordenados
- `JsonPrimitive`: wrapper para `String`, `Number`, `Boolean`
- `JsonNull`: singleton para `null`
- `JsonReference`: nó de referência (preparado para a fase de referências)

## Regras de integridade

- nomes de propriedades não podem ser vazios ou em branco;
- tipos não suportados geram `IllegalArgumentException`;
- ordem de propriedades em `JsonObject` é estável.

## Operações suportadas nesta fase

### JsonObject

- `setProperty(name, value)`
- `getProperty(name)`
- `removeProperty(name)`
- `propertyNames()`

### JsonArray

- `add(value)`
- acesso por índice (`array[index]`)
- `removeAt(index)`

## Testes da fase

Os testes cobrem:

- criação de objeto e array vazios;
- adicionar, substituir e remover propriedades;
- adicionar e remover elementos em arrays;
- prevenção de estados inválidos;
- preservação da ordem de inserção;
- composição com estruturas aninhadas (`JsonObject` e `JsonArray`).
