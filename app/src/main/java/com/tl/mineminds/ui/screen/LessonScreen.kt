package com.tl.mineminds.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tl.mineminds.entity.LessonItem

interface LessonScreenInteraction {
    fun onLessonSelected(lessonIem: LessonItem)
}

@Composable
fun LessonScreen(lessonItems: List<LessonItem>, interaction: LessonScreenInteraction) {
    val list = listOf(
        LessonItem(1, "Addition", 1),
        LessonItem(2, "Subtraction", 1),
        LessonItem(3, "Multiplication", 1),
        LessonItem(4, "Division", 1),
        LessonItem(5, "Decimal Number", 1),
        LessonItem(6, "Numeric values", 1),
        LessonItem(7, "Algebra", 1),
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn() {
            items(list.size) { index ->
                LessonItemCard(list[index].id, list[index].lessonName, itemClicked = {
                    interaction.onLessonSelected(list[index]) //todo replace with actual item
                })
            }
        }
    }
}


@Composable
fun LessonItemCard(num: Int, lessonName: String, itemClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                itemClicked()
            },
        shape = RoundedCornerShape(8.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = num.toString(),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = lessonName,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                ),
            )
        }
    }
}
