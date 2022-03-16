package binar.academy.myfileprocessingpractice

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import binar.academy.myfileprocessing.utils.Utils
import binar.academy.myfileprocessingpractice.databinding.ActivityMainBinding
import binar.academy.myfileprocessingpractice.databinding.ActivityPdfViewBinding
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import java.io.File

class PdfViewActivity : AppCompatActivity() {
    companion object {
        private const val PDF_SELECTION_CODE = 99
    }
    private lateinit var binding: ActivityPdfViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPdfAction(intent)
        PRDownloader.initialize(applicationContext)
    }

    private fun checkPdfAction(intent: Intent) {
        when (intent.getStringExtra("ViewType")) {
            "assets" -> {
                // perform action to show pdf from assets
                showPdfFromAssets(Utils.getPdfNameFromAssets())
            }
            "storage" -> {
                // perform action to show pdf from storage
                selectPdfFromStorage()
            }
            "internet" -> {
                // perform action to show pdf from the internet
                binding.progressBar.visibility = View.VISIBLE
                val fileName = "myFile.pdf"
                downloadPdfFromInternet(
                    Utils.getPdfUrl(),
                    Utils.getRootDirPath(this),
                    fileName
                )
            }
        }
    }

    private fun showPdfFromAssets(pdfName: String) {
        binding.pdfView.fromAsset(pdfName)
            .password(null) // if password protected, then write password
            .defaultPage(0) // set the default page to open
            .onPageError { page, _ ->
                Toast.makeText(
                    this@PdfViewActivity,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }

    private fun selectPdfFromStorage() {
        Toast.makeText(this, "selectPDF", Toast.LENGTH_LONG).show()
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(
            Intent.createChooser(browseStorage, "Select PDF"), PDF_SELECTION_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdfFromStorage = data.data
            showPdfFromUri(selectedPdfFromStorage)
        }
    }

    private fun showPdfFromUri(uri: Uri?) {
        binding.pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
    }

    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Toast.makeText(this@PdfViewActivity, "downloadComplete", Toast.LENGTH_LONG)
                        .show()
                    val downloadedFile = File(dirPath, fileName)
                    binding.progressBar.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: Error?) {
                    Toast.makeText(
                        this@PdfViewActivity,
                        "Error in downloading file : $error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    private fun showPdfFromFile(file: File) {
        binding.pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    this@PdfViewActivity,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }
}