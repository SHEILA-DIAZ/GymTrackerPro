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
import androidx.compose.material.icons.filled.* // Importa todos los iconos -> Visuales
import androidx.compose.material3.* // Importa componentes Material3 -> UI Kit
import androidx.compose.runtime.* // Importa runtime de Compose -> Manejo de estados
import androidx.compose.runtime.saveable.rememberSaveable // Importa estado persistente -> Memoria ante rotación
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
import com.example.gymtrackerpro.model.Usuario // Importa Modelo Usuario -> Estructura datos
import com.example.gymtrackerpro.viewmodel.GymViewModel // Importa ViewModel -> Lógica de negocio
import kotlinx.coroutines.delay // Importa retardo -> Control de tiempo
import kotlinx.coroutines.launch // Importa lanzador de corrutinas -> Tareas asíncronas
import java.text.SimpleDateFormat // Importa formateador de fecha -> Utilidad de tiempo
import java.util.* // Importa utilidades java -> Manejo de fechas

@OptIn(ExperimentalMaterial3Api::class) // Habilita APIs experimentales -> Uso de TopAppBar
@Composable // Marca función como Composable -> Generador de UI
fun RegistroScreen(navController: NavController, viewModel: GymViewModel) { // Pantalla de registro -> Recibe controlador y VM
    var nombreCompleto by rememberSaveable { mutableStateOf("") } // Estado nombre -> Texto ingresado
    var user by rememberSaveable { mutableStateOf("") } // Estado usuario -> Texto ingresado
    var email by rememberSaveable { mutableStateOf("") } // Estado email -> Texto ingresado
    var edad by rememberSaveable { mutableStateOf("") } // Estado edad -> Texto ingresado
    var password by rememberSaveable { mutableStateOf("") } // Estado contraseña -> Texto ingresado
    var passwordVisible by rememberSaveable { mutableStateOf(false) } // Estado visibilidad -> Booleano UI
    var visible by remember { mutableStateOf(false) } // Estado animación -> Control de visibilidad
    
    val snackbarHostState = remember { SnackbarHostState() } // Estado snackbar -> Gestor de mensajes
    val scope = rememberCoroutineScope() // Scope de corrutinas -> Lanzador de efectos

    val darkBackground = Color(0xFF0D0D0D) // Color fondo -> Paleta oscura
    val neonPurple = Color(0xFF7B2FF7) // Color principal -> Paleta neón
    val gradientPurple = Color(0xFFA855F7) // Color secundario -> Paleta degradada
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f) // Color traslúcido -> Efecto cristal

    LaunchedEffect(Unit) { // Efecto de arranque -> Se ejecuta al cargar
        delay(200) // Espera inicial -> Pausa estética
        visible = true // Activa visibilidad -> Dispara animaciones
    }

    Box( // Contenedor base -> Capas de fondo
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Box( // Fondo degradado -> Efecto estético
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

        Scaffold( // Estructura de pantalla -> Layout estándar
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar( // Barra superior -> Navegación atrás
                    title = {
                        Text(
                            "Crear Cuenta",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) { // Acción volver -> Regresa pantalla previa
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
            Column( // Contenedor vertical -> Formulario scrolleable
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility( // Animación de entrada -> Header
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(800)) + slideInVertically(initialOffsetY = { -20 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Box( // Contenedor icono -> Diseño visual
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(neonPurple.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon( // Icono central -> Visual decorativo
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                modifier = Modifier.size(45.dp),
                                tint = neonPurple
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text( // Título -> Mensaje principal
                            text = "Únete a la élite",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                        
                        Text( // Subtítulo -> Texto secundario
                            text = "Comienza tu transformación hoy mismo",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column( // Grupo de entradas -> Campos del usuario
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RegistroTextField( // Campo nombre -> Entrada texto
                        value = nombreCompleto,
                        onValueChange = { nombreCompleto = it },
                        label = "Nombre Completo",
                        icon = Icons.Default.Badge,
                        neonPurple = neonPurple,
                        glassColor = glassColor
                    )

                    RegistroTextField( // Campo usuario -> Entrada texto
                        value = user,
                        onValueChange = { user = it },
                        label = "Usuario",
                        icon = Icons.Default.Person,
                        neonPurple = neonPurple,
                        glassColor = glassColor
                    )

                    RegistroTextField( // Campo email -> Entrada validada
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email,
                        neonPurple = neonPurple,
                        glassColor = glassColor
                    )

                    RegistroTextField( // Campo edad -> Entrada numérica
                        value = edad,
                        onValueChange = { if (it.all { char -> char.isDigit() }) edad = it },
                        label = "Edad",
                        icon = Icons.Default.Cake,
                        keyboardType = KeyboardType.Number,
                        neonPurple = neonPurple,
                        glassColor = glassColor
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

                    if (password.isNotEmpty()) { // Indicador fortaleza -> Reacción a input
                        PasswordStrengthBar(password, neonPurple)
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                val interactionSource = remember { MutableInteractionSource() } // Manejo de interacción -> Feedback visual
                val isPressed by interactionSource.collectIsPressedAsState() // Detecta presión -> Estado reactivo
                val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "btnScale") // Calcula escala -> Animación

                Button( // Botón registro -> Ejecuta guardado
                    onClick = {
                        if (nombreCompleto.isBlank() || user.isBlank() || email.isBlank() || edad.isBlank() || password.isBlank()) { // Validación vacíos -> Control de datos
                            scope.launch { snackbarHostState.showSnackbar("Por favor, completa todos los campos") } // Muestra aviso -> Feedback UI
                            return@Button
                        }
                        
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Validación formato email -> Control de datos
                            scope.launch { snackbarHostState.showSnackbar("Email inválido") } // Muestra aviso -> Feedback UI
                            return@Button
                        }

                        if (password.length < 6) { // Validación longitud clave -> Seguridad
                            scope.launch { snackbarHostState.showSnackbar("La contraseña debe tener al menos 6 caracteres") } // Muestra aviso -> Feedback UI
                            return@Button
                        }

                        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()) // Genera fecha -> Dato de sistema
                        val nuevoUsuario = Usuario( // Crea objeto -> Empaqueta datos para DB
                            nombreUsuario = user,
                            password = password,
                            nombreCompleto = nombreCompleto,
                            email = email,
                            edad = edad.toIntOrNull() ?: 0,
                            fechaRegistro = fechaActual
                        )

                        viewModel.registrarUsuario(nuevoUsuario) { error -> // Llama a registro -> Envía objeto al ViewModel
                            if (error == null) { // Caso éxito -> Navegación
                                navController.popBackStack() // Vuelve al login -> Acción final exitosa
                            } else { // Caso error (duplicado) -> Notificación
                                scope.launch { snackbarHostState.showSnackbar(error) } // Muestra error -> Feedback del servidor/DB
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
                        "REGISTRARME",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row( // Footer -> Alternativa login
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text( // Pregunta -> Texto guía
                        text = "¿Ya tienes cuenta? ",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Text( // Link login -> Navegación atrás
                        text = "Inicia sesión",
                        color = neonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.popBackStack() } // Vuelve atrás -> Regresa al login
                    )
                }
            }
        }
    }
}

@Composable
fun RegistroTextField( // Componente campo personalizado -> Reutilización de UI
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    neonPurple: Color,
    glassColor: Color
) {
    OutlinedTextField( // Campo de texto -> Entrada de datos
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
fun PasswordStrengthBar(password: String, neonPurple: Color) { // Barra de fortaleza -> Feedback seguridad
    val strength = when { // Calcula nivel -> Lógica de seguridad
        password.length < 6 -> 0.2f
        password.any { it.isDigit() } && password.any { it.isUpperCase() } -> 1f
        else -> 0.5f
    }
    
    val color = when { // Asigna color -> Feedback visual semántico
        strength <= 0.2f -> Color.Red
        strength <= 0.5f -> Color.Yellow
        else -> neonPurple
    }

    Column(modifier = Modifier.padding(horizontal = 4.dp)) {
        LinearProgressIndicator( // Indicador visual -> Barra de progreso
            progress = { strength },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = color,
            trackColor = Color.White.copy(alpha = 0.1f),
        )
        Text( // Etiqueta nivel -> Texto descriptivo
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
