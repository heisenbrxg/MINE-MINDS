package com.tl.mineminds.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tl.mineminds.entity.Subject
import com.tl.mineminds.ui.component.SubjectItem
import com.tl.mineminds.ui.component.SubjectItemInteraction

@Composable
fun SubjectScreen(subjects: List<Subject>, interaction: SubjectItemInteraction) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.padding(8.dp).fillMaxSize(),
        state = rememberLazyGridState()
    ) {
        items(subjects, key = {it.id}) {
            SubjectItem(it, interaction)
        }
    }
}