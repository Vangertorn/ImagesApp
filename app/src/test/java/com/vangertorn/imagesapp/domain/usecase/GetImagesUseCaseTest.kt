package com.vangertorn.imagesapp.domain.usecase

import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetImagesUseCaseTest {

    private lateinit var useCase: GetImagesUseCase

    @MockK
    private lateinit var imageRepository: ImageRepository

    val expected = List(3) { index ->
        ImageModel(
            id = index.toString(),
            url = "testUrl",
            isFavorite = false,
            width = "10",
            height = "10"
        )
    }

    @BeforeEach
    fun setUp() {
        useCase = GetImagesUseCase(
            imageRepository = imageRepository
        )
        coEvery { imageRepository.getImagesFromLocal() } returns expected
    }

    @Test
    fun `should return the same data as in repository`() = runTest {
        val actual = useCase.execute()
        assertEquals(
            expected, actual
        )
    }
}