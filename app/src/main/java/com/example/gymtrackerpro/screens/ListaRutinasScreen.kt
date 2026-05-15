package com.example.gymtrackerpro.screens // Define paquete -> Ubicación en el proyecto

import androidx.compose.animation.* // Importa animaciones -> Control de UI reactiva
import androidx.compose.animation.core.* // Importa núcleo de animaciones -> Control de estados
import androidx.compose.foundation.background // Importa fondo -> Estilo visual
import androidx.compose.foundation.clickable // Importa click -> Interacción de usuario
import androidx.compose.foundation.layout.* // Importa layouts -> Estructura de UI
import androidx.compose.foundation.lazy.LazyColumn // Importa lista vertical -> Carga optimizada
import androidx.compose.foundation.lazy.items // Importa items de lista -> Vinculación de datos
import androidx.compose.foundation.shape.CircleShape // Importa forma circular -> Estilo visual
import androidx.compose.foundation.shape.RoundedCornerShape // Importa esquinas redondeadas -> Estilo visual
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
import androidx.compose.ui.text.font.FontWeight // Importa peso fuente -> Estilo texto
import androidx.compose.ui.text.style.TextAlign // Importa alineación texto -> Formato
import androidx.compose.ui.unit.dp // Importa unidad dp -> Medidas UI
import androidx.compose.ui.unit.sp // Importa unidad sp -> Tamaño texto
import androidx.navigation.NavController // Importa controlador navegación -> Gestión de rutas
import com.example.gymtrackerpro.model.Rutina // Importa Modelo Rutina -> Entidad
import com.example.gymtrackerpro.viewmodel.GymViewModel // Importa ViewModel -> Lógica de negocio
import kotlinx.coroutines.delay // Importa retardo -> Control de tiempo

@OptIn(ExperimentalMaterial3Api::class) // Habilita APIs experimentales -> Uso de TopAppBar
@Composable // Marca función como Composable -> Generador de UI
fun ListaRutinasScreen(navController: NavController, viewModel: GymViewModel) { // Pantalla lista rutinas -> Recibe controlador y VM
    val usuario by viewModel.usuarioLogueado.collectAsState() // Observa sesión -> Obtiene ID para filtrar
    val rutinas by viewModel.getRutinas(usuario?.id ?: 0).collectAsState(initial = emptyList()) // Observa rutinas -> Obtiene lista del ViewModel
    
    var routineToDelete by remember { mutableStateOf<Rutina?>(null) } // Estado para borrado -> Referencia a objeto temporal
    var visible by remember { mutableStateOf(false) } // Estado animación -> Control de visibilidad

    val darkBackground = Color(0xFF0D0D0D) // Color fondo -> Paleta oscura
    val neonPurple = Color(0xFF7B2FF7) // Color principal -> Paleta neón
    val gradientPurple = Color(0xFFA855F7) // Color secundario -> Paleta degradada
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f) // Color traslúcido -> Efecto cristal

    LaunchedEffect(Unit) { // Efecto arranque -> Se ejecuta al cargar
        delay(100) // Pausa estética -> Sincronía visual
        visible = true // Activa visibilidad -> Dispara animaciones
    }

    Box( // Contenedor base -> Capas superpuestas
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        Box( // Overlay degradado -> Estilo visual
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
                CenterAlignedTopAppBar( // Barra superior -> Título y acciones
                    title = {
                        Text(
                            "Mis Rutinas",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    },
                    navigationIcon = {
                        IconButton( // Botón atrás -> Navegación previa
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(CircleShape)
                                .background(glassColor)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) { // Botón buscar -> Espacio para función futura
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton( // Botón flotante -> Acción rápida agregar
                    onClick = { navController.navigate("agregar_rutina") },
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(neonPurple, gradientPurple)
                            )
                        )
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }
        ) { padding ->
            AnimatedVisibility( // Animación de lista -> Entrada suave
                visible = visible,
                enter = fadeIn(tween(800)) + slideInVertically(initialOffsetY = { 30 }),
                modifier = Modifier.padding(padding)
            ) {
                if (rutinas.isEmpty()) { // Verificación vacíos -> Control de estado
                    EmptyRutinasState(neonPurple, navController) // UI vacía -> Mensaje motivacional
                } else {
                    LazyColumn( // Lista de rutinas -> Scroll optimizado
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
                    ) {
                        items(rutinas, key = { it.id }) { rutina -> // Itera rutinas -> Genera componentes
                            RutinaItemPremium( // Ítem de rutina -> Tarjeta informativa
                                rutina = rutina,
                                neonPurple = neonPurple,
                                glassColor = glassColor,
                                onEdit = { navController.navigate("detalle_rutina/${rutina.id}") }, // Navega a detalle -> Envía ID
                                onDelete = { routineToDelete = rutina } // Prepara borrado -> Activa diálogo
                            )
                        }
                    }
                }
            }

            if (routineToDelete != null) { // Diálogo de borrado -> Confirmación de seguridad
                AlertDialog(
                    onDismissRequest = { routineToDelete = null },
                    containerColor = Color(0xFF1A1A1A),
                    titleContentColor = Color.White,
                    textContentColor = Color.White.copy(alpha = 0.7f),
                    title = { Text("¿Eliminar Rutina?", fontWeight = FontWeight.Bold) },
                    text = { Text("Esta acción no se puede deshacer. ¿Estás seguro?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                routineToDelete?.let { viewModel.eliminarRutina(it) } // Ejecuta borrado -> Llama al ViewModel
                                routineToDelete = null // Cierra diálogo -> Acción final
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                        ) {
                            Text("ELIMINAR", fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { routineToDelete = null }) {
                            Text("CANCELAR", color = Color.Gray)
                        }
                    },
                    shape = RoundedCornerShape(24.dp)
                )
            }
        }
    }
}

@Composable
fun RutinaItemPremium( // Componente tarjeta rutina -> Visualización de registro
    rutina: Rutina,
    neonPurple: Color,
    glassColor: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card( // Contenedor item -> Estilo neón
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onEdit() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = glassColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Row( // Layout horizontal -> Información y acciones
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) { // Columna info -> Datos de rutina
                Text( // Ejercicio -> Dato principal
                    text = rutina.ejercicio,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Box( // Chip categoría -> Grupo muscular
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(neonPurple.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = rutina.grupoMuscular.uppercase(),
                        color = neonPurple,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) { // Fila series/reps -> Info técnica
                    Icon(Icons.Default.History, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${rutina.series} series x ${rutina.repeticiones} reps",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) { // Fila peso/fecha -> Info técnica
                    Icon(Icons.Default.MonitorWeight, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${rutina.pesoKg} kg",
                        color = neonPurple,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = rutina.fecha,
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 13.sp
                    )
                }
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) { // Columna acciones -> Botones rápidos
                IconButton( // Botón editar -> Navegación
                    onClick = onEdit,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.05f))
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton( // Botón eliminar -> Confirmación
                    onClick = onDelete,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Red.copy(alpha = 0.1f))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun EmptyRutinasState(neonPurple: Color, navController: NavController) { // UI estado vacío -> Motivación al usuario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box( // Icono central -> Visual
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(neonPurple.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = neonPurple.copy(alpha = 0.3f)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text( // Título -> Mensaje informativo
            text = "Sin rutinas aún",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text( // Descripción -> Guía al usuario
            text = "Tu transformación comienza con el primer registro. ¡Añade una rutina ahora!",
            color = Color.White.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedButton( // Botón acción -> Crea primer registro
            onClick = { navController.navigate("agregar_rutina") },
            border = androidx.compose.foundation.BorderStroke(1.dp, neonPurple),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = neonPurple)
        ) {
            Text("AGREGAR MI PRIMERA RUTINA", fontWeight = FontWeight.Bold)
        }
    }
}
