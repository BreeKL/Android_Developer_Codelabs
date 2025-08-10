// A simple app that uses events to change the visible objects in the UI

package com.example.lemonade

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

// Data structure for state of lemonade, containing respective image and text resources
enum class LemonadeState(val imageRes: Int, val textRes: Int) {
    SELECT(R.drawable.lemon_tree, R.string.select_lemon),
    SQUEEZE(R.drawable.lemon_squeeze, R.string.lemon_squeeze),
    DRINK(R.drawable.lemon_drink, R.string.lemon_drink),
    RESTART(R.drawable.lemon_restart, R.string.lemon_restart);

    fun next(): LemonadeState = when(this) {
        SELECT -> SQUEEZE
        SQUEEZE -> DRINK
        DRINK -> RESTART
        RESTART -> SELECT
    }
}

// Composable function for the main content of the app
@Composable
fun LemonadeImageAndButton(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(LemonadeState.SELECT) }
    var lemonSqueezes by remember { mutableStateOf(0) }

    // Header
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFEA9D)),
        contentAlignment = Alignment.TopCenter

    ) {
        Text(
            text = (stringResource(id = R.string.app_name)),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )
    }

    // Main content
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Clickable image that iterates through the lemonade states
        Button(
            onClick = {
                Log.d("State", "Current state: $state")

                when (state) {
                    // If the current state is SELECT, change to SQUEEZE and generate a random
                    // number of squeezes between 2 and 4.
                    LemonadeState.SELECT -> {
                        state = state.next()
                        lemonSqueezes = (2..4).random()
                        Log.d("Lemon", "Random squeezes: $lemonSqueezes")
                    }
                    // If the current state is SQUEEZE, decrement the number of squeezes by 1.
                    // Otherwise, if finished squeezing, change to DRINK.
                    LemonadeState.SQUEEZE -> {
                        if (lemonSqueezes > 1) {
                            lemonSqueezes--
                            Log.d("Lemon", "Remaining squeezes: $lemonSqueezes")
                        } else {
                            state = state.next()
                        }
                    }
                    // Otherwise, move to the next state
                    LemonadeState.DRINK -> state = state.next()
                    LemonadeState.RESTART -> state = state.next()
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFFC3ECD2))
        ) {
            Image(
                // Match the image and description to the current state
                painter = painterResource(id = LemonadeState.valueOf(state.name).imageRes),
                contentDescription = stringResource(id = LemonadeState.valueOf(state.name).textRes),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        // Display the text for the current state
        Text(
            text = stringResource(id = LemonadeState.valueOf(state.name).textRes),
            fontSize = 18.sp
        )
    }
}

// Composable preview function
@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeTheme {
        LemonadeImageAndButton(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    }
}