package com.lovevery.notes.android.ui.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lovevery.notes.android.data.repository.NotesRepository
import com.lovevery.notes.android.data.repository.SessionRepository
import com.lovevery.notes.android.data.repository.model.NoteModel
import com.lovevery.notes.android.ui.PostNoteState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NotesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var mockNotesRepository: NotesRepository

    @RelaxedMockK
    private lateinit var mockSessionRepository: SessionRepository

    private lateinit var systemUnderTest: NotesViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        systemUnderTest = NotesViewModel(
            notesRepository = mockNotesRepository,
            sessionRepository = mockSessionRepository
        )
    }

    @Test
    fun `test postNote returns a success response`() {
        // given
        val testSubject = "testSubject"
        val testNoteContent = "testContent"
        val mockNoteModel: NoteModel = NoteModel(
            testSubject,
            testNoteContent
        )
        every { mockSessionRepository.getSubject() } returns testSubject
        every {
            mockNotesRepository.addNote(testSubject, testNoteContent)
        } returns Single.just(mockNoteModel)

        val postNoteStateObserver: Observer<PostNoteState> = mockk(relaxed = true)
        systemUnderTest.postNoteState.observeForever(postNoteStateObserver)

        // when
        systemUnderTest.postNote(testNoteContent)

        // verify
        verify { mockSessionRepository.getSubject() }
        verify { mockNotesRepository.addNote(testSubject, testNoteContent) }
        verify { postNoteStateObserver.onChanged(ofType(PostNoteState.Success::class)) }
    }

    @Test
    fun `test postNote returns an error response`() {
        // given
        val testSubject = "testSubject"
        val testNoteContent = "testContent"

        val error = Throwable("An error occurred")
        every { mockSessionRepository.getSubject() } returns testSubject
        every { mockNotesRepository.addNote(testSubject, testNoteContent) } returns Single.error(error)

        val postNoteStateObserver: Observer<PostNoteState> = mockk(relaxed = true)
        systemUnderTest.postNoteState.observeForever(postNoteStateObserver)

        // when
        systemUnderTest.postNote(testNoteContent)

        // verify
        verify { mockSessionRepository.getSubject() }
        verify { mockNotesRepository.addNote(testSubject, testNoteContent) }
        verify { postNoteStateObserver.onChanged(PostNoteState.Error(error)) }
    }
}