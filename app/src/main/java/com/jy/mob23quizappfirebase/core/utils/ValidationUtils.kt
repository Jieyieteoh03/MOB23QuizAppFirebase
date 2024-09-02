package com.jy.mob23quizappfirebase.core.utils

import com.jy.mob23quizappfirebase.data.model.ValidationField

object ValidationUtils {
    fun validate(vararg fields: ValidationField): String? {
        fields.forEach { field ->
            if(!Regex(field.regExp).matches(field.value)) {
                return field.errMsg
            }
        }
        return null
    }
}