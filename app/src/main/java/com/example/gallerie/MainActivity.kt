package com.example.gallerie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gallerie.ui.theme.GallerieTheme

// Classe pour représenter une œuvre d'art
data class Artwork(
    val title: String,
    val artist: String,
    val year: String,
    val imageResId: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GallerieTheme {
                ArtSpaceScreen()
            }
        }
    }
}

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    val artworks = listOf(
        Artwork("Titre 1", "Artiste 1", "2020", R.drawable.images),
        Artwork("Titre 2", "Artiste 2", "2021", R.drawable.island_1024),
        Artwork("Titre 3", "Artiste 3", "2022", R.drawable.why_gallery)
    )

    var currentIndex by remember { mutableStateOf(0) } // État pour l'indice de l'œuvre actuelle

    val currentArtwork = artworks[currentIndex] // Obtenez l'œuvre actuelle

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),

    ) {
        ArtGalleryWall(currentArtwork) // Affichez l'œuvre actuelle
        ArtworkDescription(currentArtwork) // Affichez les détails de l'œuvre actuelle
        ControlButtons { direction -> // Passez la direction à la logique des boutons
            currentIndex = when (direction) {
                Direction.NEXT -> (currentIndex + 1) % artworks.size
                Direction.PREVIOUS -> (currentIndex - 1 + artworks.size) % artworks.size
            }
        }
    }
}

@Composable
fun ArtGalleryWall(artwork: Artwork) {
    Image(
        painter = painterResource(id = artwork.imageResId),
        contentDescription = artwork.title,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    )
}

@Composable
fun ArtworkDescription(artwork: Artwork) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = artwork.title, style = MaterialTheme.typography.titleMedium)
        Text(text = artwork.artist, style = MaterialTheme.typography.bodyLarge)
        Text(text = artwork.year, style = MaterialTheme.typography.bodyMedium)
    }
}

enum class Direction {
    NEXT, PREVIOUS
}

@Composable
fun ControlButtons(onDirectionChange: (Direction) -> Unit) {
    Row(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { onDirectionChange(Direction.PREVIOUS) }) {
            Text("Précédent")
        }
        Spacer(modifier = Modifier.weight(1f)) // Espace entre les boutons
        Button(onClick = { onDirectionChange(Direction.NEXT) }) {
            Text("Suivant")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArtSpaceScreen() {
    GallerieTheme {
        ArtSpaceScreen()
    }
}
