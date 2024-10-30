package com.bajiguri.bandrek.AppScreen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bajiguri.bandrek.AppScreen.AppInfo
import com.bajiguri.bandrek.R

@Composable
fun AppSearchView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier.padding(10.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            placeholder = {
                Text(stringResource(R.string.search))
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )
    }

}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
fun PreviewAppSearchView() {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Box(modifier = Modifier
                .weight(1f)
                .background(Color.Cyan)) {

            }
            AppSearchView()
        }
    }


}