/*
 * Copyright 2024 olehshyker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.olehsh.newsapp.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Params, out Type> {

  /** Operation will be invoked on [Dispatchers.IO] */
  suspend operator fun invoke(params: Params): Result<Type> = withContext(Dispatchers.IO) {
    runCatching {
      execute(params)
    }
      // TODO: don't log errors in release builds
      .onFailure { it.printStackTrace() }
  }

  @Throws(Exception::class)
  protected abstract suspend fun execute(params: Params): Type
}

abstract class NoParamsUseCase<out Type> : BaseUseCase<Unit, Type>() {
  suspend operator fun invoke() = invoke(Unit)
}

abstract class CompletableUseCase<in Params> : BaseUseCase<Params, Unit>()
abstract class NoParamsCompletableUseCase : NoParamsUseCase<Unit>()

abstract class ObservableUseCase<in Params, out Type> {

  private val paramsFlow = MutableSharedFlow<Params>(replay = 1)

  suspend operator fun invoke(params: Params) {
    paramsFlow.emit(params)
  }

  fun observe(): Flow<Type> = paramsFlow
    .flatMapLatest { execute(it) }
    .flowOn(Dispatchers.IO)

  @Throws(Exception::class)
  protected abstract suspend fun execute(params: Params): Flow<Type>
}

abstract class NoParamsObservableUseCase<out Type> : ObservableUseCase<Unit, Type>() {
  suspend operator fun invoke() = invoke(Unit)
}
