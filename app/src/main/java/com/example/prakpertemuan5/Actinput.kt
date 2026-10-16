package com.example.prakpertemuan5

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

val PurpleTitle = Color(0xFF673AB7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen() {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var birth by rememberSaveable { mutableStateOf("") }
    var experience by rememberSaveable { mutableStateOf("") }
    var skillAndroid by rememberSaveable { mutableStateOf(false) }
    var skillUiux by rememberSaveable { mutableStateOf(false) }
    var skillWeb by rememberSaveable { mutableStateOf(false) }

    var showDatePicker by remember { mutableStateOf(false) }
    var expandedExp by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }

    val experienceList = listOf("< 1 Tahun", "1 - 3 Tahun", "3 - 5 Tahun", "> 5 Tahun")
    val fieldShape = RoundedCornerShape(16.dp)
    val cardShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

    val isValid = firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() &&
            birth.isNotBlank() &&
            experience.isNotBlank() &&
            (skillAndroid || skillUiux || skillWeb)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Gambar latar (pdi.jpg dari drawable)
        Image(
            painter = painterResource(id = R.drawable.pdi),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

