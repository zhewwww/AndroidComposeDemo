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
import androidx.compose.ui.graphics.Color
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
    var ipAddress by remember { mutableStateOf(TextFieldValue("192.168.43.56")) }
    var port by remember { mutableStateOf(TextFieldValue("80")) }
    var connectionStatus by remember { mutableStateOf("未连接") }
    var socket by remember { mutableStateOf<Socket?>(null) }
    val scope = rememberCoroutineScope()
    var selectedOption by remember { mutableStateOf("Connect") }
    val options = listOf("Connect", "Message")

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
            // 自定义带槽效果的 Segmented Button
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(35.dp),
                color = IOSStyles.SecondaryTextColor, // 背景色为半透明淡灰色
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), // 内部 padding 创建槽的深度感
                    horizontalArrangement = Arrangement.SpaceEvenly // 按钮均匀分布
                ) {
                    options.forEach { label ->
                        Button(
                            onClick = { selectedOption = label },
                            modifier = Modifier
                                .weight(1f) // 每个按钮等宽
                                .padding(horizontal = 4.dp) // 按钮之间的间隔
                                .height(34.dp), // 控制按钮高度
                            shape = RoundedCornerShape(20.dp), // 保持圆角矩形
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = if (selectedOption == label)
                                    IOSStyles.BackgroundColor
                                else IOSStyles.SecondaryTextColor,
                                contentColor = if (selectedOption == label)
                                    Color.Black
                                else MaterialTheme.colorScheme.onSurface
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = if (selectedOption == label) 8.dp else 0.dp
                            )
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelLarge

                            )
                        }
                    }
                }
            }
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
                    shape = RoundedCornerShape(20.dp),
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
                    shape = RoundedCornerShape(20.dp),
                    colors = IOSStyles.buttonColors(),
                ) {
                    Text("断开", style = IOSStyles.buttonTextStyle())
                }
            }
        }
    }
}