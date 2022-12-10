package com.example.budetbybrock.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.budetbybrock.ui.models.Transaction

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BudgetItem(
    type: Int,
    amount: Double,
    onClick: () -> Unit = {},
    clickable: Boolean = false
) {
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp),
        onClick = onClick
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = when (type) {
                        Transaction.INCOME -> "Total Income: "
                        Transaction.EXPENSE -> "Total Expenses: "
                        else -> "Difference: "
                    },
                    style = MaterialTheme.typography.h5
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 0.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "\$${"%.2f".format(amount)}", style = MaterialTheme.typography.h5)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (clickable) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Arrow Icon"
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(160.dp))
}