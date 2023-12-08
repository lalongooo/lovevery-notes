package com.lovevery.notes.android.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lovevery.notes.android.data.repository.NotesRepository
import com.lovevery.notes.android.data.repository.model.NoteModel
import com.lovevery.notes.android.data.repository.model.NotesModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var notesRepository: NotesRepository

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SplashViewModel(notesRepository)
    }

    @Test
    fun `test getNotes returns success`() {
        // given
        val testUser = "user123"
        val testSubject = "testSubject"
        val testContent = "testContent"
        val mockNotes = NotesModel(
            notes = mapOf(testUser to listOf(NoteModel(
                subject = testSubject,
                content = testContent,
            )))
        )
        val notesStateObserver: Observer<NotesState> = mockk(relaxed = true)
        viewModel.notesState.observeForever(notesStateObserver)

        every { notesRepository.getNotes() } returns Single.just(mockNotes)

        // when
        viewModel.getNotes()

        // verify
        verify { notesRepository.getNotes() }
        verify { notesStateObserver.onChanged(ofType(NotesState.Success::class)) }
    }

    @Test
    fun `test getNotes returns an empty response`() {
        // given
        val mockNotes = NotesModel(emptyMap())
        val notesStateObserver: Observer<NotesState> = mockk(relaxed = true)
        viewModel.notesState.observeForever(notesStateObserver)

        every { notesRepository.getNotes() } returns Single.just(mockNotes)

        // when
        viewModel.getNotes()

        // verify
        verify { notesRepository.getNotes() }
        verify { notesStateObserver.onChanged(ofType(NotesState.Empty::class)) }
    }

    @Test
    fun `test getNotes results in an error state`() {
        // Arrange
        val error = Throwable("An error occurred")
        val observer = mockk<Observer<NotesState>>()
        viewModel.notesState.observeForever(observer)

        every { notesRepository.getNotes() } returns Single.error(error)

        // Act
        viewModel.getNotes()

        // Assert
        verify { notesRepository.getNotes() }
        verify { observer.onChanged(NotesState.Error(error)) }
    }
}
