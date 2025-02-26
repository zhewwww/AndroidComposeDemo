package com.example.socketconnector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.socketconnector.ui.style.IOSStyles
import java.net.Socket
import kotlinx.coroutines.*
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocketApp()
        }
    }
}

@Preview
@Composable
fun SocketApp() {
    var ipAddress by remember { mutableStateOf(TextFieldValue("192.168.1.1")) }
    var port by remember { mutableStateOf(TextFieldValue("8080")) }
    var connectionStatus by remember { mutableStateOf("未连接") }
    var socket by remember { mutableStateOf<Socket?>(null) }
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = IOSStyles.BackgroundColor
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SingleChoiceSegmentedButtonRow {
                // Handle selection change

            }
        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TextField(
                value = ipAddress,
                onValueChange = { ipAddress = it },
                label = { Text("IP地址") },
                modifier = IOSStyles.textFieldModifier(),
                colors = IOSStyles.textFieldColors(),
                shape = RoundedCornerShape(12.dp)
            )

            TextField(
                value = port,
                onValueChange = { port = it },
                label = { Text("端口号") },
                modifier = IOSStyles.textFieldModifier(),
                colors = IOSStyles.textFieldColors(),
                shape = RoundedCornerShape(12.dp)
            )

            Text(
                text = "状态: $connectionStatus",
                style = IOSStyles.statusTextStyle(),
                modifier = IOSStyles.statusModifier()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                ElevatedButton(
                    onClick = {
                        scope.launch {
                            try {
                                connectionStatus = "正在连接..."
                                val portNum = port.text.toInt()
                                socket = withContext(Dispatchers.IO) {
                                    Socket(ipAddress.text, portNum)
                                }
                                connectionStatus = "已连接"
                            } catch (e: Exception) {
                                connectionStatus = "连接失败: ${e.message}"
                                socket = null
                            }
                        }
                    },
                    enabled = socket == null,
                    modifier = IOSStyles.buttonModifier(),
                    shape = RoundedCornerShape(12.dp),
                    colors = IOSStyles.buttonColors(),
                ) {
                    Text("连接", style = IOSStyles.buttonTextStyle())
                }

                ElevatedButton(
                    onClick = {
                        scope.launch {
                            try {
                                socket?.close()
                                socket = null
                                connectionStatus = "未连接"
                            } catch (e: IOException) {
                                connectionStatus = "断开失败: ${e.message}"
                            }
                        }
                    },
                    enabled = socket != null,
                    modifier = IOSStyles.buttonModifier(),
                    shape = RoundedCornerShape(12.dp),
                    colors = IOSStyles.buttonColors(),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text("断开", style = IOSStyles.buttonTextStyle())
                }
            }
        }
    }
}