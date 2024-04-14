package com.example.aiqrcode.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aiqrcode.ui.SetupParams

@Composable
fun SetupScreen(
    params: SetupParams,
    updateParams: (SetupParams) -> Unit = {},
    sendRequest: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        ParamsList(params, updateParams)
        GenerateButton(sendRequest)
    }
}

@Composable
private fun ParamsList(
    params: SetupParams,
    updateParams: (SetupParams) -> Unit = {},
) {
    Column {
        ParamTextField(params.prompt, "Prompt") { updateParams(params.copy(prompt = it)) }
    }
}

@Composable
private fun ParamTextField(
    value: String,
    label: String,
    updateValue: (String) -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = updateValue,
        label = { Text(label) },
        minLines = 3,
        maxLines = 6,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun GenerateButton(onClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Text("Generate")
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SetupScreenPreview() {
    SetupScreen(params = SetupParams())
}