import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF, Word2Vec, Word2VecModel}

import scala.collection.immutable.HashMap
import java.io._

import scala.io.Source

/**
  * Created by Mayanka on 19-06-2017.
  */
object lab3 {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "F:\\summer\\kdm")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    //Reading the Text File
    val documents = sc.textFile("C:\\Users\\syedm\\Desktop\\Tutorial 3 Source Code\\Tutorial 3 Source Code\\Spark_TFIDF_W2V\\data\\001.txt")

    //Getting the N-Gram form of the words in TextFile
    val documentseq = documents.map(f => {

      val x=NGRAM.getNGrams(f,2).map(f=>{f.mkString(" ")})
   //   val lemmatised = CoreNLP.returnLemma(f)
     // val splitString = lemmatised.split(" ")
      x.toSeq
    })

    //Creating an object of HashingTF Class
    val hashingTF = new HashingTF()

    //Creating Term Frequency of the document
    val tf = hashingTF.transform(documentseq)
    tf.cache()


    val idf = new IDF().fit(tf)

    //Creating Inverse Document Frequency
    val tfidf = idf.transform(tf)

    val tfidfvalues = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val values = ff(2).replace("]", "").replace(")", "").split(",")
      values
    })

    val tfidfindex = tfidf.flatMap(f => {
      val ff: Array[String] = f.toString.replace(",[", ";").split(";")
      val indices = ff(1).replace("]", "").replace(")", "").split(",")
      indices
    })

    tfidf.foreach(f => println("line55:"+f))

    val tfidfData = tfidfindex.zip(tfidfvalues)

    var hm = new HashMap[String, Double]

    tfidfData.collect().foreach(f => {
      hm += f._1 -> f._2.toDouble
    })

    val mapp = sc.broadcast(hm)

    val documentData = documentseq.flatMap(_.toList)
    val dd = documentData.map(f => {
      val i = hashingTF.indexOf(f)
      val h = mapp.value
      (f, h(i.toString))
    })

    val dd1 = dd.distinct().sortBy(_._2, false)
    dd1.take(20).foreach(f => {
      println(f)
        val x=f._1

      val file = new File("C:\\Users\\syedm\\Desktop\\hello.txt")
      val bw = new BufferedWriter(new FileWriter(file,true))
      bw.write(x)
      bw.write(" ")
      bw.close()
    })


    val input1 = sc.textFile("C:\\Users\\syedm\\Desktop\\hello.txt").map(line => line.split(" ").toSeq)

    val modelFolder = new File("myModelPath")

    if (modelFolder.exists()) {
      val sameModel = Word2VecModel.load(sc, "myModelPath")
      val synonyms = sameModel.findSynonyms("out", 10)

      for ((synonym, cosineSimilarity) <- synonyms) {
        println(s"$synonym $cosineSimilarity")
      }
    }
    else {
      val word2vec = new Word2Vec().setVectorSize(500)

      val model = word2vec.fit(input1)

      val synonyms = model.findSynonyms("out", 10)

      for ((synonym, cosineSimilarity) <- synonyms) {
        println(s"$synonym $cosineSimilarity")
      }

      model.getVectors.foreach(f => println(f._1 + ":" + f._2.length))

      // Save and load model
      model.save(sc, "myModelPath")

    }


    }

}
