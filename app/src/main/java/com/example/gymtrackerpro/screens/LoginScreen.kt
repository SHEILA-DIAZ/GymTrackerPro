package com.example.gymtrackerpro.screens // Define paquete -> Ubicación en el proyecto

import androidx.compose.animation.AnimatedVisibility // Importa visibilidad animada -> Animación UI
import androidx.compose.animation.core.* // Importa núcleo de animaciones -> Control de estados
import androidx.compose.animation.fadeIn // Importa desvanecimiento -> Efecto visual
import androidx.compose.animation.slideInVertically // Importa deslizamiento vertical -> Efecto visual
import androidx.compose.foundation.background // Importa fondo -> Estilo visual
import androidx.compose.foundation.clickable // Importa click -> Interacción de usuario
import androidx.compose.foundation.interaction.MutableInteractionSource // Importa fuente interacción -> Estado de entrada
import androidx.compose.foundation.interaction.collectIsPressedAsState // Importa estado presión -> Feedback visual
import androidx.compose.foundation.layout.* // Importa layouts -> Estructura de UI
import androidx.compose.foundation.rememberScrollState // Importa estado scroll -> Persistencia de posición
import androidx.compose.foundation.shape.RoundedCornerShape // Importa esquinas redondeadas -> Estilo visual
import androidx.compose.foundation.text.KeyboardOptions // Importa teclado -> Configuración entrada
import androidx.compose.foundation.verticalScroll // Importa scroll vertical -> Navegación de contenido
import androidx.compose.material.icons.Icons // Importa iconos -> Librería Material
import androidx.compose.material.icons.filled.FitnessCenter // Importa icono pesas -> Visual
import androidx.compose.material.icons.filled.Lock // Importa icono candado -> Visual
import androidx.compose.material.icons.filled.Person // Importa icono persona -> Visual
import androidx.compose.material.icons.filled.Visibility // Importa icono ver -> Visual
import androidx.compose.material.icons.filled.VisibilityOff // Importa icono no ver -> Visual
import androidx.compose.material3.* // Importa componentes Material3 -> UI Kit
import androidx.compose.runtime.* // Importa runtime de Compose -> Manejo de estados
import androidx.compose.ui.Alignment // Importa alineación -> Posicionamiento
import androidx.compose.ui.Modifier // Importa modificadores -> Atributos de componentes
import androidx.compose.ui.draw.clip // Importa recorte -> Forma visual
import androidx.compose.ui.graphics.Brush // Importa degradados -> Estilo visual
import androidx.compose.ui.graphics.Color // Importa colores -> Estilo visual
import androidx.compose.ui.graphics.graphicsLayer // Importa capa gráfica -> Transformaciones 2D/3D
import androidx.compose.ui.text.SpanStyle // Importa estilo de texto -> Formato parcial
import androidx.compose.ui.text.buildAnnotatedString // Importa cadena anotada -> Texto mixto
import androidx.compose.ui.text.font.FontWeight // Importa peso fuente -> Estilo texto
import androidx.compose.ui.text.input.KeyboardType // Importa tipo teclado -> Configuración entrada
import androidx.compose.ui.text.input.PasswordVisualTransformation // Importa transformación password -> Privacidad entrada
import androidx.compose.ui.text.input.VisualTransformation // Importa transformación visual -> Formato entrada
import androidx.compose.ui.text.withStyle // Importa con estilo -> Bloque de formato
import androidx.compose.ui.unit.dp // Importa unidad dp -> Medidas UI
import androidx.compose.ui.unit.sp // Importa unidad sp -> Tamaño texto
import androidx.navigation.NavController // Importa controlador navegación -> Gestión de rutas
import com.example.gymtrackerpro.viewmodel.GymViewModel // Importa ViewModel -> Lógica de negocio
import kotlinx.coroutines.delay // Importa retardo -> Control de tiempo

@Composable // Marca función como Composable -> Generador de UI
fun LoginScreen(navController: NavController, viewModel: GymViewModel) { // Pantalla de login -> Recibe controlador y VM
    var user by remember { mutableStateOf("") } // Estado usuario -> Texto ingresado
    var password by remember { mutableStateOf("") } // Estado contraseña -> Texto ingresado
    var passwordVisible by remember { mutableStateOf(false) } // Estado visibilidad -> Booleano UI
    var showError by remember { mutableStateOf(false) } // Estado error -> Control de snackbar
    var visible by remember { mutableStateOf(false) } // Estado animación inicial -> Control de visibilidad
    val snackbarHostState = remember { SnackbarHostState() } // Estado snackbar -> Gestor de mensajes

    val darkBackground = Color(0xFF0D0D0D) // Color fondo -> Paleta oscura
    val neonPurple = Color(0xFF7B2FF7) // Color principal -> Paleta neón
    val gradientPurple = Color(0xFFA855F7) // Color secundario -> Paleta degradada
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f) // Color traslúcido -> Efecto cristal

    LaunchedEffect(Unit) { // Efecto de arranque -> Se ejecuta al cargar
        delay(300) // Espera inicial -> Pausa de animación
        visible = true // Activa visibilidad -> Dispara animaciones
    }

    Box( // Contenedor base -> Capas superpuestas
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Box( // Fondo con degradado -> Efecto estético
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

        Scaffold( // Estructura de pantalla -> Soporte para snackbar
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { padding ->
            Column( // Contenedor vertical -> Apila elementos centralmente
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 30.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility( // Animación de entrada -> Logo y título
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(initialOffsetY = { -40 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box( // Contenedor logo -> Diseño ícono
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(neonPurple.copy(alpha = 0.1f))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon( // Icono central -> Visual decorativo
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp),
                                tint = neonPurple
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp)) // Espaciador -> Separación vertical

                        Text( // Título de app -> Texto estilizado
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

                        Text( // Lema -> Texto secundario
                            text = "Tu entrenamiento, tu progreso",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp)) // Espaciador -> Separación de formulario

                Column( // Grupo de campos -> Inputs de datos
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField( // Campo usuario -> Entrada de texto
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

                    OutlinedTextField( // Campo contraseña -> Entrada protegida
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

                    Text( // Olvido contraseña -> Link de ayuda
                        text = "¿Olvidaste tu contraseña?",
                        color = neonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp)) // Espaciador -> Separación de botón

                val interactionSource = remember { MutableInteractionSource() } // Manejo de interacción -> Feedback visual
                val isPressed by interactionSource.collectIsPressedAsState() // Detecta presión -> Estado reactivo
                val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "buttonScale") // Calcula escala -> Animación botón

                Button( // Botón login -> Ejecuta acción
                    onClick = {
                        viewModel.login(user, password) { success -> // Llama a login -> Envía datos a ViewModel
                            if (success) { // Verifica resultado -> Control flujo navegación
                                navController.navigate("menu") { // Navega al menú -> Destino de éxito
                                    popUpTo("login") { inclusive = true } // Limpia historial -> Evita volver atrás
                                }
                            } else { // Caso error -> Notificación
                                showError = true // Dispara snackbar -> Feedback error
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
                    Text( // Texto del botón -> Call to action
                        "INICIAR SESIÓN",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp)) // Espaciador -> Separación de footer

                Row( // Footer registro -> Navegación externa
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text( // Pregunta -> Texto guía
                        text = "¿No tienes cuenta? ",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Text( // Link registro -> Navegación
                        text = "Regístrate",
                        color = neonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.navigate("registro") } // Navega a registro -> Destino alternativo
                    )
                }

                if (showError) { // Muestra error -> Reacción a estado
                    LaunchedEffect(Unit) { // Lanza efecto visual -> Snackbar
                        snackbarHostState.showSnackbar("Credenciales incorrectas") // Muestra mensaje -> Notificación al usuario
                        showError = false // Reinicia estado -> Limpieza
                    }
                }
            }
        }
    }
}
