package com.vangertorn.imagesapp.domain.usecase

import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class GetFavoriteImagesUseCaseTest  {

    private lateinit var useCase: GetFavoriteImagesUseCase

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
        useCase = GetFavoriteImagesUseCase(
            imageRepository = imageRepository
        )
        coEvery { imageRepository.getFavoriteImages() } returns expected
    }

    @Test
    fun `should return the same favorite data as in repository`() = runTest {
        val actual = useCase.execute()
        assertEquals(
            expected, actual
        )
    }
}