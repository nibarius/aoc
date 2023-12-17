import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.nio.PngWriter
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO

class Visualization(private val zoom: Int = 10) {

    private fun BufferedImage.toByteArray(): ByteArray {
        val baos = ByteArrayOutputStream()
        ImageIO.write(this, "gif", baos)
        return baos.toByteArray()
    }

    private fun drawToImage(toDraw: AMap, colors: Map<Char, Int>): ImmutableImage {
        val yrange = toDraw.yRange()
        val xrange = toDraw.xRange()
        val img = BufferedImage(xrange.count() * zoom, yrange.count() * zoom, BufferedImage.TYPE_INT_ARGB)
        yrange.forEach { y ->
            xrange.forEach { x ->
                if (x >= 0) {
                    toDraw.getOrDefault(Pos(x, y), '\u0000').let { color ->
                        val realColor: Int = colors.getOrDefault(color, Color(0, 0, 0, 0).rgb)
                        (0..<zoom).forEach { yOffset ->
                            (0..<zoom).forEach { xOffset ->
                                val drawColor = if (zoom > 5 && (yOffset == 0 || xOffset == 0)) {
                                    // Draw a solid black border around each pixel for large enough zoom levels
                                    Color.BLACK.rgb
                                } else {
                                    realColor
                                }
                                img.setRGB(x * zoom + xOffset, y * zoom + yOffset, drawColor)
                            }
                        }
                    }
                }
            }
        }
        return ImmutableImage.loader().fromBytes(img.toByteArray())
    }

    /**
     * Draws the given AMap to a png file using the provided color mapping. Characters with no specified colars
     * are drawn as transparent.
     * @param toDraw the AMap to draw
     * @param colors a mapping that tells which characters should have which color. '\u0000' is
     *               used for positions not present in the map.
     * @param fileName The name of the file to create. Will be placed in the folder visualizations.
     */
    fun drawPng(toDraw: AMap, colors: Map<Char, Int>, fileName: String = "tmp.png") {
        val allowed = "[a-zA-Z0-9._-]+".toRegex()
        if (!allowed.matches(fileName) || fileName.contains("..")) {
            error("Invalid file name: $fileName")
        }
        val image = drawToImage(toDraw, colors)
        val writer = PngWriter(9)
        image.output(writer, File("visualizations/$fileName"))
    }
}