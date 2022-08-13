package uz.gita.justdictionary.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.justdictionary.domain.usecase.AppUseCase
import uz.gita.justdictionary.domain.usecase.impl.AppUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindAppUseCase(impl: AppUseCaseImpl): AppUseCase
}