package com.example.w04

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.w04.ui.theme.DksekrudTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DksekrudTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen()
                }
            }
        }
    }
}

data class Message(val name: String, val msg: String)
data class Profile(val name: String, val intro: String)

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            profilecard(Profile("이종원", "안드로이드 앱 개발자"))
            Spacer(modifier = Modifier.height(16.dp))
            messagecard(Message("안드로이드", "Jetpack Compose"))
        }
    }
}

@Composable
fun profilecard(data: Profile) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = data.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = data.intro,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun messagecard(me: Message) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = me.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = me.msg,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(
    name = "프로필 카드 미리보기",
    showBackground = true
)
@Composable
fun PreviewProfileCard() {
    DksekrudTheme {
        profilecard(Profile("이종원", "안드로이드 앱 개발자"))
    }
}

@Preview(
    name = "메시지 카드 미리보기",
    showBackground = true
)
@Composable
fun PreviewMessageCard() {
    DksekrudTheme {
        messagecard(Message("안드로이드", "Jetpack Compose"))
    }
}
