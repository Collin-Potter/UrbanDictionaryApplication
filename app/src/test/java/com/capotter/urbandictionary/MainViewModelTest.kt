package com.capotter.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.capotter.urbandictionary.viewmodel.MainViewModel

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mainViewModel: MainViewModel

    /**
     * Verify successful term updates on method 'updateTerm' call
     */
    @Test
    fun `ViewModel term successfully updates`() {
        mainViewModel = MainViewModel("old", "thumbs_up")
        mainViewModel.updateTerm("new", "thumbs_up")
        assertEquals(mainViewModel.word, "new")
    }

    /**
     * Verify successful order updates to 'thumbs_up' on method 'updateTerm' call
     */
    @Test
    fun `ViewModel order successfully updates to thumbs_up`() {
        mainViewModel = MainViewModel("old", "thumbs_down")
        mainViewModel.updateTerm("old", "thumbs_up")
        assertEquals(mainViewModel.order, "thumbs_up")
    }

    /**
     * Verify successful order updates to 'thumbs_down' on method 'updateTerm' call
     */
    @Test
    fun `ViewModel order successfully updates to thumbs_down`() {
        mainViewModel = MainViewModel("old", "thumbs_up")
        mainViewModel.updateTerm("old", "thumbs_down")
        assertEquals(mainViewModel.order, "thumbs_down")
    }
}