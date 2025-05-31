package com.example.interfaz_tfg.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Card(
    title: String,
    emojis: Map<String, EmojiItem>,
    selectedLabels: List<String?>,
    onSelectionChange: (MutableList<String?>) -> Unit,
    color: Color = Color.LightGray
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color = color, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        val updatedSelection = remember { mutableStateOf(selectedLabels.toMutableList()) }

        emojis.forEach { (label, emojiItem) ->
            val isSelected = updatedSelection.value.contains(label)

            Text(
                text = "${emojiItem.emoji} ${emojiItem.label}",
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        if (isSelected) Color.Gray else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(8.dp)
                    .clickable {
                        val newSelection = updatedSelection.value.toMutableList()
                        if (isSelected) {
                            newSelection.remove(label)
                        } else {
                            newSelection.add(label)
                        }
                        updatedSelection.value = newSelection
                        onSelectionChange( newSelection)
                    }
            )
        }
    }
}
