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
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.gymtrackerpro.viewmodel.GymViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavController, viewModel: GymViewModel) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Colors
    val darkBackground = Color(0xFF0D0D0D)
    val neonPurple = Color(0xFF7B2FF7)
    val gradientPurple = Color(0xFFA855F7)
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f)

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Background Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            neonPurple.copy(alpha = 0.15f),
                            Color.Transparent,
                            darkBackground
                        )
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 30.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(initialOffsetY = { -40 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Logo Icon
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(neonPurple.copy(alpha = 0.1f))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp),
                                tint = neonPurple
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Title
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                                    append("GymTracker ")
                                }
                                withStyle(style = SpanStyle(color = neonPurple, fontWeight = FontWeight.ExtraBold)) {
                                    append("Pro")
                                }
                            },
                            fontSize = 32.sp,
                            style = MaterialTheme.typography.headlineLarge
                        )

                        Text(
                            text = "Tu entrenamiento, tu progreso",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Inputs
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // User Input
                    OutlinedTextField(
                        value = user,
                        onValueChange = { user = it },
                        label = { Text("Usuario", color = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(glassColor),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = neonPurple) },
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

                    // Password Input
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

                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = neonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { /* Handle forgot password */ }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Login Button
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "buttonScale")

                Button(
                    onClick = {
                        viewModel.login(user, password) { success ->
                            if (success) {
                                navController.navigate("menu") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                showError = true
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
                        "INICIAR SESIÓN",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Register Text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿No tienes cuenta? ",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Regístrate",
                        color = neonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.navigate("registro") }
                    )
                }

                if (showError) {
                    LaunchedEffect(Unit) {
                        snackbarHostState.showSnackbar("Credenciales incorrectas")
                        showError = false
                    }
                }
            }
        }
    }
}
