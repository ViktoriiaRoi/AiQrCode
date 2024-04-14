package com.example.aiqrcode.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aiqrcode.ui.SetupErrors
import com.example.aiqrcode.ui.SetupParams

@Composable
fun SetupScreen(
    params: SetupParams = SetupParams(),
    errors: SetupErrors = SetupErrors(),
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
        ParamsList(params, errors, updateParams)
        GenerateButton(sendRequest)
    }
}

@Composable
private fun ParamsList(
    params: SetupParams,
    errors: SetupErrors,
    updateParams: (SetupParams) -> Unit = {},
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ParamTextField(params.website, errors.websiteError, "Website") {
            updateParams(params.copy(website = it))
        }
        ParamTextField(params.prompt, errors.promptError, "Prompt") {
            updateParams(params.copy(prompt = it))
        }
    }
}

@Composable
private fun ParamTextField(
    value: String,
    error: String?,
    label: String,
    updateValue: (String) -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = updateValue,
        label = { Text(label) },
        minLines = 3,
        maxLines = 6,
        isError = error != null,
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
            if (error != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
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
    SetupScreen()
}