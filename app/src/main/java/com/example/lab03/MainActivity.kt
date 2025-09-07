package com.example.lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme()
            ) {
                FitnessScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessScreen() {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { MyTopAppBar() },
        floatingActionButton = { MyFloatingActionButton(snackbarHostState) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { MyIcon() }
            item { MyLazyColumn() }
            item { MyLazyRow() }
            item { MyFlowRow() }
            item { MyOutlinedTextField() }
            item { MyCheckbox() }
            item { MySlider() }
            item { MyAlertDialog() }
            item { MyProgressBar() }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    TopAppBar(
        title = { Text("Mi Entrenamiento") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun MyFloatingActionButton(snackbarHostState: SnackbarHostState) {
    var showSnackbar by remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = { showSnackbar = true },
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    ) {
        Icon(Icons.Default.Face, contentDescription = "Iniciar")
    }

    if (showSnackbar) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("¡Entrenamiento iniciado!")
            showSnackbar = false
        }
    }
}

@Composable
fun MyLazyColumn() {
    Column {
        Text("Rutinas (LazyColumn):", style = MaterialTheme.typography.titleMedium)
        LazyColumn(
            modifier = Modifier
                .height(150.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) { index ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Rutina #$index",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun MyLazyRow() {
    Column {
        Text("Categorías (LazyRow):", style = MaterialTheme.typography.titleMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(5) { index ->
                ElevatedCard(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = when (index) {
                                0 -> "Cardio"
                                1 -> "Fuerza"
                                2 -> "Yoga"
                                3 -> "Crossfit"
                                else -> "HIIT"
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyFlowRow() {
    Column {
        Text("Metas (FlowRow):", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Bajar de peso", "Tonificar", "Resistencia", "Flexibilidad").forEach {
                ElevatedCard(
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = it, modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}

@Composable
fun MyOutlinedTextField() {
    var userName by remember { mutableStateOf("") }
    OutlinedTextField(
        value = userName,
        onValueChange = { userName = it },
        label = { Text("Tu nombre") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MyCheckbox() {
    var reminders by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = reminders, onCheckedChange = { reminders = it })
        Text("Activar recordatorios")
    }
}

@Composable
fun MySlider() {
    var intensity by remember { mutableStateOf(0.5f) }
    Column {
        Text("Intensidad: ${(intensity * 10).toInt()}/10")
        Slider(
            value = intensity,
            onValueChange = { intensity = it },
            valueRange = 0f..1f
        )
    }
}

@Composable
fun MyAlertDialog() {
    var openDialog by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { openDialog = true }) {
            Text("Iniciamos ?")
        }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = { openDialog = false },
                title = { Text("Confirmación") },
                text = { Text("¿Deseas iniciar la rutina?") },
                confirmButton = {
                    TextButton(onClick = { openDialog = false }) {
                        Text("Sí")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Composable
fun MyProgressBar() {
    var progress by remember { mutableStateOf(0.1f) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Progreso de la rutina")
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (progress < 1f) progress += 0.1f
        }) {
            Text("Avanzar")
        }
    }
}


@Composable
fun MyIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.gym_svgrepo_com),
            contentDescription = "Icono Fitness",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp)
        )
        Text(
            text = "Do it",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFitnessScreen() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        FitnessScreen()
    }
}
