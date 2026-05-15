package com.example.gymtrackerpro.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtrackerpro.model.Usuario
import com.example.gymtrackerpro.viewmodel.GymViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController, viewModel: GymViewModel) {
    var nombreCompleto by rememberSaveable { mutableStateOf("") }
    var user by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Colors
    val darkBackground = Color(0xFF0D0D0D)
    val neonPurple = Color(0xFF7B2FF7)
    val gradientPurple = Color(0xFFA855F7)
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f)

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Background Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            neonPurple.copy(alpha = 0.1f),
                            Color.Transparent,
                            darkBackground
                        )
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Crear Cuenta",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Atrás",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(800)) + slideInVertically(initialOffsetY = { -20 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Header Icon
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(neonPurple.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                modifier = Modifier.size(45.dp),
                                tint = neonPurple
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Únete a la élite",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        
                        Text(
                            text = "Comienza tu transformación hoy mismo",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Input Fields
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Full Name
                    RegistroTextField(
                        value = nombreCompleto,
                        onValueChange = { nombreCompleto = it },
                        label = "Nombre Completo",
                        icon = Icons.Default.Badge,
                        neonPurple = neonPurple,
                        glassColor = glassColor
                    )

                    // User
                    RegistroTextField(
                        value = user,
                        onValueChange = { user = it },
                        label = "Usuario",
                        icon = Icons.Default.Person,
                        neonPurple = neonPurple,
                        glassColor = glassColor
                    )

                    // Email
                    RegistroTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email,
                        neonPurple = neonPurple,
                        glassColor = glassColor
                    )

                    // Age
                    RegistroTextField(
                        value = edad,
                        onValueChange = { if (it.all { char -> char.isDigit() }) edad = it },
                        label = "Edad",
                        icon = Icons.Default.Cake,
                        keyboardType = KeyboardType.Number,
                        neonPurple = neonPurple,
                        glassColor = glassColor
                    )

                    // Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña", color = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(glassColor),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = neonPurple) },
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = null, tint = Color.Gray)
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = neonPurple,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                            focusedLabelColor = neonPurple,
                            unfocusedLabelColor = Color.Gray,
                            cursorColor = neonPurple,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    // Password Strength Indicator (Simple)
                    if (password.isNotEmpty()) {
                        PasswordStrengthBar(password, neonPurple)
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Register Button
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "btnScale")

                Button(
                    onClick = {
                        if (nombreCompleto.isBlank() || user.isBlank() || email.isBlank() || edad.isBlank() || password.isBlank()) {
                            scope.launch { snackbarHostState.showSnackbar("Por favor, completa todos los campos") }
                            return@Button
                        }
                        
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            scope.launch { snackbarHostState.showSnackbar("Email inválido") }
                            return@Button
                        }

                        if (password.length < 6) {
                            scope.launch { snackbarHostState.showSnackbar("La contraseña debe tener al menos 6 caracteres") }
                            return@Button
                        }

                        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                        val nuevoUsuario = Usuario(
                            nombreUsuario = user,
                            password = password,
                            nombreCompleto = nombreCompleto,
                            email = email,
                            edad = edad.toIntOrNull() ?: 0,
                            fechaRegistro = fechaActual
                        )

                        viewModel.registrarUsuario(nuevoUsuario) { error ->
                            if (error == null) {
                                navController.popBackStack()
                            } else {
                                scope.launch { snackbarHostState.showSnackbar(error) }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .graphicsLayer(scaleX = scale, scaleY = scale)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(neonPurple, gradientPurple)
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(16.dp),
                    interactionSource = interactionSource
                ) {
                    Text(
                        "REGISTRARME",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Already have account
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Ya tienes cuenta? ",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Inicia sesión",
                        color = neonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun RegistroTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    neonPurple: Color,
    glassColor: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(glassColor),
        leadingIcon = { Icon(icon, contentDescription = null, tint = neonPurple) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = neonPurple,
            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
            focusedLabelColor = neonPurple,
            unfocusedLabelColor = Color.Gray,
            cursorColor = neonPurple,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}

@Composable
fun PasswordStrengthBar(password: String, neonPurple: Color) {
    val strength = when {
        password.length < 6 -> 0.2f
        password.any { it.isDigit() } && password.any { it.isUpperCase() } -> 1f
        else -> 0.5f
    }
    
    val color = when {
        strength <= 0.2f -> Color.Red
        strength <= 0.5f -> Color.Yellow
        else -> neonPurple
    }

    Column(modifier = Modifier.padding(horizontal = 4.dp)) {
        LinearProgressIndicator(
            progress = { strength },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = color,
            trackColor = Color.White.copy(alpha = 0.1f),
        )
        Text(
            text = when {
                strength <= 0.2f -> "Débil"
                strength <= 0.5f -> "Media"
                else -> "Fuerte"
            },
            color = color,
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
