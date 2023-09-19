import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val inputImagePath = "path/to/your/input/image.jpg" // 入力画像のパス
    val outputImagePath = "path/to/your/output/resized_image.jpg" // 出力画像のパス
    val newWidth = 200 // 新しい幅
    val newHeight = 150 // 新しい高さ

    resizeImage(inputImagePath, outputImagePath, newWidth, newHeight)
}

fun resizeImage(inputPath: String, outputPath: String, newWidth: Int, newHeight: Int) {
    try {
        val inputFile = File(inputPath)
        if (!inputFile.exists()) {
            println("指定したファイルが存在しません: $inputPath")
            return
        }

        val originalImage = ImageIO.read(inputFile).let {
            when (it.type) {
                BufferedImage.TYPE_BYTE_GRAY -> {
                    val tempImage = BufferedImage(it.width, it.height, BufferedImage.TYPE_3BYTE_BGR)
                    tempImage.graphics.drawImage(it, 0, 0, null)
                    tempImage
                }

                else -> it
            }
        }

        val resizedImage = BufferedImage(newWidth, newHeight, originalImage.type)

        val g2d: Graphics2D = resizedImage.createGraphics()
        g2d.drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null)
        g2d.dispose()

        val outputFile = File(outputPath)
        ImageIO.write(resizedImage, "jpg", outputFile)

        println("画像をリサイズして保存しました: $outputPath")
    } catch (e: Exception) {
        println("エラーが発生しました: ${e.message}")
    }
}