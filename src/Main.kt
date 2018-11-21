import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.PdfReader
import tornadofx.*
import javafx.scene.control.TextField
import javafx.scene.control.TextArea
import java.io.File



class Main : View("CountPDF") {
    //override val root = borderpane {
    //}

    private var pathField: TextField by singleAssign()
    private var resultext: TextArea by singleAssign()
    private var dir: File? = null


    override val root = group {
        form{

            hbox {

                button("Target Directory") {
                    hboxConstraints {
                        marginRight = 5.0

                    }

                    action {
                        dir = chooseDirectory("Select Target Directory")
                        println(dir.toString())
                        if( dir.toString() != "null"){
                            pathField.text = dir.toString()
                        }

                    }

                }
                pathField = textfield(){

                }

            }

            vbox {
                button( "Count").setOnAction { countPages(dir.toString(), resultext) }

                resultext = textarea {
                    prefRowCount = 2

                }

            }


        }


    }

    fun countPages(filepath: String, text: TextArea){
        var total = 0
        var tempResultText = ""
        File(filepath).walkTopDown().forEach {

            if(it.toString().endsWith(".pdf")){
                try {
                    val reader = PdfReader(it.toString())
                    total =+ total + reader.numberOfPages
                    println("Number of pages " + reader.numberOfPages)
                    tempResultText = tempResultText + it.toString() + " = " + reader.numberOfPages + "\n"
                }
                catch(e: Exception){
                    println(e)
                }
            }
        }

                text.text = "Total Pages: " + total + "\n" + tempResultText

    }

}
