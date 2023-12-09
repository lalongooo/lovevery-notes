package com.lovevery.notes.android.data.api.serializer

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.lovevery.notes.android.data.api.model.OperationType
import java.lang.reflect.Type

class OperationTypeSerializer : JsonSerializer<OperationType> {
    override fun serialize(
        operationType: OperationType,
        typeOfSrc: Type,
        context: JsonSerializationContext,
    ): JsonElement {
        return JsonPrimitive(operationType.value)
    }
}
