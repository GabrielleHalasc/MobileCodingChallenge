package com.example.bitcoincodechallenge.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bitcoincodechallenge.data.InternetResponse
import com.example.bitcoincodechallenge.data.NodeModel
import com.example.bitcoincodechallenge.presentation.NodesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BitcoinScreen(viewModel: NodesViewModel) {

    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    var selectedNode by remember { mutableStateOf<NodeModel?>(null) }
    val nodesLiveData = viewModel.nodesLiveData.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getNodes()
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(
                        "Bitcoin Nodes",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF8A2BE2))
            )
            Spacer(modifier = Modifier.height(8.dp))

            when (val result = nodesLiveData.value) {
                is InternetResponse.Error -> {
                    Text(text = result.message)
                }

                InternetResponse.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                is InternetResponse.Success -> {
                    if (selectedNode == null) {
                        val currentIsRefreshing by rememberUpdatedState(isRefreshing)
                        PullRefreshLazyColumn(
                            items = result.data,
                            content = { node ->
                                NodesCardView(node) {
                                    selectedNode = it
                                }
                            },
                            isRefreshing = currentIsRefreshing,
                            onRefresh = {
                                scope.launch {
                                    isRefreshing = true
                                    viewModel.getNodes()
                                    delay(1000L)
                                    isRefreshing = false
                                }
                            }
                        )
                    } else {
                        NodeDetail(data = selectedNode!!) {
                            selectedNode = null
                        }
                    }
                }

                null -> {}
            }

        }
    }
}

@Composable
fun NodesCardView(data: NodeModel, onCardClick: (NodeModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                onCardClick(data)
            }
            .padding(8.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEBEBEB)
        )

    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Node -> ",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(text = data.alias)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Details"
            )
            Text(
                text = "Open Details",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}
