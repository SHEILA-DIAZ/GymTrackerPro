package com.example.gymtrackerpro.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtrackerpro.viewmodel.GymViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(navController: NavController, viewModel: GymViewModel) {
    val usuario by viewModel.usuarioLogueado.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(false) }

    // Colores Fitness Premium
    val darkBackground = Color(0xFF0D0D0D)
    val neonPurple = Color(0xFF7B2FF7)
    val gradientPurple = Color(0xFFA855F7)
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f)

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                drawerContainerColor = darkBackground
            ) {
                // Drawer Header
                Box(
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
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(neonPurple.copy(alpha = 0.1f))
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(neonPurple),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = usuario?.nombreCompleto?.take(1)?.uppercase() ?: "G",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = usuario?.nombreCompleto ?: "Atleta",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = usuario?.email ?: "gymtracker@pro.com",
                            color = Color.White.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Drawer Items
                DrawerItemCustom(
                    label = "Inicio",
                    icon = Icons.Default.Home,
                    isSelected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    neonPurple = neonPurple
                )
                DrawerItemCustom(
                    label = "Agregar Rutina",
                    icon = Icons.Default.AddCircleOutline,
                    isSelected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("agregar_rutina")
                    },
                    neonPurple = neonPurple
                )
                DrawerItemCustom(
                    label = "Mis Rutinas",
                    icon = Icons.Default.FitnessCenter,
                    isSelected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("lista_rutinas")
                    },
                    neonPurple = neonPurple
                )
                DrawerItemCustom(
                    label = "Mi Perfil",
                    icon = Icons.Default.AccountCircle,
                    isSelected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("perfil")
                    },
                    neonPurple = neonPurple
                )

                Spacer(modifier = Modifier.weight(1f))
                
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp), color = Color.White.copy(alpha = 0.1f))

                DrawerItemCustom(
                    label = "Cerrar Sesión",
                    icon = Icons.AutoMirrored.Filled.Logout,
                    isSelected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo("menu") { inclusive = true }
                        }
                    },
                    neonPurple = Color.Red.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(darkBackground)
        ) {
            // Fondo con degradado sutil
            Box(
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

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    CenterAlignedTopAppBar(
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
                                onClick = { scope.launch { drawerState.open() } },
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
                                onClick = { /* Notificaciones */ },
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 24.dp)
                        .animateContentSize()
                ) {
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(tween(1000)) + slideInVertically(initialOffsetY = { 20 })
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Bienvenido de nuevo,",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 16.sp
                            )
                            Text(
                                text = usuario?.nombreCompleto ?: "Atleta",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))

                            // Banner Motivacional
                            Box(
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
                                    Text(
                                        text = "Supérate hoy",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Sigue entrenando y supera\ntus límites físicos.",
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 14.sp
                                    )
                                }
                                Icon(
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

                            Text(
                                text = "Tu Dashboard",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item {
                                    MenuCardPremium(
                                        "Agregar Rutina",
                                        Icons.Default.Add,
                                        neonPurple,
                                        glassColor
                                    ) {
                                        navController.navigate("agregar_rutina")
                                    }
                                }
                                item {
                                    MenuCardPremium(
                                        "Mis Rutinas",
                                        Icons.AutoMirrored.Filled.FormatListBulleted,
                                        neonPurple,
                                        glassColor
                                    ) {
                                        navController.navigate("lista_rutinas")
                                    }
                                }
                                item {
                                    MenuCardPremium(
                                        "Mi Perfil",
                                        Icons.Default.Person,
                                        neonPurple,
                                        glassColor
                                    ) {
                                        navController.navigate("perfil")
                                    }
                                }
                                item {
                                    MenuCardPremium(
                                        "Cerrar Sesión",
                                        Icons.AutoMirrored.Filled.ExitToApp,
                                        Color.Red.copy(alpha = 0.6f),
                                        glassColor
                                    ) {
                                        viewModel.logout()
                                        navController.navigate("login") {
                                            popUpTo("menu") { inclusive = true }
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
fun DrawerItemCustom(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    neonPurple: Color
) {
    NavigationDrawerItem(
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
fun MenuCardPremium(
    title: String,
    icon: ImageVector,
    accentColor: Color,
    glassColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "cardScale")

    Card(
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = accentColor
                )
            }
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 18.sp
            )
        }
    }
}
