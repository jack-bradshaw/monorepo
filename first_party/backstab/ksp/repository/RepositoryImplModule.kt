package com.jackbradshaw.backstab.ksp.repository

import com.jackbradshaw.backstab.core.repository.Repository as CoreRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryImplModule {
  @Binds fun bindRepository(impl: RepositoryImpl): Repository

  @Binds fun bindCoreRepository(impl: RepositoryImpl): CoreRepository
}
