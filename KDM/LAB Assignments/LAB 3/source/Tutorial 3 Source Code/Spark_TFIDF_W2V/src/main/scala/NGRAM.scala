import scala.io.Source


/**
  * Created by Mayanka on 19-06-2017.
  */
object NGRAM {

  def main(args: Array[String]): Unit = {

    val bufferedSource = Source.fromFile("C:\\Users\\syedm\\Desktop\\1.txt")
    for (line <- bufferedSource.getLines) {
      println(line.toUpperCase)

      val a = getNGrams(line,2)
      a.foreach(f=>println(f.mkString(" ")))
    }

    bufferedSource.close

//    val a = getNGrams("the bee is the bee of the bees",3)
//    a.foreach(f=>println(f.mkString(" ")))
  }

  def getNGrams(sentence: String, n:Int): Array[Array[String]] = {
    val words = sentence
    val ngrams = words.split(' ').sliding(n)
    ngrams.toArray
  }

}


