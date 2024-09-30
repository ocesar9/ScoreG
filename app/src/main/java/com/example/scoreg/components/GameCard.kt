package com.example.scoreg.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scoreg.database.entities.Game
import coil.compose.rememberImagePainter

@Composable
fun GameCard(
    game: Game,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    showScore: Boolean = false
) {
    Box(
        modifier = modifier
            .size(width = 136.dp, height = 171.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = rememberImagePainter(data = game.urlImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            if (showScore) {
                val scoreColor = when {
                    game.score <= 60 -> Color(0xFFFF6874)
                    game.score in 60..75 -> Color(0xFFFFBD3F)
                    game.score in 76..100 -> Color(0xFF25F396)
                    else -> Color.Gray
                }

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(scoreColor, RoundedCornerShape(4.dp))
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${game.score}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 10.sp,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = game.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = Color.White,
                            lineHeight = 12.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
