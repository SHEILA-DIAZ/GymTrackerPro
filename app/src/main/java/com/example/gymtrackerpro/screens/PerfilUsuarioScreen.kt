package com.example.gymtrackerpro.screens // Define paquete -> Ubicación en el proyecto

import androidx.compose.animation.* // Importa animaciones -> Control de UI reactiva
import androidx.compose.animation.core.* // Importa núcleo de animaciones -> Control de estados
import androidx.compose.foundation.background // Importa fondo -> Estilo visual
import androidx.compose.foundation.border // Importa borde -> Estilo visual
import androidx.compose.foundation.layout.* // Importa layouts -> Estructura de UI
import androidx.compose.foundation.rememberScrollState // Importa estado scroll -> Persistencia de posición
import androidx.compose.foundation.shape.CircleShape // Importa forma circular -> Estilo visual
import androidx.compose.foundation.shape.RoundedCornerShape // Importa esquinas redondeadas -> Estilo visual
import androidx.compose.foundation.verticalScroll // Importa scroll vertical -> Navegación de contenido
import androidx.compose.material.icons.Icons // Importa iconos -> Librería Material
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Importa icono atrás -> Visual
import androidx.compose.material.icons.automirrored.filled.ExitToApp // Importa icono salir -> Visual
import androidx.compose.material.icons.filled.* // Importa todos los iconos -> Visuales
import androidx.compose.material3.* // Importa componentes Material3 -> UI Kit
import androidx.compose.runtime.* // Importa runtime de Compose -> Manejo de estados
import androidx.compose.ui.Alignment // Importa alineación -> Posicionamiento
import androidx.compose.ui.Modifier // Importa modificadores -> Atributos de componentes
import androidx.compose.ui.draw.clip // Importa recorte -> Forma visual
import androidx.compose.ui.graphics.Brush // Importa degradados -> Estilo visual
import androidx.compose.ui.graphics.Color // Importa colores -> Estilo visual
import androidx.compose.ui.graphics.vector.ImageVector // Importa vector imagen -> Tipo de icono
import androidx.compose.ui.text.font.FontWeight // Importa peso fuente -> Estilo texto
import androidx.compose.ui.unit.dp // Importa unidad dp -> Medidas UI
import androidx.compose.ui.unit.sp // Importa unidad sp -> Tamaño texto
import androidx.navigation.NavController // Importa controlador navegación -> Gestión de rutas
import com.example.gymtrackerpro.viewmodel.GymViewModel // Importa ViewModel -> Lógica de negocio
import kotlinx.coroutines.delay // Importa retardo -> Control de tiempo
import java.util.Locale // Importa configuración regional -> Formateo de datos

@OptIn(ExperimentalMaterial3Api::class) // Habilita APIs experimentales -> Uso de TopAppBar
@Composable // Marca función como Composable -> Generador de UI
fun PerfilUsuarioScreen(navController: NavController, viewModel: GymViewModel) { // Pantalla perfil -> Recibe controlador y VM
    val usuario by viewModel.usuarioLogueado.collectAsState() // Observa sesión -> Obtiene datos personales
    var estadisticas by remember { mutableStateOf(Pair(0, 0.0)) } // Estado métricas -> Registra total rutinas y peso
    var visible by remember { mutableStateOf(false) } // Estado animación -> Control de visibilidad

    val darkBackground = Color(0xFF0D0D0D) // Color fondo -> Paleta oscura
    val neonPurple = Color(0xFF7B2FF7) // Color principal -> Paleta neón
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f) // Color traslúcido -> Efecto cristal

    LaunchedEffect(usuario) { // Efecto carga -> Se ejecuta al detectar sesión
        usuario?.let {
            estadisticas = viewModel.getEstadisticas(it.id) // Obtiene cálculos -> Llama al ViewModel
        }
        delay(100) // Pausa inicial -> Mejora estética
        visible = true // Activa visibilidad -> Dispara animaciones
    }

    Box( // Contenedor base -> Capas de fondo
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
                CenterAlignedTopAppBar( // Barra superior -> Título y navegación atrás
                    title = {
                        Text(
                            "Perfil",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton( // Botón volver -> Regresa pantalla previa
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->
            AnimatedVisibility( // Animación de entrada -> Datos del perfil
                visible = visible,
                enter = fadeIn(tween(800)) + slideInVertically(initialOffsetY = { 30 }),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column( // Contenedor vertical -> Perfil scrolleable
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Box( // Cabecera perfil -> Avatar de usuario
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(neonPurple.copy(alpha = 0.1f))
                            .border(2.dp, neonPurple, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text( // Inicial del nombre -> Identificador visual
                            text = usuario?.nombreCompleto?.take(1)?.uppercase() ?: "G",
                            color = Color.White,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text( // Nombre real -> Dato de sesión
                        text = usuario?.nombreCompleto ?: "Atleta",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text( // Username -> Identificador único
                        text = "@${usuario?.nombreUsuario}",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text( // Frase motivacional -> Estilo visual
                        text = "\"Cada repetición cuenta. Sigue superando tus límites.\"",
                        color = neonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row( // Fila de estadísticas -> Resumen de progreso
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StatCardPremium( // Tarjeta cantidad -> Total registros
                            value = estadisticas.first.toString(),
                            label = "Rutinas",
                            icon = Icons.Default.FitnessCenter,
                            accentColor = neonPurple,
                            glassColor = glassColor,
                            modifier = Modifier.weight(1f)
                        )
                        StatCardPremium( // Tarjeta volumen -> Total peso
                            value = String.format(Locale.getDefault(), "%.0f", estadisticas.second),
                            label = "kg totales",
                            icon = Icons.Default.MonitorWeight,
                            accentColor = neonPurple,
                            glassColor = glassColor,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Card( // Card detalles -> Información personal
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = glassColor),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            InfoRowPremium(Icons.Default.Email, "Email", usuario?.email ?: "", neonPurple) // Fila email -> Dato sesión
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.White.copy(alpha = 0.05f))
                            InfoRowPremium(Icons.Default.Person, "Edad", "${usuario?.edad} años", neonPurple) // Fila edad -> Dato sesión
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.White.copy(alpha = 0.05f))
                            InfoRowPremium(Icons.Default.DateRange, "Miembro desde", usuario?.fechaRegistro ?: "", neonPurple) // Fila fecha -> Dato sesión
                        }
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    Button( // Botón cerrar sesión -> Acción sistema
                        onClick = {
                            viewModel.logout() // Limpia sesión -> Llama al ViewModel
                            navController.navigate("login") { // Redirige al login -> Destino final
                                popUpTo("menu") { inclusive = true } // Limpia historial -> Seguridad
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.15f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) { // Contenido botón -> Visual error
                            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = Color.Red)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "CERRAR SESIÓN",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
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

@Composable
fun StatCardPremium( // Componente tarjeta estadística -> Reutilización UI
    value: String,
    label: String,
    icon: ImageVector,
    accentColor: Color,
    glassColor: Color,
    modifier: Modifier = Modifier
) {
    Card( // Contenedor tarjeta -> Estilo neón
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = glassColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Column( // Contenido tarjeta -> Valor y etiqueta
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = label, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun InfoRowPremium(icon: ImageVector, label: String, value: String, accentColor: Color) { // Fila informativa -> Reutilización UI
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box( // Contenedor icono -> Diseño visual
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(accentColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = accentColor)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column { // Texto info -> Título y valor
            Text(text = label, color = Color.Gray, fontSize = 12.sp)
            Text(text = value, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}
