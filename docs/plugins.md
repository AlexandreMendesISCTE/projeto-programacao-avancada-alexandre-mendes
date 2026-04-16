# Plugins de serialização para string

Plugins permitem converter classes para valores string em JSON sem alterar o núcleo da biblioteca.

## Contrato

```kotlin
interface JsonStringSerializer<T : Any> {
    fun serialize(value: T): String
}
```

## Ativação por annotation

```kotlin
@JsonString(DateAsText::class)
data class DateValue(val day: Int, val month: Int, val year: Int)

class DateAsText : JsonStringSerializer<DateValue> {
    override fun serialize(value: DateValue): String =
        "%02d/%02d/%04d".format(value.day, value.month, value.year)
}
```

## Regras de validação

- o serializer deve implementar `JsonStringSerializer`;
- deve ter construtor público sem argumentos;
- erros de contrato falham cedo com `IllegalArgumentException`.
