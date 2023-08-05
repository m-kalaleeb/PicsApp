package com.sixbits.domain.usecase

abstract class AbstractUseCase <T>{

    abstract suspend fun execute(): Result<T>
}