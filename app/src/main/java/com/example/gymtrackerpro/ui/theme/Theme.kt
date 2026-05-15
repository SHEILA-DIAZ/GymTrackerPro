package com.example.gymtrackerpro.ui.theme // Define paquete -> Ubicación de recursos de diseño

import android.app.Activity // Importa Activity -> Contexto de Android
import android.os.Build // Importa Build -> Verificación de versión del sistema
import androidx.compose.foundation.isSystemInDarkTheme // Importa detector de tema -> Preferencia del sistema
import androidx.compose.material3.MaterialTheme // Importa MaterialTheme -> Proveedor de estilos
import androidx.compose.material3.darkColorScheme // Importa esquema oscuro -> Definición de colores
import androidx.compose.material3.dynamicDarkColorScheme // Importa colores dinámicos oscuros -> Android 12+
import androidx.compose.material3.dynamicLightColorScheme // Importa colores dinámicos claros -> Android 12+
import androidx.compose.material3.lightColorScheme // Importa esquema claro -> Definición de colores
import androidx.compose.runtime.Composable // Importa Composable -> Función de UI
import androidx.compose.ui.platform.LocalContext // Importa contexto local -> Acceso a recursos

private val DarkColorScheme = darkColorScheme( // Crea esquema oscuro -> Asigna colores
    primary = Purple80, // Asigna color primario -> Estilo visual
    secondary = PurpleGrey80, // Asigna color secundario -> Estilo visual
    tertiary = Pink80 // Asigna color terciario -> Estilo visual
)

private val LightColorScheme = lightColorScheme( // Crea esquema claro -> Asigna colores
    primary = Purple40, // Asigna color primario -> Estilo visual
    secondary = PurpleGrey40, // Asigna color secundario -> Estilo visual
    tertiary = Pink40 // Asigna color terciario -> Estilo visual
)

@Composable // Define función de tema -> Envuelve la aplicación
fun GymTrackerProTheme( // Configura el tema global -> Recibe preferencias y contenido
    darkTheme: Boolean = isSystemInDarkTheme(), // Detecta tema oscuro -> Preferencia del sistema
    dynamicColor: Boolean = true, // Habilita colores dinámicos -> Función de Android
    content: @Composable () -> Unit // Recibe contenido UI -> Pantallas de la app
) {
    val colorScheme = when { // Selecciona esquema -> Lógica de diseño
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> { // Si soporta dinámico -> Android 12+
            val context = LocalContext.current // Obtiene contexto -> Acceso a colores de sistema
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context) // Elige dinámico -> Envía esquema
        }

        darkTheme -> DarkColorScheme // Usa esquema oscuro -> Envía colores fijos
        else -> LightColorScheme // Usa esquema claro -> Envía colores fijos
    }

    MaterialTheme( // Aplica el sistema de diseño -> Configura colores y tipografía
        colorScheme = colorScheme, // Define colores -> Envía esquema seleccionado
        typography = Typography, // Define tipografía -> Estilos de texto
        content = content // Renderiza la app -> Envía a MainActivity
    )
}
