// A simple app page to practice displaying text and images

package com.example.compose_tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_tutorial.ui.theme.Compose_TutorialTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose_TutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Tutorial(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Tutorial(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        Image(
            painterResource(R.drawable.bg_compose_background),
            contentDescription = null,
            //modifier = Modifier.fillMaxSize()
        )
        Text(
            text = stringResource(R.string.title),
            fontSize = 24.sp,
            modifier = modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = stringResource(R.string.paragraph_1),
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = stringResource(R.string.paragraph_2),
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun GreetingPreview() {
    Compose_TutorialTheme {
        Tutorial()
    }
}
