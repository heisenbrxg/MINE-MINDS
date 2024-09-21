package com.tl.mineminds.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tl.mineminds.R
import com.tl.mineminds.entity.Subject


interface SubjectItemInteraction {
    fun onSubjectItemSelected(subject: Subject)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SubjectItem(subject: Subject, interaction: SubjectItemInteraction) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).clickable {
                interaction.onSubjectItemSelected(subject)
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(painter = rememberImagePainter(R.drawable.login_illustration), contentDescription = "", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().height(80.dp))
            Text(text = subject.name, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}