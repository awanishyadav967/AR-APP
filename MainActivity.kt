package com.xperiencelabs.arapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position

class MainActivity : AppCompatActivity() {

    private lateinit var sceneView: ArSceneView
    private lateinit var modelNode: ArModelNode
    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var button1: ExtendedFloatingActionButton
    private lateinit var button2: ExtendedFloatingActionButton
    private lateinit var buttonDetail1: ExtendedFloatingActionButton
    private lateinit var buttonDetail2: ExtendedFloatingActionButton
    private lateinit var backButton: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneView = findViewById<ArSceneView?>(R.id.sceneView).apply {
            this.lightEstimationMode = Config.LightEstimationMode.DISABLED
        }

        button1 = findViewById<ExtendedFloatingActionButton>(R.id.button1)
        button2 = findViewById<ExtendedFloatingActionButton>(R.id.button2)
        buttonDetail1 = findViewById<ExtendedFloatingActionButton>(R.id.buttonDetail1)
        buttonDetail2 = findViewById<ExtendedFloatingActionButton>(R.id.buttonDetail2)
        backButton = findViewById<ExtendedFloatingActionButton>(R.id.backButton)

        button1.setOnClickListener {
            addTajMahalModel()
            startTajMahalAudioPlayback()
            buttonDetail1.visibility = View.VISIBLE
            backButton.visibility = View.VISIBLE
            button1.visibility = View.GONE
            button2.visibility = View.GONE
        }

        button2.setOnClickListener {
            addTowerModel()
            startTowerAudioPlayback()
            buttonDetail2.visibility = View.VISIBLE
            backButton.visibility = View.VISIBLE
            button1.visibility = View.GONE
            button2.visibility = View.GONE
        }

        buttonDetail1.setOnClickListener {
            val detailText = """
            There are a few ways to reach the Taj Mahal. The most popular way is to take a train to Agra Cantt Railway Station, 
            which is about 3 kilometers from the Taj Mahal. From the train station, you can take a taxi or rickshaw to the Taj Mahal. 
            You can also take a bus to Agra, which will drop you off near the Taj Mahal. 
            
            Here are some of the best hostels to stay in Agra:
            1. Bedweiser Backpackers Hostel: This hostel is located in the heart of Agra, just a short walk from the Taj Mahal. It has clean and comfortable dorm rooms, as well as private rooms. The hostel also has a rooftop terrace with amazing views of the Taj Mahal.
            
            2. Zostel Agra: This hostel is also located in the heart of Agra, and it is a great option for budget travelers. It has clean and comfortable dorm rooms, as well as private rooms. The hostel also has a common kitchen, a library, and a games room.
            
            3. Moustache Hostel Agra: This hostel is located a bit further from the Taj Mahal, but it is a great option for those who want a more relaxed and laid-back atmosphere. It has clean and comfortable dorm rooms, as well as private rooms. The hostel also has a rooftop terrace with stunning views of the Agra Fort.
        """.trimIndent()

            showDialogWithDetail(detailText)
        }

        buttonDetail2.setOnClickListener {
            val detailText = """
           The Leaning Tower of Pisa is located in Piazza dei Miracoli (Square of Miracles) in the city of Pisa, Italy. You can reach Pisa by air, train, or bus.

           By Air: The closest airport to Pisa is the Pisa International Airport (Galileo Galilei Airport). From the airport, you can take a taxi or a shuttle bus to the city center.

           By Train: Pisa is well-connected by train to major cities in Italy. You can take a train to Pisa Centrale railway station, which is located within walking distance from the Leaning Tower of Pisa.

           By Bus: There are long-distance and regional buses that connect Pisa with other cities in Italy and Europe. The main bus station in Pisa is located near the train station.
           
           Hotels:
           Hostel Pisa: Located near the Leaning Tower of Pisa, this hostel offers clean and comfortable dormitory rooms with free Wi-Fi.

           Ostello Bello: Situated close to the Pisa Centrale train station, this hostel provides a vibrant atmosphere with a rooftop terrace and social events.

           Pisa Backpackers Hostel: This hostel is known for its friendly staff and proximity to major attractions like the Leaning Tower.

           Academy Hostel: Situated in the heart of Pisa, this hostel offers a cozy and welcoming environment.

           Affittacamere Delfo: Although not a traditional hostel, it offers budget-friendly private rooms and is located near the Piazza dei Miracoli.
        """.trimIndent()

            showDialogWithDetail(detailText)
        }

        backButton.setOnClickListener {
            backButtonClicked()
        }
    }

    private fun showDialogWithDetail(detailText: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Details")
            .setMessage(detailText)
            .setPositiveButton("OK", null)
            .create()
        dialog.show()
    }

    private fun addTajMahalModel() {
        modelNode = ArModelNode(sceneView.engine, PlacementMode.INSTANT).apply {
            loadModelGlbAsync(
                glbFileLocation = "models/tajmahal.glb",
                scaleToUnits = 1f,
                centerOrigin = Position(0f, 0f, 0f) // Adjust position as needed
            ) {
                sceneView.planeRenderer.isVisible = true
            }
        }

        sceneView.addChild(modelNode)
    }

    private fun startTajMahalAudioPlayback() {
        mediaPlayer = MediaPlayer.create(this, R.raw.tajmahal)
        mediaPlayer.start()
    }

    private fun addTowerModel() {
        modelNode = ArModelNode(sceneView.engine, PlacementMode.INSTANT).apply {
            loadModelGlbAsync(
                glbFileLocation = "models/tower.glb",
                scaleToUnits = 1f,
                centerOrigin = Position(0f, 0f, -1.5f) // Adjust position as needed
            ) {
                sceneView.planeRenderer.isVisible = true
            }
        }

        sceneView.addChild(modelNode)
    }

    private fun startTowerAudioPlayback() {
        mediaPlayer = MediaPlayer.create(this, R.raw.tower)
        mediaPlayer.start()
    }

    private fun backButtonClicked() {
        sceneView.removeChild(modelNode)
        mediaPlayer.stop()
        button1.visibility = View.VISIBLE
        button2.visibility = View.VISIBLE
        backButton.visibility = View.GONE
        buttonDetail1.visibility = View.GONE
        buttonDetail2.visibility = View.GONE
    }
}