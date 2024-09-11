package com.example.bitcoincodechallenge.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.bitcoincodechallenge.BuildConfig
import com.example.bitcoincodechallenge.data.InternetResponse
import com.example.bitcoincodechallenge.data.NodeModel
import com.example.bitcoincodechallenge.data.NodesInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NodesViewModel(
    private val nodesApi: NodesInterface,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val apiKey = BuildConfig.API_KEY

    private val _nodesLiveData = MutableLiveData<InternetResponse<List<NodeModel>>>()
    val nodesLiveData: LiveData<InternetResponse<List<NodeModel>>> = _nodesLiveData

    fun getNodes() {
        _nodesLiveData.value = InternetResponse.Loading

        viewModelScope.launch(
            dispatcher
        ) {
            try {
                val response =
                    nodesApi.getData(apiKey)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _nodesLiveData.postValue(InternetResponse.Success(it))
                    } ?: run {
                        _nodesLiveData.postValue(InternetResponse.Error("Resposta vazia"))
                    }
                }
                else {
                    _nodesLiveData.postValue(InternetResponse.Error("Erro ao carregar: ${response.message()}"))

                }
            } catch (e: Exception) {
                _nodesLiveData.postValue(InternetResponse.Error("Erro ao carregar"))
            }
        }
    }

    companion object {
        fun factory(
            nodesApi: NodesInterface,
            dispatcher: CoroutineDispatcher = Dispatchers.IO
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                return NodesViewModel(nodesApi,dispatcher) as T
            }

        }

    }
}
