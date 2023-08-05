package com.sixbits.domain.usecase

abstract class AbstractUseCaseWithParams <PARAMS, T>{

    abstract suspend fun execute(params: PARAMS): Result<T>
}