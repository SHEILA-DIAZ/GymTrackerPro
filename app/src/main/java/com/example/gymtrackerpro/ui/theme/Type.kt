package com.example.gymtrackerpro.ui.theme // Define paquete -> Ubicación de recursos de diseño

import androidx.compose.material3.Typography // Importa Typography -> Material3
import androidx.compose.ui.text.TextStyle // Importa TextStyle -> Estilo de texto
import androidx.compose.ui.text.font.FontFamily // Importa FontFamily -> Fuente de texto
import androidx.compose.ui.text.font.FontWeight // Importa FontWeight -> Peso de fuente
import androidx.compose.ui.unit.sp // Importa unidad sp -> Tamaño de texto

// Define estilos tipográficos -> Configuración global de texto
val Typography = Typography( // Crea objeto de tipografía -> Material Design 3
    bodyLarge = TextStyle( // Define estilo para cuerpo grande -> Texto estándar
        fontFamily = FontFamily.Default, // Fuente por defecto -> Sistema Android
        fontWeight = FontWeight.Normal, // Peso normal -> Legibilidad
        fontSize = 16.sp, // Tamaño 16sp -> Tamaño base
        lineHeight = 24.sp, // Altura de línea -> Espaciado vertical
        letterSpacing = 0.5.sp // Espaciado entre letras -> Legibilidad
    )
)
