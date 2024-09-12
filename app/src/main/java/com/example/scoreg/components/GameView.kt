package com.example.scoreg.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.scoreg.database.entities.Game
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun GameView(
    game: Game,
    modifier: Modifier = Modifier
) {
    val scoreColor = when {
        game.score <= 50 -> Color(0xFFFF6874)
        game.score in 60..75 -> Color(0xFFFFBD3F)
        game.score in 76..100 -> Color(0xFF25F396)
        else -> Color.Gray
    }

    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = game.urlImage),
            contentDescription = game.title,
            modifier = Modifier
                .width(185.dp)
                .height(243.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp)
        ) {
            Text(
                text = game.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ano: ${game.releaseYear}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(scoreColor, RoundedCornerShape(4.dp))
                    .align(Alignment.Start),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${game.score}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                fontSize = 12.sp,
                text = game.description ?: "Sem descrição disponível.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify
            )
        }
    }
}
