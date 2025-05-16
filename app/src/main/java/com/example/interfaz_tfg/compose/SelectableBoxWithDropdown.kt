package com.example.interfaz_tfg.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableBoxWithDropdown(
    options: List<String>,
    currentGoal: String,
    onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    Box(modifier = Modifier
        .padding(top = 10.dp),
        contentAlignment = Alignment.TopEnd
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .padding(top = 10.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .menuAnchor()
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White)
                    .height(50.dp)
                    .clickable { expanded = true }

            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Objetivo: ",
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = selectedOption.ifEmpty { currentGoal },
                        color = Color.Black
                    )
                    Spacer(Modifier.weight(2f))
                    Icon(
                        imageVector = if (!expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = "",
                        Modifier.size(40.dp).padding(end = 5.dp)
                    )
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOption = selectionOption
                            expanded = false
                            onOptionSelected(selectionOption)
                        }
                    )
                }
            }
        }
    }
}