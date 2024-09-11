package com.example.bitcoincodechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.bitcoincodechallenge.data.InternetResponse
import com.example.bitcoincodechallenge.data.NodeModel
import com.example.bitcoincodechallenge.data.NodesInterface
import com.example.bitcoincodechallenge.presentation.NodesViewModel
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@OptIn(ExperimentalCoroutinesApi::class)
class NodeViewModelTest {

    private val apiKey = BuildConfig.API_KEY

    private val api: NodesInterface = mock()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val underTest by lazy {
        NodesViewModel(api, mainCoroutineRule.testDispatcher)
    }

    @Test
    fun `GIVEN any response WHEN getNodes THEN assert Loading`() {
        runTest {
            //Given
            whenever(
                api.getData(apiKey)
            ).thenReturn(Response.success(listOf()))
            //When
            underTest.getNodes()
            //Then
            Truth.assertThat(underTest.nodesLiveData.value)
                .isEqualTo(InternetResponse.Loading)
        }
    }


    @Test
    fun `GIVEN Success response empty List WHEN getNodes THEN assert Success with empty List`() {
        runTest {
            //Given
            whenever(
                api.getData(apiKey)
            ).thenReturn(Response.success(listOf()))
            //When
            underTest.getNodes()
            mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
            //Then
            Truth.assertThat(underTest.nodesLiveData.getOrAwaitValue( ))
                .isEqualTo(InternetResponse.Success(listOf<NodeModel>()))
        }
    }
    @Test
    fun `GIVEN unsuccessful response WHEN getNodes THEN assert Error with message`() {
        runTest {
            // Given
            val errorMessage = "Erro ao carregar: Response.error()"
            val errorResponse = Response.error<List<NodeModel>>(
                404, ResponseBody.create(null, ""))
            whenever(api.getData(apiKey)).thenReturn(errorResponse)
            // When
            underTest.getNodes()
            mainCoroutineRule.testDispatcher.scheduler.advanceUntilIdle()
            // Then
            Truth.assertThat(underTest.nodesLiveData.getOrAwaitValue())
                .isEqualTo(InternetResponse.Error(errorMessage))
        }
    }
}
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}