package com.example.gymtrackerpro.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtrackerpro.viewmodel.GymViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(navController: NavController, viewModel: GymViewModel) {
    val usuario by viewModel.usuarioLogueado.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                usuario?.let { user ->
                    Column(modifier = Modifier.padding(16.dp)) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .padding(bottom = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Surface(
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text(
                                        text = user.nombreCompleto.take(2).uppercase(),
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                }
                            }
                        }
                        Text(text = user.nombreCompleto, style = MaterialTheme.typography.titleLarge)
                        Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Agregar rutina") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        navController.navigate("agregar_rutina") 
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Mis rutinas") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        navController.navigate("lista_rutinas") 
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Mi perfil") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        navController.navigate("perfil") 
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo("menu") { inclusive = true }
                        }
                    },
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GymTracker Pro") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* notification icon */ }) {
                            Icon(Icons.Default.Notifications, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(text = "Hola,", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = usuario?.nombreCompleto ?: "Usuario",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        MenuCard("Agregar rutina", Icons.Default.Add, MaterialTheme.colorScheme.primaryContainer) {
                            navController.navigate("agregar_rutina")
                        }
                    }
                    item {
                        MenuCard("Mis rutinas", Icons.Default.List, MaterialTheme.colorScheme.secondaryContainer) {
                            navController.navigate("lista_rutinas")
                        }
                    }
                    item {
                        MenuCard("Mi perfil", Icons.Default.Person, MaterialTheme.colorScheme.tertiaryContainer) {
                            navController.navigate("perfil")
                        }
                    }
                    item {
                        MenuCard("Cerrar sesión", Icons.Default.ExitToApp, MaterialTheme.colorScheme.errorContainer) {
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

@Composable
fun MenuCard(title: String, icon: ImageVector, color: androidx.compose.ui.graphics.Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontSize = 14.sp)
        }
    }
}
