package com.example.bitcoincodechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.bitcoincodechallenge.data.RetrofitInstance.nodesApi
import com.example.bitcoincodechallenge.presentation.NodesViewModel
import com.example.bitcoincodechallenge.ui.theme.BitcoinCodeChallengeTheme
import com.example.bitcoincodechallenge.view.BitcoinScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<NodesViewModel> { NodesViewModel.factory(nodesApi) }

        setContent {
            BitcoinCodeChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BitcoinScreen(viewModel)
                }
            }
        }
    }
}
