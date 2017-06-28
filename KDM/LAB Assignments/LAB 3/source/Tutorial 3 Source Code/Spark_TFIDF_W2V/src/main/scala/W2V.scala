import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

/**
  * Created by Mayanka on 19-06-2017.
  */
object W2V {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "F:\\summer\\kdm")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
      .set("spark.driver.memory", "6g").set("spark.executor.memory", "6g")

    val sc = new SparkContext(sparkConf)

    val input = sc.textFile("C:\\Users\\syedm\\Desktop\\Tutorial 3 Source Code\\Tutorial 3 Source Code\\Spark_TFIDF_W2V\\data\\001.txt").map(line => line.split(" ").toSeq)

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

      val model = word2vec.fit(input)

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
