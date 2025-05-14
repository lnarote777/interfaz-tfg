package com.example.interfaz_tfg.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfaz_tfg.R

@Composable
fun Card(
    title: String,
    emojis: Map<String, EmojiItem>,
    selectedLabels: List<String>,
    onSelectionChange: (List<String>) -> Unit,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .shadow(10.dp, RoundedCornerShape(30.dp))
            .border(3.dp, color, RoundedCornerShape(30.dp))
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            val chunkedEmojis = emojis.entries.chunked(3)
            chunkedEmojis.forEach { rowEmojis ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    rowEmojis.forEach { item ->
                        val isSelected = selectedLabels.contains(item.key)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) colorResource(R.color.botones2).copy(alpha = 0.4f) else Color.Transparent)
                                .clickable {
                                    val newSelection = if (isSelected) {
                                        selectedLabels - item.key
                                    } else {
                                        selectedLabels + item.key
                                    }
                                    onSelectionChange(newSelection)
                                }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = item.value.emoji,
                                fontSize = 26.sp,
                            )
                            Text(
                                text = item.key,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}