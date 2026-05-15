package com.example.gymtrackerpro.screens // Define paquete -> Ubicación en el proyecto

import androidx.compose.animation.* // Importa animaciones -> Control de UI reactiva
import androidx.compose.animation.core.* // Importa núcleo de animaciones -> Control de estados
import androidx.compose.foundation.background // Importa fondo -> Estilo visual
import androidx.compose.foundation.clickable // Importa click -> Interacción de usuario
import androidx.compose.foundation.interaction.MutableInteractionSource // Importa fuente interacción -> Estado de entrada
import androidx.compose.foundation.interaction.collectIsPressedAsState // Importa estado presión -> Feedback visual
import androidx.compose.foundation.layout.* // Importa layouts -> Estructura de UI
import androidx.compose.foundation.lazy.grid.GridCells // Importa celdas grid -> Configuración de rejilla
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid // Importa grid vertical -> Lista en columnas
import androidx.compose.foundation.shape.CircleShape // Importa forma circular -> Estilo visual
import androidx.compose.foundation.shape.RoundedCornerShape // Importa esquinas redondeadas -> Estilo visual
import androidx.compose.material.icons.Icons // Importa iconos -> Librería Material
import androidx.compose.material.icons.automirrored.filled.* // Importa iconos con espejo -> Visuales
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
import androidx.compose.ui.text.SpanStyle // Importa estilo de texto -> Formato parcial
import androidx.compose.ui.text.buildAnnotatedString // Importa cadena anotada -> Texto mixto
import androidx.compose.ui.text.font.FontWeight // Importa peso fuente -> Estilo texto
import androidx.compose.ui.text.withStyle // Importa con estilo -> Bloque de formato
import androidx.compose.ui.unit.dp // Importa unidad dp -> Medidas UI
import androidx.compose.ui.unit.sp // Importa unidad sp -> Tamaño texto
import androidx.navigation.NavController // Importa controlador navegación -> Gestión de rutas
import com.example.gymtrackerpro.viewmodel.GymViewModel // Importa ViewModel -> Lógica de negocio
import kotlinx.coroutines.delay // Importa retardo -> Control de tiempo
import kotlinx.coroutines.launch // Importa lanzador de corrutinas -> Tareas asíncronas

@OptIn(ExperimentalMaterial3Api::class) // Habilita APIs experimentales -> Uso de Drawer/TopAppBar
@Composable // Marca función como Composable -> Generador de UI
fun MenuPrincipalScreen(navController: NavController, viewModel: GymViewModel) { // Pantalla principal -> Recibe controlador y VM
    val usuario by viewModel.usuarioLogueado.collectAsState() // Observa sesión -> Obtiene datos de ViewModel
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // Estado del menú lateral -> Control de apertura/cierre
    val scope = rememberCoroutineScope() // Scope de corrutinas -> Lanzador de efectos
    var visible by remember { mutableStateOf(false) } // Estado animación -> Control de visibilidad

    val darkBackground = Color(0xFF0D0D0D) // Color fondo -> Paleta oscura
    val neonPurple = Color(0xFF7B2FF7) // Color principal -> Paleta neón
    val gradientPurple = Color(0xFFA855F7) // Color secundario -> Paleta degradada
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f) // Color traslúcido -> Efecto cristal

    LaunchedEffect(Unit) { // Efecto de arranque -> Se ejecuta al cargar
        delay(200) // Espera inicial -> Pausa estética
        visible = true // Activa visibilidad -> Dispara animaciones
    }

    ModalNavigationDrawer( // Contenedor de menú lateral -> Navegación de la app
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet( // Contenido del menú lateral -> Panel deslizable
                modifier = Modifier.width(300.dp),
                drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                drawerContainerColor = darkBackground
            ) {
                Box( // Cabecera del menú -> Info del usuario
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(neonPurple.copy(alpha = 0.2f), Color.Transparent)
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Box( // Avatar circular -> Visual del usuario
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(neonPurple.copy(alpha = 0.1f))
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(neonPurple),
                            contentAlignment = Alignment.Center
                        ) {
                            Text( // Inicial del nombre -> Identificador visual
                                text = usuario?.nombreCompleto?.take(1)?.uppercase() ?: "G",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text( // Nombre completo -> Dato de sesión
                            text = usuario?.nombreCompleto ?: "Atleta",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text( // Email -> Dato de sesión
                            text = usuario?.email ?: "gymtracker@pro.com",
                            color = Color.White.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                DrawerItemCustom( // Opción inicio -> Navegación interna
                    label = "Inicio",
                    icon = Icons.Default.Home,
                    isSelected = true,
                    onClick = { scope.launch { drawerState.close() } }, // Cierra menú -> Acción UI
                    neonPurple = neonPurple
                )
                DrawerItemCustom( // Opción agregar -> Navegación
                    label = "Agregar Rutina",
                    icon = Icons.Default.AddCircleOutline,
                    isSelected = false,
                    onClick = {
                        scope.launch { drawerState.close() } // Cierra menú -> Acción UI
                        navController.navigate("agregar_rutina") // Navega a formulario -> Destino
                    },
                    neonPurple = neonPurple
                )
                DrawerItemCustom( // Opción mis rutinas -> Navegación
                    label = "Mis Rutinas",
                    icon = Icons.Default.FitnessCenter,
                    isSelected = false,
                    onClick = {
                        scope.launch { drawerState.close() } // Cierra menú -> Acción UI
                        navController.navigate("lista_rutinas") // Navega a historial -> Destino
                    },
                    neonPurple = neonPurple
                )
                DrawerItemCustom( // Opción perfil -> Navegación
                    label = "Mi Perfil",
                    icon = Icons.Default.AccountCircle,
                    isSelected = false,
                    onClick = {
                        scope.launch { drawerState.close() } // Cierra menú -> Acción UI
                        navController.navigate("perfil") // Navega a datos personales -> Destino
                    },
                    neonPurple = neonPurple
                )

                Spacer(modifier = Modifier.weight(1f))
                
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = Color.White.copy(alpha = 0.1f)) // Divisor -> Separación visual

                DrawerItemCustom( // Opción logout -> Acción de sistema
                    label = "Cerrar Sesión",
                    icon = Icons.AutoMirrored.Filled.Logout,
                    isSelected = false,
                    onClick = {
                        scope.launch { drawerState.close() } // Cierra menú -> Acción UI
                        viewModel.logout() // Limpia sesión -> Llama al ViewModel
                        navController.navigate("login") { // Redirige al login -> Destino final
                            popUpTo("menu") { inclusive = true } // Limpia historial -> Evita volver atrás
                        }
                    },
                    neonPurple = Color.Red.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    ) {
        Box( // Contenedor contenido principal -> Fondo
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ) {
            Box( // Efecto de luz radial -> Estilo visual neón
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(neonPurple.copy(alpha = 0.15f), Color.Transparent),
                            center = androidx.compose.ui.geometry.Offset(1000f, 0f),
                            radius = 1500f
                        )
                    )
            )

            Scaffold( // Estructura de pantalla -> Layout de contenido
                containerColor = Color.Transparent,
                topBar = {
                    CenterAlignedTopAppBar( // Barra superior personalizada -> Logo y menú
                        title = {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                                        append("GymTracker ")
                                    }
                                    withStyle(style = SpanStyle(color = neonPurple, fontWeight = FontWeight.ExtraBold)) {
                                        append("Pro")
                                    }
                                },
                                fontSize = 22.sp
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } }, // Abre menú -> Acción UI
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(CircleShape)
                                    .background(glassColor)
                            ) {
                                Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = { }, // Notificaciones -> Espacio para futura función
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(CircleShape)
                                    .background(glassColor)
                            ) {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White)
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
            ) { padding ->
                Column( // Contenedor vertical -> Dashboard principal
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 24.dp)
                        .animateContentSize()
                ) {
                    AnimatedVisibility( // Animación de entrada -> Elementos del dashboard
                        visible = visible,
                        enter = fadeIn(tween(1000)) + slideInVertically(initialOffsetY = { 20 })
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text( // Saludo -> Texto informativo
                                text = "Bienvenido de nuevo,",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 16.sp
                            )
                            Text( // Nombre del atleta -> Dato de sesión
                                text = usuario?.nombreCompleto ?: "Atleta",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))

                            Box( // Banner motivacional -> Diseño visual destacado
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(neonPurple, gradientPurple)
                                        )
                                    )
                                    .padding(24.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Column {
                                    Text( // Frase motivadora -> Visual
                                        text = "Supérate hoy",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text( // Subfrase -> Visual
                                        text = "Sigue entrenando y supera\ntus límites físicos.",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 14.sp
                                    )
                                }
                                Icon( // Icono decorativo -> Rayo estético
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .align(Alignment.CenterEnd)
                                        .graphicsLayer(alpha = 0.2f),
                                    tint = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Text( // Título sección -> Organización
                                text = "Tu Dashboard",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            LazyVerticalGrid( // Rejilla de tarjetas -> Acceso rápido
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item {
                                    MenuCardPremium( // Tarjeta agregar -> Navegación
                                        "Agregar Rutina",
                                        Icons.Default.Add,
                                        neonPurple,
                                        glassColor
                                    ) {
                                        navController.navigate("agregar_rutina") // Navega a formulario -> Destino
                                    }
                                }
                                item {
                                    MenuCardPremium( // Tarjeta lista -> Navegación
                                        "Mis Rutinas",
                                        Icons.AutoMirrored.Filled.FormatListBulleted,
                                        neonPurple,
                                        glassColor
                                    ) {
                                        navController.navigate("lista_rutinas") // Navega a historial -> Destino
                                    }
                                }
                                item {
                                    MenuCardPremium( // Tarjeta perfil -> Navegación
                                        "Mi Perfil",
                                        Icons.Default.Person,
                                        neonPurple,
                                        glassColor
                                    ) {
                                        navController.navigate("perfil") // Navega a datos personales -> Destino
                                    }
                                }
                                item {
                                    MenuCardPremium( // Tarjeta cerrar sesión -> Acción
                                        "Cerrar Sesión",
                                        Icons.AutoMirrored.Filled.ExitToApp,
                                        Color.Red.copy(alpha = 0.6f),
                                        glassColor
                                    ) {
                                        viewModel.logout() // Limpia sesión -> Llama al ViewModel
                                        navController.navigate("login") { // Redirige al login -> Destino
                                            popUpTo("menu") { inclusive = true } // Limpia historial -> Seguridad
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItemCustom( // Componente ítem de menú -> Reutilización UI
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    neonPurple: Color
) {
    NavigationDrawerItem( // Componente estándar -> Opción de menú
        label = { Text(text = label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
        selected = isSelected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = null) },
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = neonPurple.copy(alpha = 0.1f),
            unselectedIconColor = Color.White.copy(alpha = 0.6f),
            selectedIconColor = neonPurple,
            unselectedTextColor = Color.White.copy(alpha = 0.6f),
            selectedTextColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun MenuCardPremium( // Tarjeta de dashboard -> Diseño personalizado
    title: String,
    icon: ImageVector,
    accentColor: Color,
    glassColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() } // Manejo interacción -> Feedback
    val isPressed by interactionSource.collectIsPressedAsState() // Detecta toque -> Estado reactivo
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "cardScale") // Calcula escala -> Animación

    Card( // Contenedor tarjeta -> Estilo neón
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = glassColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Column( // Contenido tarjeta -> Icono y título
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box( // Contenedor icono -> Diseño visual
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon( // Icono -> Representación visual
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = accentColor
                )
            }
            Text( // Título de la tarjeta -> Texto informativo
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 18.sp
            )
        }
    }
}
