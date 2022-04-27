package com.thoughtworks.lonestarcafe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thoughtworks.lonestarcafe.theme.LoneStarCafeTheme

@Composable
fun MenuItem(menuItemUiState: MenuItemUiState) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colors.surface)
            .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = false, onCheckedChange = {})
                Text(
                    text = menuItemUiState.description,
                    color = contentColorFor(backgroundColor = MaterialTheme.colors.surface),
                    fontSize = 16.sp
                )
            }
            Text(
                text = formatPrice(menuItemUiState.price),
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun formatPrice(priceInCent: Int): String {
     val price = priceInCent / 100.0
    return "$$price"
}


@Composable
@Preview
fun MenuItemPreview() {
    LoneStarCafeTheme {
        MenuItem(MenuItemUiState(id = "1", description = "Menu Item", price = 499))
    }
}