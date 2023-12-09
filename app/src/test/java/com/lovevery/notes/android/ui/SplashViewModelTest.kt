package com.lovevery.notes.android.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lovevery.notes.android.data.repository.NotesRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule

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
}
