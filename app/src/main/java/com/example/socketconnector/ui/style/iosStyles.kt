package com.example.socketconnector.ui.style

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object IOSStyles {
    // Colors
    val BackgroundColor = Color(0xFFF2F2F7)
    private val PrimaryColor = Color(0xFF007AFF)
    private val DisabledColor = Color(0xFFE5E5EA)
    private val SecondaryTextColor = Color(0xFF8E8E93)
    private val White = Color.White


    // Text Field Modifier
    fun textFieldModifier() = Modifier
        .fillMaxWidth()

    // Text Field Colors
    @Composable
    fun textFieldColors() = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = White,
        unfocusedContainerColor = White,
        cursorColor = PrimaryColor,
        focusedLabelColor = PrimaryColor
    )


    // Status Text Style
    @Composable
    fun statusTextStyle() = MaterialTheme.typography.bodyLarge.copy(
        fontWeight = FontWeight.Medium,
        color = White,
        fontSize = 17.sp
    )

    // Status Text Modifier
    fun statusModifier() = Modifier
        .background(SecondaryTextColor, RoundedCornerShape(12.dp))
        .padding(horizontal = 12.dp, vertical = 8.dp)


    // Button Modifier
    fun buttonModifier() = Modifier
        .height(50.dp)

    // Button Colors
    @Composable
    fun buttonColors() = ButtonDefaults.buttonColors(
        containerColor = PrimaryColor,
        disabledContainerColor = DisabledColor
    )

    // Button Text Style
    fun buttonTextStyle() = TextStyle(
        color = White,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    )
}