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

        // Titik indikator kecil di kiri atas
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 16.dp, top = 12.dp)
        ) {
            repeat(4) { i ->
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(6.dp)
                        .background(
                            if (i == 0) Color(0xFF2196F3) else Color(0x99FFFFFF),
                            CircleShape
                        )
                )
            }
        }

        // Kartu form
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp)
                .shadow(8.dp, cardShape)
                .background(Color.White, cardShape)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "Form Biodata",
                color = PurpleTitle,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Silahkan isi biodata Anda dibawah ini !!!",
                color = Color(0xFF757575),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(28.dp))

            // Nama depan & belakang
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Nama Depan") },
                    singleLine = true,
                    shape = fieldShape,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Nama Belakang") },
                    singleLine = true,
                    shape = fieldShape,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                shape = fieldShape,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Tgl lahir & Experience
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = birth,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tgl Lahir") },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = "Pilih tanggal"
                            )
                        },
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Lapisan transparan agar seluruh field bisa diklik
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { showDatePicker = true }
                    )
                }

                ExposedDropdownMenuBox(
                    expanded = expandedExp,
                    onExpandedChange = { expandedExp = it },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = experience,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Experience") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedExp) },
                        singleLine = true,
                        shape = fieldShape,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedExp,
                        onDismissRequest = { expandedExp = false }
                    ) {
                        experienceList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    experience = item
                                    expandedExp = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Skill
            Text(
                text = "Pilih Skill Anda :",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF212121)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SkillCheck("Android", skillAndroid, { skillAndroid = it }, Modifier.weight(1f))
                SkillCheck("UI/UX", skillUiux, { skillUiux = it }, Modifier.weight(1f))
                SkillCheck("Web", skillWeb, { skillWeb = it }, Modifier.weight(1f))
            }

            Spacer(Modifier.height(24.dp))

            // Tombol Proceed
            Button(
                onClick = { showResult = true },
                enabled = isValid,
                shape = fieldShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleTitle,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE0E0E0),
                    disabledContentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Proceed", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(24.dp))
        }
    }

    // Date picker
    if (showDatePicker) {
        val dateState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dateState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        sdf.timeZone = TimeZone.getTimeZone("UTC")
                        birth = sdf.format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = dateState)
        }
    }

    // Dialog hasil
    if (showResult) {
        val skills = listOfNotNull(
            "Android".takeIf { skillAndroid },
            "UI/UX".takeIf { skillUiux },
            "Web".takeIf { skillWeb }
        ).joinToString(", ")

        AlertDialog(
            onDismissRequest = { showResult = false },
            title = { Text("Data Biodata") },
            text = {
                Text(
                    "Nama      : $firstName $lastName\n" +
                            "Email     : $email\n" +
                            "Tgl Lahir : $birth\n" +
                            "Experience: $experience\n" +
                            "Skill     : $skills"
                )
            },
            confirmButton = {
                TextButton(onClick = { showResult = false }) { Text("OK") }
            }
        )
    }
}
