import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object ScalaOne {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "F:\\summer\\kdm");

    val sparkConfig = new SparkConf().setAppName("SparkNLP").setMaster("local[*]")

    val sc1 = new SparkContext(sparkConfig)
    val call: LabTwo = new LabTwo();


    val rdfile = sc1.textFile("C:\\Users\\syedm\\Desktop\\kdm\\001.txt");
    for (b <- 0 to 3) {
      val in1 = scala.io.StdIn.readLine()
      if (in1.contains("who")) {
        val res1 = rdfile.map(line => {
          call.retrn(line, "PERSON")
        })
        funtn(res1,in1)
      }
      if (in1.contains("where")) {
        val res1 = rdfile.map(line => {
          call.retrn(line, "LOCATION")
        })
        funtn(res1,in1)
      }
      if (in1.contains("what")) {
        val res1 = rdfile.map(line => {
          call.retrn(line, "ORGANIZATION")
        })
        funtn(res1,in1)
      }
    }

  }
  def funtn(value: RDD[String], str: String)
  {

    val ra2=value.flatMap(a=>{a.split(" ")}).map(c=>(c)).filter(k=>{k.length>2})
    val rb3=ra2.distinct()


    rb3.take(9).foreach(println)
  }
}