package com.tl.mineminds.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.app.mineminds.R

interface QuizScreenInteraction {

}

@Composable
fun SampleQuizScreen() {

    val list =
        QuizData(
            qId = 1,
            qTitle = "Algebra",
            qDescription = "The magnitude of vector a = 3i - 2j + 6k is:",
            qImageUrl = "",
            qOptionList = listOf(
                OptionData(
                    optionId = 11,
                    optionName = "7"
                ),
                OptionData(
                    optionId = 12,
                    optionName = "-7"
                ),
                OptionData(
                    optionId = 13,
                    optionName = "11"
                ),
                OptionData(
                    optionId = 13,
                    optionName = "-11"
                )
            ),
        )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        QuizDataCard(list, itemClicked = {
                  //todo replace with actual item
        })

    }
}

@Composable
fun QuizDataCard(quizData: QuizData, itemClicked: () -> Unit) {
    val state = rememberScrollState()
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = quizData.qId.toString(), style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = quizData.qTitle,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth())
        }

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(quizData.qImageUrl)
                .error(R.drawable.algebra)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )

        Image(
            painter = painter,
            contentDescription = "qImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )


        Text(
            text = quizData.qDescription,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth().padding(8.dp),
        )

        OptionList(quizData.qOptionList, onOptionClicked = {

        })
    }
}

@Composable
fun OptionList(list: List<OptionData>, onOptionClicked: (Int) -> Unit) {
    val mRememberObserver = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        list.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioItem(mRememberObserver, item, onOptionClicked)
            }
        }
    }
}

@Composable
fun RadioItem(
    mRememberObserver: MutableState<String>,
    item: OptionData,
    onOptionClicked: (Int) -> Unit
) {
    RadioButton(
        selected = mRememberObserver.value == item.optionId.toString(),
        onClick = {
            mRememberObserver.value = item.optionId.toString()
            onOptionClicked(item.optionId)
        },
        colors = RadioButtonDefaults.colors(
            selectedColor = Color.Magenta
        )
    )
    Text(text = item.optionName, modifier = Modifier.padding(start = 8.dp))
}
