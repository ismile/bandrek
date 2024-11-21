package com.bajiguri.bandrek.screen.appScreen.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bajiguri.bandrek.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay

@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
@Composable
fun AppSearchView(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
) {
    var textState by remember { mutableStateOf(value) }

    LaunchedEffect(key1 = textState) {
        // this check is optional if you want the value to emit from the start
        if (textState.isBlank()) return@LaunchedEffect

        delay(600)
        // print or emit to your viewmodel
        onValueChange(textState)
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            textState = ""
                            onValueChange("")
                        }
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(),
            shape = RoundedCornerShape(30.dp),
            placeholder = {
                Text(stringResource(R.string.search))
            },
            modifier = modifier
                .fillMaxWidth()
                .border(0.dp, Color.Transparent)
        )
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewAppSearchView() {
    AppSearchView(value = "adasd")
}