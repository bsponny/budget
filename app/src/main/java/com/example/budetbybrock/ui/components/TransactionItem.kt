package com.example.budetbybrock.ui.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.budetbybrock.ui.models.Transaction

enum class SwipeState{
    OPEN, CLOSED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionItem (
    transaction: Transaction,
    onEditPressed: () -> Unit = {},
    onDeletePressed: () -> Unit = {}
){
    val swipeableState = rememberSwipeableState(initialValue = SwipeState.CLOSED)
    val anchors = mapOf(0f to SwipeState.CLOSED, -200f to SwipeState.OPEN)
    Box(modifier = Modifier
        .fillMaxWidth()
        .swipeable(
            state = swipeableState,
            anchors = anchors,
            orientation = Orientation.Horizontal
        )
    ){
        Row(modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onDeletePressed,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.5f),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
        Surface(
            modifier = Modifier.offset{ IntOffset(swipeableState.offset.value.toInt(), 0)},
            elevation = 2.dp,
            shape = RoundedCornerShape(4.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = when (transaction.type){
                            Transaction.INCOME -> "Income"
                            Transaction.EXPENSE -> "Expense"
                            else -> ""
                        },
                        style = MaterialTheme.typography.h6)
                    Text(text = transaction.date ?: "", style = MaterialTheme.typography.h6)
                    IconButton(onClick = onEditPressed) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
                    }
                }
                Row(
                    modifier = Modifier.padding(16.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "\$${"%.2f".format(transaction.amount)}",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Row(
                    modifier = Modifier.padding(16.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = transaction.description ?: "")
                }
            }
        }
    }
}