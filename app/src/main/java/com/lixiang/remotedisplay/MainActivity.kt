package com.lixiang.remotedisplay

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.lixiang.remotedisplay.ui.theme.LixiangTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LixiangTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF0B0C10)) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Left navigation
        LeftNav(modifier = Modifier.width(220.dp).fillMaxHeight())

        Spacer(modifier = Modifier.width(16.dp))

        // Main content
        Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
            TopBar()
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                // Large player
                Card(modifier = Modifier.weight(0.62f).fillMaxHeight(), shape = RoundedCornerShape(12.dp), backgroundColor = Color(0xFF0F1114)) {
                    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                        Text("YouTube — Preview", color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxSize()) {
                            WebPlayer(modifier = Modifier.fillMaxSize())
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Right column with system status and slave cards
                Column(modifier = Modifier.weight(0.38f).fillMaxHeight()) {
                    SystemStatusCard(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(12.dp))
                    SlaveCard(name = "USER 21473", role = "SLAVE", modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    SlaveCard(name = "USER 6174", role = "SLAVE", modifier = Modifier.fillMaxWidth())
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom panels
            Row(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                PlaylistPanel(modifier = Modifier.weight(0.33f))
                Spacer(modifier = Modifier.width(12.dp))
                SyncCenterPanel(modifier = Modifier.weight(0.34f))
                Spacer(modifier = Modifier.width(12.dp))
                SettingsPanel(modifier = Modifier.weight(0.33f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Cinema player bar
            CinemaBar()
        }
    }
}

@Composable
fun LeftNav(modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(Color(0xFF0E0F13), shape = RoundedCornerShape(12.dp)).padding(12.dp)) {
        Text("LIXIANG", color = Color(0xFFFFD77A), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        val items = listOf("Video", "Displays", "Sync Center", "Playlists", "Settings", "About")
        for (it in items) {
            Text(it, color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        Text("LIXIANG L9 ULTRA", color = Color.LightGray)
    }
}

@Composable
fun TopBar() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text("22:48", color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
        Text("3 / 3 ONLINE", color = Color(0xFF7BF57B), fontWeight = FontWeight.Bold)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebPlayer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var url by remember { mutableStateOf("https://www.youtube.com/embed/0N6b5FQK0Y4?rel=0&autoplay=0") }

    AndroidView(factory = {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            loadUrl(url)
        }
    }, modifier = modifier)
}

@Composable
fun SystemStatusCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(12.dp), backgroundColor = Color(0xFF0E1114)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("СОСТОЯНИЕ СИСТЕМЫ", color = Color(0xFF9AA0A6), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("3 / 3", color = Color(0xFF7BF57B), fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(8.dp))
            // Displays list
            val displays = listOf(
                Triple("DISPLAY 01 (MASTER)", "USER 0", true),
                Triple("DISPLAY 02 (SLAVE)", "USER 21473", true),
                Triple("DISPLAY 03 (SLAVE)", "USER 6174", true),
            )
            for (d in displays) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(d.first, color = Color.White)
                        Text(d.second, color = Color.Gray, style = MaterialTheme.typography.caption)
                    }
                    Text(if (d.third) "ONLINE" else "OFFLINE", color = if (d.third) Color(0xFF7BF57B) else Color.Red)
                }
            }
        }
    }
}

@Composable
fun SlaveCard(name: String, role: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(12.dp), backgroundColor = Color(0xFF0E1114)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(name, color = Color.White)
                    Text(role, color = Color(0xFF9AA0A6))
                }
                Text("00:42 / 12:35", color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(progress = 0.2f, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Управление заблокировано", color = Color(0xFF9AA0A6))
                Row {
                    IconButton(onClick = {}) { Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Pause, contentDescription = null, tint = Color.White) }
                }
            }
        }
    }
}

@Composable
fun PlaylistPanel(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxHeight(), shape = RoundedCornerShape(12.dp), backgroundColor = Color(0xFF0E1114)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("ПОИСК В YOUTUBE", color = Color(0xFF9AA0A6), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Рекомендации", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            // Mock list
            for (i in 1..3) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Box(modifier = Modifier.size(64.dp).background(Color.DarkGray))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Video title $i", color = Color.White)
                        Text("4K • 12:35", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun SyncCenterPanel(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxHeight(), shape = RoundedCornerShape(12.dp), backgroundColor = Color(0xFF0E1114)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("SYNC CENTER", color = Color(0xFF9AA0A6), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            // Placeholder circular sync visualization
            Box(modifier = Modifier.size(120.dp).background(Color(0xFF111215), shape = RoundedCornerShape(60.dp)), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SYNC", color = Color(0xFFFFD77A), fontWeight = FontWeight.Bold)
                    Text("99.8%", color = Color(0xFF7BF57B), fontWeight = FontWeight.ExtraBold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Displays:", color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Display 01 — Master\nDisplay 02 — Slave\nDisplay 03 — Slave", color = Color.Gray)
        }
    }
}

@Composable
fun SettingsPanel(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxHeight(), shape = RoundedCornerShape(12.dp), backgroundColor = Color(0xFF0E1114)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("НАСТРОЙКИ", color = Color(0xFF9AA0A6), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Качество видео: Авто (1080p)", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Язык: Русский", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Тема: Тёмная (Lux)", color = Color.White)
        }
    }
}

@Composable
fun CinemaBar() {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), backgroundColor = Color(0xFF070708)) {
        Row(modifier = Modifier.fillMaxWidth().height(64.dp).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(Color.DarkGray))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Night Drive — City Lights", color = Color.White)
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(progress = 0.2f, modifier = Modifier.fillMaxWidth())
            }
            IconButton(onClick = {}) { Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White) }
        }
    }
}
