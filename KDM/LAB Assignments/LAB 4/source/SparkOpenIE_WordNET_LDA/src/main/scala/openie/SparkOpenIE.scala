package openie

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Mayanka on 27-Jun-16.
  */
object SparkOpenIE {

  def main(args: Array[String]) {
    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)


    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val input = sc.textFile("C:\\Users\\syedm\\Desktop\\kdm\\datasets\\bbcsport\\cricket\\001.txt").map(line => {
      //Getting OpenIE Form of the word using lda.CoreNLP

      val t=CoreNLP.returnTriplets(line)
      t
    })

    //println(CoreNLP.returnTriplets("katrina is bollywood heroine"))

    val x=CoreNLP.returnTriplets(input.collect().mkString("\n"));
    val y = x.replaceAll("," ," ");
    println(y);


  //  println(input.collect().mkString("\n"))



  }

}
