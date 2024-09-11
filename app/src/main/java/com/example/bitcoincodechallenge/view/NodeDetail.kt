package com.example.bitcoincodechallenge.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bitcoincodechallenge.data.NodeModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NodeDetail(data: NodeModel, onBackClick: () -> Unit) {
    val capacityInBTC = data.capacity / 100_000_000.0
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    val firstSeenDate = Date(data.firstSeen * 1000L)
    val updatedAtDate = Date(data.updatedAt * 1000L)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.alias,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Public Key:")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.publicKey, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Column {

            Text(
                text = "Channels: ",
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = "${data.channels}",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Capacity: ",
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = " %.3f BTC".format(capacityInBTC),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "First Seen: ",
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = dateFormat.format(firstSeenDate),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Last Updated: ",
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = dateFormat.format(updatedAtDate),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Location: ",
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = "${data.city?.name ?: "Unknown"}, ${data.country.name}",
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray, thickness = 1.dp)

        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8A2BE2)
            )
        ) {
            Text("Back to List")
        }
    }
}