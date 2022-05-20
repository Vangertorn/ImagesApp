package com.vangertorn.imagesapp.presentation.splash

import app.cash.turbine.test
import com.vangertorn.imagesapp.domain.usecase.FirstStartUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel

    @RelaxedMockK
    private lateinit var firstStartUseCase: FirstStartUseCase

    @BeforeEach
    fun setUp() {
        viewModel = SplashViewModel(
            firstStartUseCase = firstStartUseCase
        )
    }

    @Test
    fun `should run execute after call checkDataExist`() = runBlocking {
        coEvery { firstStartUseCase.execute() } returns Unit
        viewModel.checkDataExist()
        coVerify(exactly = 1) {
            firstStartUseCase.execute()
        }
    }

    @Test
    fun `should set Loaded after success call execute`() = runBlocking {
        coEvery { firstStartUseCase.execute() } returns Unit
        viewModel.checkDataExist()
        viewModel.state.test {
            val emission = awaitItem()
            assertEquals(SplashViewModel.UiState.Loaded, emission)
        }
    }
}