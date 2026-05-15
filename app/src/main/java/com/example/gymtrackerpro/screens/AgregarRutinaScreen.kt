package com.example.gymtrackerpro.screens // Define paquete -> Ubicación en el proyecto

import androidx.compose.animation.* // Importa animaciones -> Control de UI reactiva
import androidx.compose.animation.core.* // Importa núcleo de animaciones -> Control de estados
import androidx.compose.foundation.background // Importa fondo -> Estilo visual
import androidx.compose.foundation.interaction.MutableInteractionSource // Importa fuente interacción -> Estado de entrada
import androidx.compose.foundation.interaction.collectIsPressedAsState // Importa estado presión -> Feedback visual
import androidx.compose.foundation.layout.* // Importa layouts -> Estructura de UI
import androidx.compose.foundation.rememberScrollState // Importa estado scroll -> Persistencia de posición
import androidx.compose.foundation.shape.RoundedCornerShape // Importa esquinas redondeadas -> Estilo visual
import androidx.compose.foundation.text.KeyboardOptions // Importa teclado -> Configuración entrada
import androidx.compose.foundation.verticalScroll // Importa scroll vertical -> Navegación de contenido
import androidx.compose.material.icons.Icons // Importa iconos -> Librería Material
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Importa icono atrás -> Visual
import androidx.compose.material.icons.filled.* // Importa todos los iconos -> Visuales
import androidx.compose.material3.* // Importa componentes Material3 -> UI Kit
import androidx.compose.runtime.* // Importa runtime de Compose -> Manejo de estados
import androidx.compose.ui.Alignment // Importa alineación -> Posicionamiento
import androidx.compose.ui.Modifier // Importa modificadores -> Atributos de componentes
import androidx.compose.ui.draw.clip // Importa recorte -> Forma visual
import androidx.compose.ui.graphics.Brush // Importa degradados -> Estilo visual
import androidx.compose.ui.graphics.Color // Importa colores -> Estilo visual
import androidx.compose.ui.graphics.graphicsLayer // Importa capa gráfica -> Transformaciones 2D/3D
import androidx.compose.ui.graphics.vector.ImageVector // Importa vector imagen -> Tipo de icono
import androidx.compose.ui.text.font.FontWeight // Importa peso fuente -> Estilo texto
import androidx.compose.ui.text.input.KeyboardType // Importa tipo teclado -> Configuración entrada
import androidx.compose.ui.unit.dp // Importa unidad dp -> Medidas UI
import androidx.compose.ui.unit.sp // Importa unidad sp -> Tamaño texto
import androidx.navigation.NavController // Importa controlador navegación -> Gestión de rutas
import com.example.gymtrackerpro.model.Rutina // Importa Modelo Rutina -> Entidad
import com.example.gymtrackerpro.viewmodel.GymViewModel // Importa ViewModel -> Lógica de negocio
import kotlinx.coroutines.delay // Importa retardo -> Control de tiempo
import java.text.SimpleDateFormat // Importa formateador fecha -> Utilidad de tiempo
import java.util.* // Importa utilidades java -> Manejo de fechas

@OptIn(ExperimentalMaterial3Api::class) // Habilita APIs experimentales -> Uso de ExposedDropdown/TopAppBar
@Composable // Marca función como Composable -> Generador de UI
fun AgregarRutinaScreen(navController: NavController, viewModel: GymViewModel) { // Pantalla agregar rutina -> Recibe controlador y VM
    val usuario by viewModel.usuarioLogueado.collectAsState() // Observa sesión -> Obtiene ID de usuario
    
    var ejercicio by remember { mutableStateOf("") } // Estado ejercicio -> Texto ingresado
    var grupoMuscular by remember { mutableStateOf("Pecho") } // Estado categoría -> Valor seleccionado
    var series by remember { mutableStateOf("") } // Estado series -> Texto numérico
    var repeticiones by remember { mutableStateOf("") } // Estado reps -> Texto numérico
    var peso by remember { mutableStateOf("") } // Estado peso -> Texto decimal
    var fecha by remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())) } // Estado fecha -> Generado automáticamente

    val grupos = listOf("Pecho", "Espalda", "Pierna", "Brazo", "Hombro") // Opciones fijas -> Datos para selector
    var expanded by remember { mutableStateOf(false) } // Estado dropdown -> Control apertura menú
    var visible by remember { mutableStateOf(false) } // Estado animación -> Control de visibilidad

    val darkBackground = Color(0xFF0D0D0D) // Color fondo -> Paleta oscura
    val neonPurple = Color(0xFF7B2FF7) // Color principal -> Paleta neón
    val gradientPurple = Color(0xFFA855F7) // Color secundario -> Paleta degradada
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f) // Color traslúcido -> Efecto cristal

    LaunchedEffect(Unit) { // Efecto arranque -> Se ejecuta al cargar
        delay(100) // Pausa inicial -> Mejora estética
        visible = true // Activa visibilidad -> Dispara animaciones
    }

    Box( // Contenedor base -> Capas superpuestas
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Box( // Fondo con degradado -> Estilo visual
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

        Scaffold( // Estructura pantalla -> Layout estándar
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar( // Barra superior -> Título y navegación
                    title = {
                        Text(
                            "Nueva Rutina",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) { // Acción volver -> Regresa pantalla previa
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                    .verticalScroll(rememberScrollState())
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility( // Animación entrada -> Grupo de inputs
                    visible = visible,
                    enter = fadeIn(tween(800)) + slideInVertically(initialOffsetY = { 20 })
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Spacer(modifier = Modifier.height(16.dp))

                        PremiumTextField( // Campo ejercicio -> Entrada texto
                            value = ejercicio,
                            onValueChange = { ejercicio = it },
                            label = "Ejercicio",
                            icon = Icons.Default.FitnessCenter,
                            neonPurple = neonPurple,
                            glassColor = glassColor
                        )

                        ExposedDropdownMenuBox( // Selector desplegable -> Grupo muscular
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField( // Campo de texto del selector -> Solo lectura
                                value = grupoMuscular,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Grupo Muscular", color = Color.Gray) },
                                trailingIcon = { 
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(glassColor),
                                leadingIcon = { 
                                    Icon(Icons.Default.Category, contentDescription = null, tint = neonPurple) 
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonPurple,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                                    focusedLabelColor = neonPurple,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = neonPurple,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                            
                            DropdownMenu( // Menú de opciones -> Selección fija
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .background(Color(0xFF1A1A1A))
                                    .fillMaxWidth(0.85f)
                            ) {
                                grupos.forEach { selectionOption ->
                                    DropdownMenuItem( // Opción individual -> Item de lista
                                        text = { Text(selectionOption, color = Color.White) },
                                        onClick = {
                                            grupoMuscular = selectionOption // Actualiza selección -> Dato UI
                                            expanded = false // Cierra menú -> Acción UI
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.Circle, contentDescription = null, tint = neonPurple, modifier = Modifier.size(8.dp))
                                        }
                                    )
                                }
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) { // Fila horizontal -> Inputs pareados
                            PremiumTextField( // Campo series -> Entrada numérica
                                value = series,
                                onValueChange = { if (it.all { c -> c.isDigit() }) series = it },
                                label = "Series",
                                icon = Icons.Default.Repeat,
                                neonPurple = neonPurple,
                                glassColor = glassColor,
                                modifier = Modifier.weight(1f),
                                keyboardType = KeyboardType.Number
                            )
                            PremiumTextField( // Campo repeticiones -> Entrada numérica
                                value = repeticiones,
                                onValueChange = { if (it.all { c -> c.isDigit() }) repeticiones = it },
                                label = "Reps",
                                icon = Icons.Default.FlashOn,
                                neonPurple = neonPurple,
                                glassColor = glassColor,
                                modifier = Modifier.weight(1f),
                                keyboardType = KeyboardType.Number
                            )
                        }

                        PremiumTextField( // Campo peso -> Entrada decimal
                            value = peso,
                            onValueChange = { peso = it },
                            label = "Peso (kg)",
                            icon = Icons.Default.MonitorWeight,
                            neonPurple = neonPurple,
                            glassColor = glassColor,
                            keyboardType = KeyboardType.Decimal
                        )

                        PremiumTextField( // Campo fecha -> Texto informativo
                            value = fecha,
                            onValueChange = { fecha = it },
                            label = "Fecha",
                            icon = Icons.Default.CalendarToday,
                            neonPurple = neonPurple,
                            glassColor = glassColor,
                            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray) }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        val interactionSource = remember { MutableInteractionSource() } // Manejo interacción -> Feedback
                        val isPressed by interactionSource.collectIsPressedAsState() // Detecta toque -> Estado reactivo
                        val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "btnScale") // Calcula escala -> Animación

                        Button( // Botón de guardado -> Ejecuta inserción
                            onClick = {
                                val userId = usuario?.id ?: return@Button // Valida sesión -> Seguridad
                                val nuevaRutina = Rutina( // Crea objeto -> Empaqueta datos
                                    usuarioId = userId,
                                    ejercicio = ejercicio,
                                    grupoMuscular = grupoMuscular,
                                    series = series.toIntOrNull() ?: 0,
                                    repeticiones = repeticiones.toIntOrNull() ?: 0,
                                    pesoKg = peso.toDoubleOrNull() ?: 0.0,
                                    fecha = fecha
                                )
                                viewModel.agregarRutina(nuevaRutina) // Llama a guardado -> Envía al ViewModel
                                navController.popBackStack() // Vuelve atrás -> Acción final exitosa
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
                            Row(verticalAlignment = Alignment.CenterVertically) { // Contenido botón -> Icono y texto
                                Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "GUARDAR RUTINA",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 1.2.sp
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PremiumTextField( // Componente personalizado -> Input estilizado
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    neonPurple: Color,
    glassColor: Color,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField( // Campo de texto -> Entrada de datos
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(glassColor),
        leadingIcon = { Icon(icon, contentDescription = null, tint = neonPurple) },
        trailingIcon = trailingIcon,
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
