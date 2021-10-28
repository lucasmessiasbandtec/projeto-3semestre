package com.gotravel.application.validations

import com.gotravel.application.response.Response
import com.gotravel.domain.entities.User
import org.springframework.validation.BindingResult

object InvalidRequest {
    fun check(response: Response<Any>, result: BindingResult): Response<Any> {

        if (result.hasErrors()){
            result.allErrors.forEach { error ->
                error.defaultMessage?.let { response.erros.add(it) }
            }
            return response
        }
        return response
    }
}