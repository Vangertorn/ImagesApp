package com.vangertorn.imagesapp.domain.usecase

import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class FirstStartUseCaseTest {

    private lateinit var useCase: FirstStartUseCase

    @MockK
    private lateinit var imageRepository: ImageRepository

    private val expected = List(3) { index ->
        ImageModel(
            id = index.toString(),
            url = "testUrl",
            isFavorite = true,
            width = "10",
            height = "10"
        )
    }

    @BeforeEach
    fun setUp() {
        useCase = FirstStartUseCase(
            imageRepository = imageRepository
        )
    }

    @Test
    fun `skip update database if exist data`()  = runTest{
        coEvery { imageRepository.getImagesFromLocal() } returns expected
        useCase.execute()
        coVerify(exactly = 0) {
            imageRepository.getImagesFromNetwork()
        }

    }

    @Test
    fun `run update database if it does not have data`() = runTest {
        coEvery { imageRepository.getImagesFromLocal() } returns listOf()
        coEvery { imageRepository.getImagesFromNetwork() } returns listOf()
        useCase.execute()
        coVerify(exactly = 1) {
            imageRepository.getImagesFromNetwork()
        }
    }

    @Test
    fun `skip save in database empty list`() = runTest {
        coEvery { imageRepository.getImagesFromLocal() } returns listOf()
        coEvery { imageRepository.getImagesFromNetwork() } returns listOf()
        useCase.execute()
        coVerify(exactly = 0) {
            imageRepository.saveImagesDatabase(any())
        }
    }

    @Test
    fun `run save in database if list from network contains data`() = runTest {
        coEvery { imageRepository.getImagesFromLocal() } returns listOf()
        coEvery { imageRepository.getImagesFromNetwork() } returns expected
        coEvery { imageRepository.saveImagesDatabase(any()) } returns Unit
        useCase.execute()
        coVerify(exactly = 1) {
            imageRepository.saveImagesDatabase(any())
        }
    }
}