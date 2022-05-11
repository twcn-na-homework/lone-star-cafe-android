package com.thoughtworks.lonestarcafe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.thoughtworks.lonestarcafe.R
import com.thoughtworks.lonestarcafe.theme.LoneStarCafeTheme

@Composable
fun BottomOrderSummaryBar(selectedItemCount: Int) {
    val resources = LocalContext.current.resources
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.list_item_padding)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = resources.getQuantityString(R.plurals.number_selected_items, selectedItemCount, selectedItemCount),
            color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.secondary),
                disabledBackgroundColor = MaterialTheme.colors.secondaryVariant,
            ),
            onClick = { /*TODO*/ },
            enabled = selectedItemCount > 0
        ) {
            Text(text = stringResource(id = R.string.submit_order))
        }
    }
}

@Composable
@Preview
fun BottomOrderSummaryBarPreview() {
    LoneStarCafeTheme {
        BottomOrderSummaryBar(3)
    }
}